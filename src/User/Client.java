package User;

public class Client {
    private String ipAddress;

    public Client(String ipAddress) {
        setIpAddress(ipAddress);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
