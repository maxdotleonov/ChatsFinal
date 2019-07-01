import User.User;
import User.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagement {
    private List<User> users;
    private List<User> activeUsers;

    public UserManagement() {
        activeUsers = new ArrayList<>();
        users = new ArrayList<>();
        //todo: user basis laden
    }

    public void putUser(User user) {
        activeUsers.add(user);
    }

    public void deleteUser(User user) {
        activeUsers.remove(user);
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void registerUser(User user){
        users.add(user);
        //todo: user permanent speichern
    }

    /**
     *
     * @param username
     * @param password
     * @return 1=eingeloggt,0=nicht einlogbar,2=schon eingeloggt
     */
    public int login(String username, String password, String ipAddress) {
        List<User> userToLogin = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList());
        if (userToLogin.size() == 0) {
            return 0;
        } else if (userToLogin.size() == 1) {
            User user = userToLogin.get(0);
            user.setClient(new Client(ipAddress));
            if (activeUsers.contains(user)) {
                return 2;
            }
            //todo: check password
            activeUsers.add(user);
            return 1;
        } else {
            throw new RuntimeException("Wir haben mehrere User mit dem gleichen Benutzername.");
        }
    }
}
