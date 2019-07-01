import java.io.IOException;
import java.net.DatagramSocket;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private boolean running;

    public UDPServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Der Port " + port + " wird von einem anderen Prozess benutzt.");
        }
    }


    @Override
    public void run() {
        running = true;
        System.out.println("UDP Server Started");

        while (running) {

        }
    }

}
