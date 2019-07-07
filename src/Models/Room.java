package Models;

import User.User;

import java.util.ArrayList;
import java.util.UUID;

public class Room {

    private String id;
    private ArrayList<User> users = new ArrayList<User>();

    public Room() {
        setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public int size() {
        return users.size();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean hasUser(User user) {
        return users.contains(user);
    }

    public void leaveRoom(User user) {
        users.remove(user);
    }
}
