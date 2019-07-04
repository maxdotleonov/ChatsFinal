import Models.Package;
import User.User;
import User.Client;

import java.net.SocketAddress;

public class Controller {
    public Package onLogin(Package pkg, Client client) {
        throw new RuntimeException("onLogin ist nicht implementiert.");
    }

    public Package onLogout(Package pkg, User user) {
        throw new RuntimeException("onLogout ist nicht implementiert.");
    }

    public Package onConnect(Package pkg, SocketAddress remoteAddress) {
        throw new RuntimeException("onConnect ist nicht implementiert.");
    }

    public Package onDisconnect(Package pkg, User user) {
        throw new RuntimeException("onDisconnect ist nicht implementiert.");
    }

    public Package onRegister(Package pkg) {
        throw new RuntimeException("onRegister ist nicht implementiert.");
    }

    public Package onJoinRoom(Package pkg, User user) {
        throw new RuntimeException("onJoinRoom ist nicht implementiert.");
    }

    public Package onInvite(Package pkg, User user) {
        throw new RuntimeException("onInvite ist nicht implementiert.");
    }

    public Package onLeaveRoom(Package pkg, User user) {
        throw new RuntimeException("onLeaveRoom ist nicht implementiert.");
    }
    public Package onWhoAmI(Package pkg, User user) {
        throw new RuntimeException("onWhoAmI ist nicht implementiert.");
    }
}
