import Models.Package;
import User.User;
import User.Client;

import java.net.SocketAddress;

public abstract class Controller {
    /**
     * LOGIN:username=max;password=test
     * @param pkg
     * @param client
     * @return
     */
    public Package onLogin(Package pkg, Client client) {
        throw new RuntimeException("onLogin ist nicht implementiert.");
    }

    /**
     * LOGOUT:
     * @param pkg
     * @param user
     * @return
     */
    public Package onLogout(Package pkg, User user) {
        throw new RuntimeException("onLogout ist nicht implementiert.");
    }

    /**
     * CONNECT:udp_port= (udp port den der client vergibt)
     * @param pkg
     * @param remoteAddress
     * @return
     */
    public Package onConnect(Package pkg, SocketAddress remoteAddress) {
        throw new RuntimeException("onConnect ist nicht implementiert.");
    }

    /**
     * DISCONNECT:
     * @param pkg
     * @param user
     * @return
     */
    public Package onDisconnect(Package pkg, User user) {
        throw new RuntimeException("onDisconnect ist nicht implementiert.");
    }

    /**
     * REGISTER:username=max;password=test;password_repeat=test
     * @param pkg
     * @return
     */
    public Package onRegister(Package pkg) {
        throw new RuntimeException("onRegister ist nicht implementiert.");
    }

    /**
     * JOIN_ROOM: || JOIN_ROOM:room_id=
     * ohne parameter um einen neuen Raum zu öffnen und mit parameter um einem bestehnden raum beizutreten
     * @param pkg
     * @param user
     * @return
     */
    public Package onJoinRoom(Package pkg, User user) {
        throw new RuntimeException("onJoinRoom ist nicht implementiert.");
    }

    /**
     * INVITE:username=john;room_id=abc
     * @param pkg
     * @param user
     * @return
     */
    public Package onInvite(Package pkg, User user) {
        throw new RuntimeException("onInvite ist nicht implementiert.");
    }

    /**
     * LEAVE_ROOM:room_id=
     * @param pkg
     * @param user
     * @return
     */
    public Package onLeaveRoom(Package pkg, User user) {
        throw new RuntimeException("onLeaveRoom ist nicht implementiert.");
    }

    /**
     * WHOAMI:
     * @param pkg
     * @param user
     * @return
     */
    public Package onWhoAmI(Package pkg, User user) {
        throw new RuntimeException("onWhoAmI ist nicht implementiert.");
    }

    /**
     * MESSAGE:room_id=abc;message=deine nachricht
     * @param pkg
     * @param user
     * @return
     */
    public Package onMessage(Package pkg, User user) {
        throw new RuntimeException("onMessage ist nicht implementiert");
    }
}
