import User.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TCPServer extends Thread {

    ServerSocket welcomeSocket;
    private int port;
    private InetAddress inetAddress;
    protected Controller controller;
    private boolean running;
    private ArrayList<TCPServerClientHandler> clientHandlers = new ArrayList<>();

    public TCPServer(String ipAddress, int port, Controller controller) {
        this.port = port;
        try {
            this.inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Konnte localhost nicht auflösen");
        }
        this.controller = controller;
    }

    /**
     * For TCPServerClientHandler
     */
    public TCPServer(Controller controller){
        this.controller = controller;
    }


    @Override
    public void run() {
        try {
            running = true;
            welcomeSocket = new ServerSocket(port,50, inetAddress);
            System.out.println("Server Started");

            while (running) {
                TCPServerClientHandler tcpServerClientHandler = new TCPServerClientHandler(welcomeSocket.accept(), controller);
                clientHandlers.add(tcpServerClientHandler);
                tcpServerClientHandler.start();

            }
        } catch (IOException e) {
            throw new RuntimeException("Der Port " + port + " wird von einem anderen Prozess benutzt.");
        }
    }

    public TCPServerClientHandler getClientForUser(User user) {
        return clientHandlers.stream()
                .filter(clientHandler -> user.equals(clientHandler.getUser()))
                .findFirst()
                .orElse(null);
    }
}
