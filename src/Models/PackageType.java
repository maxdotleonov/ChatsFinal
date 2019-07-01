package Models;

public enum PackageType {
    LOGIN,
    LOGOUT,
    CONNECT,
    DISCONNECT,
    REGISTER,
    RESPONSE,
    JOIN_ROOM,
    EMPTY,
    INVITE,
    LEAVE_ROOM,
    MESSAGE,
    // SERVER TO CLIENT EVENTS
    JOINED_ROOM,
    LEFT_ROOM,
    USER_STATUS,
    REGISTERED,
    LOGGEDIN,
    LOGIN_ERROR,
    ALREADY_LOGGEDIN,
    WHOAMI
    ;

    public static PackageType findByString(String string) {
        try {
            return PackageType.valueOf(string);
        } catch (Exception e) {
            throw new RuntimeException("Methode '"+string+"' ist unbekannt");
        }
    }
}
