import Models.Package;
import Models.PackageType;
import Models.Room;
import User.User;

import java.util.HashMap;
import java.util.Map;

public class RoomManagement {
    private Map<String, Room> rooms;

    public RoomManagement(){
        rooms = new HashMap<>();
    }

    public Room joinRoom(String id, User user) {
        Room room;

        if (id == null) {
            room = new Room();
            room.addUser(user);
            rooms.put(room.getId(), room);
        } else {
            room = rooms.get(id);
            if (room == null) {
                return null;
            }
            room.addUser(user);
        }

        return room;
    }

    public Room findRoom(String id) {
        return rooms.get(id);
    }
}
