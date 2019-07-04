import Models.PackageType;
import Models.Package;
import Models.Response;
import User.User;

import java.security.MessageDigest;

public class Main extends Controller {

    private UDPServer udpServer;
    private TCPServer tcpServer;
    private UserManagement userManagement;
    private RoomManagement roomManagement;

    private Main() {
        tcpServer = new TCPServer(6666, this);
        udpServer = new UDPServer(8888);
        userManagement = new UserManagement();
        roomManagement = new RoomManagement();
        tcpServer.start();
        udpServer.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    @Override
    public Package onLogin(Package pkg) {
        if (pkg.get("username") == null || pkg.get("password") == null) {
            return new Response("Username und Passwort dürfen nicht leer sein");
        }
        int loggedIn = userManagement.login(pkg.get("username"), pkg.get("password"), pkg.get("client_ip"));
        if (loggedIn == 1) {
            return new Package(PackageType.LOGGEDIN);
        } else if (loggedIn == 2) {
            return new Package(PackageType.ALREADY_LOGGEDIN);
        }
        return new Package(PackageType.LOGIN_ERROR, "Username oder Passwort falsch");
    }

    @Override
    public Package onRegister(Package pkg) {
        if (pkg.get("username") == null || pkg.get("password") == null || pkg.get("password_repeat") == null) {
            return new Response("Username und Passwort dürfen nicht leer sein");
        }
        if (!pkg.get("password").equals(pkg.get("password_repeat"))){
            return new Response("Passwörter stimmen nicht überein.");
        }
        User user = new User();
        user.setUsername(pkg.get("username"));
        user.setPasswordHash(getMD5Hash(pkg.get("password")));
        userManagement.registerUser(user);
        return new Package(PackageType.REGISTERED);
    }

    private String getMD5Hash(String string) {
        try {
            byte[] bytesOfMessage = string.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);

            return thedigest.toString();
        }catch (Exception e) {
            throw new RuntimeException("Konnte keinen MD5 Hash erstellen");
        }
    }

    @Override
    public Package onJoinRoom(Package pkg, User user) {
        String roomId = roomManagement.joinRoom(pkg.get("room_id"), user);
        return new Response("");
    }

    @Override
    public Package onLogout(Package pkg, User user) {
        userManagement.logout(user);
        return new Package(PackageType.LOGGEDOUT);
    }
    @Override
    public Package onDisconnect(Package pkg, User user) {
        if (user.getUsername() != null)  {
            userManagement.logout(user);
        }
        return new Package(PackageType.BYE);
    }

    @Override
    public Package onWhoAmI(Package pkg, User user) {
        return new Response("You are " + user.getUsername());
    }
}
