import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer extends Thread {

    ServerSocket welcomeSocket;
    private int port;
    protected Controller controller;
    private boolean running;

    public TCPServer(int port, Controller controller) {
        this.port = port;
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
            welcomeSocket = new ServerSocket(port);
            System.out.println("Server Started");

            while (running) {
                new TCPServerClientHandler(welcomeSocket.accept(), controller).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Der Port " + port + " wird von einem anderen Prozess benutzt.");
        }
    }
}
