import Models.Package;
import User.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private InetAddress inetAddress;
    private int port;

    public UDPServer(String ipAddress, int port) {
        this.port = port;
        try {
            this.inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Konnte localhost nicht auflösen");
        }
        try {
            socket = new DatagramSocket(port, inetAddress);
        } catch (IOException e) {
            throw new RuntimeException("Der Port " + port + " wird von einem anderen Prozess benutzt.");
        }
    }


    @Override
    public void run() {
        System.out.println("UDP Server Started");
    }

    public void sendToUser(User user, Package pkg) {
        byte[] data;
        data = pkg.toString().getBytes();
        DatagramPacket req = new DatagramPacket(data, data.length, user.getClient().getTcpIpAddress(), user.getClient().getUdpPort());
        try{
            socket.send(req);
        }catch (IOException e) {
            throw new RuntimeException("UDP Nachricht konnte nicht versand werden");
        }
    }

}
