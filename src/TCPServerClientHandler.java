import Models.Package;
import Models.PackageType;
import Models.Response;
import User.User;

import java.io.*;
import java.net.Socket;

public class TCPServerClientHandler extends TCPServer {
    private boolean running;
    private Socket socket;
    private User user = new User();
    public TCPServerClientHandler(Socket socket, Controller controller) {
        super(controller);
        this.socket = socket;
    }
    @Override
    public void run() {
        running = true;
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            out.println("");

            while ((inputLine = in.readLine()) != null) {
                outputLine = handlePackage(inputLine).toString();
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();//
            throw new RuntimeException("Unerwarteter Fehler in der TCP Kommunikation");
        }
    }

    private Package handlePackage(String input) {
        Package pkg = new Package(input);
        switch (pkg.getType()) {
            case CONNECT:
                return new Response("Welcome client");
            case LOGIN:
                pkg.put("client_ip", socket.getRemoteSocketAddress().toString());
                Package res = controller.onLogin(pkg);
                if (res.getType() == PackageType.LOGGEDIN) {
                    user.setUsername(pkg.get("username"));
                }
                return res;
            case REGISTER:
                return controller.onRegister(pkg);
            case JOIN_ROOM:
                return controller.onJoinRoom(pkg, user);
            case WHOAMI:
                return controller.onWhoAmI(pkg, user);
            default:
                return new Response("Leider konnte ich deine Anfrage nich verstehen");
        }
    }
}
