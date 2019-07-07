import Models.*;
import Models.Package;
import User.User;
import User.Client;

import java.net.SocketAddress;
import java.security.MessageDigest;

import static Models.PackageType.*;

public class Main extends Controller {

    private UDPServer udpServer;
    private TCPServer tcpServer;
    private UserManagement userManagement;
    private RoomManagement roomManagement;

    private Main() {
        tcpServer = new TCPServer("127.0.0.1",6666, this);
        udpServer = new UDPServer("127.0.0.1", 8888);
        userManagement = new UserManagement();
        roomManagement = new RoomManagement();
        tcpServer.start();
        udpServer.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    @Override
    public Package onLogin(Package pkg, Client client) {
        if (pkg.get("username") == null || pkg.get("password") == null) {
            return new Response("Username und Passwort dürfen nicht leer sein");
        }
        int loggedIn = userManagement.login(pkg.get("username"), pkg.get("password"), client);
        if (loggedIn == 1) {
            return new Package(PackageType.LOGGEDIN);
        } else if (loggedIn == 2) {
            return new Package(PackageType.ALREADY_LOGGEDIN);
        }
        return new Package(PackageType.LOGIN_ERROR, "Username oder Passwort falsch");
    }

    /**
     * Client übermittelt username,password und password_repeat werden als md5 hash gespeichert.
     * @param pkg
     * @return
     */
    @Override
    public Package onRegister(Package pkg) {
        if (pkg.get("username") == null || pkg.get("password") == null || pkg.get("password_repeat") == null) {
            return new Response("Username und Passwort dürfen nicht leer sein");
        }
        if (!pkg.get("password").equals(pkg.get("password_repeat"))){
            return new Response("Passwörter stimmen nicht überein.");
        }
        //todo: check if user already exists, if so return REGISTER_FAILED
        // userManagement.findUser(pkg.get("username")) if not null -> user exists return ERROR
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
        Room room = roomManagement.joinRoom(pkg.get("room_id"), user);
        if (room.size() > 1) {
            Package introPkg = new Package(PackageType.SOMEONE_JOINED_ROOM);
            introPkg.put("username", user.getUsername());
            room.getUsers().forEach(u -> udpServer.sendToUser(u, introPkg));
        }
        Package resPkg = new Package(PackageType.JOINED_ROOM);
        resPkg.put("room_id", room.getId());
        return resPkg;
    }

    @Override
    public Package onLeaveRoom(Package pkg, User user) {
        Room room = roomManagement.findRoom(pkg.get("room_id"));
        if (room == null) {
            return new ErrorResponse("Dieser Raum existiert nicht");
        }
        if (!room.getUsers().contains(user)) {
            return new ErrorResponse("Du bist nicht in diesem Raum");
        }
        room.leaveRoom(user);
        Package introPkg = new Package(PackageType.SOMEONE_LEFT_ROOM);
        introPkg.put("username", user.getUsername());
        room.getUsers().forEach(u -> udpServer.sendToUser(u, introPkg));
        return new Package(LEFT_ROOM);
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
    public Package onInvite(Package pkg, User user) {
        if (pkg.get("username") == null || pkg.get("room_id") == null) {
            return new Package(PackageType.INVITE_FAILED, "Username des anderen Users und room_id müssen gesetzt sein");
        }
        Room room = roomManagement.findRoom(pkg.get("room_id"));
        if (room == null) {
            return new Package(PackageType.INVITE_FAILED, "Room id nicht gültig");
        }
        User userToInvite = userManagement.findUser(pkg.get("username"));
        if (userToInvite == null) {
            return new Package(PackageType.INVITE_FAILED, "Der einzuladene User ist ungültig");
        }
        if (!room.hasUser(user)) {
            return new Package(PackageType.INVITE_FAILED, "Du bist selber nicht in dem Raum");
        }
        Package pkgToSend = new Package(PackageType.INCOMING_INVITE);
        pkgToSend.put("room_id", room.getId());
        tcpServer.getClientForUser(userToInvite).send(pkgToSend);
        return new Package(PackageType.INVITE_SUCCESSFULL);
    }

    @Override
    public Package onWhoAmI(Package pkg, User user) {
        return new Response("You are " + user.getUsername());
    }

    @Override
    public Package onConnect(Package pkg, SocketAddress address) {
        if(pkg.get("udp_port") == null) {
            return new Package(CONNECT_FAILED, "Udp port wurde nicht an den Server weitergegeben");
        }
        return new Package(CONNECT_SUCCESSFULL);
    }

    @Override
    public Package onMessage(Package pkg, User user) {
        //todo: which room id get from pkg ?
        //todo: roomManagement getRoom ?
        //todo: user in room ?
        //todo: pkg.set("username",user.getUsername())
        //todo: forEach userToSendTo -> udpServer.sendToUser(pkg,userToSendTo) Zeile: 87
        return new Package(MESSAGE_SENT);
    }
}
