import Models.Package;
import Models.PackageType;
import Models.Response;
import User.User;
import User.Client;

import java.io.*;
import java.net.Socket;

public class TCPServerClientHandler extends TCPServer {
    private Socket socket;
    private User user = new User();
    private Client client = new Client();
    private PrintWriter out;

    public TCPServerClientHandler(Socket socket, Controller controller) {
        super(controller);
        this.socket = socket;
    }
    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            this.out = out;
            String inputLine, outputLine;
            out.println("");

            while ((inputLine = in.readLine()) != null) {
                outputLine = handlePackage(inputLine).toString();
                out.println(outputLine);
                if (outputLine.equals(PackageType.BYE.toString() + ":"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unerwarteter Fehler in der TCP Kommunikation");
        }
    }

    private Package handlePackage(String input) {
        Package pkg = new Package(input);
        Package res;
        switch (pkg.getType()) {
            case CONNECT:
                client.setTcpIpAddress(socket.getRemoteSocketAddress());
                client.setUdpPort(Integer.valueOf(pkg.get("udp_port")));
                return controller.onConnect(pkg, socket.getRemoteSocketAddress());
            case LOGIN:
                res = controller.onLogin(pkg, client);
                if (res.getType() == PackageType.LOGGEDIN) {
                    user.setUsername(pkg.get("username"));
                    user.setClient(client);
                }
                return res;
            case REGISTER:
                return controller.onRegister(pkg);
            case JOIN_ROOM:
                return controller.onJoinRoom(pkg, user);
            case WHOAMI:
                return controller.onWhoAmI(pkg, user);
            case LOGOUT:
                res =  controller.onLogout(pkg, user);
                if (res.getType() == PackageType.LOGGEDOUT) {
                    user = new User();
                }
                return res;
            case DISCONNECT:
                return controller.onDisconnect(pkg, user);
            case LEAVE_ROOM:
                return controller.onLeaveRoom(pkg, user);
            case INVITE:
                return controller.onInvite(pkg, user);
            case MESSAGE:
                return controller.onMessage(pkg, user);
            default:
                return new Response("Leider konnte ich deine Anfrage nich verstehen");
        }
    }

    public void send(Package pkg) {
        String outputLine;
        outputLine = pkg.toString();
        out.println(outputLine);
    }

    public User getUser(){
        return user;
    }
}
