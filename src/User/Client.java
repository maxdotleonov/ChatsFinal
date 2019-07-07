package User;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Client {
    private InetAddress tcpIpAddress;
    private int tcpPort;
    private int udpPort;

    public Client(SocketAddress address) {
        setTcpIpAddress(address);
    }

    public Client() {}

    public InetAddress getTcpIpAddress() {
        return tcpIpAddress;
    }

    public void setTcpIpAddress(SocketAddress address) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
        setIpAddress(inetSocketAddress.getAddress());
        setTcpPort(inetSocketAddress.getPort());
    }
    public void setIpAddress(InetAddress ipAddress) {
        this.tcpIpAddress = ipAddress;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }
}
