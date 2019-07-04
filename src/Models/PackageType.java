package Models;

public enum PackageType {
    LOGIN,
    LOGOUT,
    CONNECT,
    CONNECT_FAILED,
    CONNECT_SUCCESSFULL,
    DISCONNECT,
    REGISTER,
    RESPONSE,
    JOIN_ROOM,
    SOMEONE_JOINED_ROOM,
    EMPTY,
    INVITE,
    INCOMING_INVITE,
    INVITE_SUCCESSFULL,
    INVITE_FAILED,
    LEAVE_ROOM,
    MESSAGE,
    // SERVER TO CLIENT EVENTS
    JOINED_ROOM,
    LEFT_ROOM,
    USER_STATUS,
    REGISTERED,
    LOGGEDIN,
    LOGGEDOUT,
    LOGIN_ERROR,
    ALREADY_LOGGEDIN,
    WHOAMI,
    BYE
    ;

    public static PackageType findByString(String string) {
        try {
            return PackageType.valueOf(string);
        } catch (Exception e) {
            throw new RuntimeException("Methode '"+string+"' ist unbekannt");
        }
    }
}
