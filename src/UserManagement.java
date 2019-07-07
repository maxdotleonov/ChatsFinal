import User.User;
import User.Client;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagement {
    private List<User> users;
    private List<User> activeUsers;
    private final String usersFilename = "users.xml";

    public UserManagement() {
        activeUsers = new ArrayList<>();
        users = loadUserArrayFromPersistence();
    }

    public void activateUser(User user) {
        activeUsers.add(user);
    }

    public void deactivateUser(User user) {
        activeUsers.remove(user);
    }

    public void addUser(User user) {
        users.add(user);
        persistUserArray();
    }

    public void persistUserArray(){
        XMLHandler.writeXML(usersFilename, users);
    }

    public ArrayList<User> loadUserArrayFromPersistence(){
        try {
            return (ArrayList<User>) XMLHandler.readXML(usersFilename);
        }catch (RuntimeException e) {
            System.out.println("Es gibt keine '"+usersFilename+"' von der ich die bestehenden User laden kann.");
            return new ArrayList<User>();
        }
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public User findUser(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void registerUser(User user){
        addUser(user);
    }

    /**
     *
     * @param username
     * @param password
     * @return 1=eingeloggt,0=nicht einlogbar,2=schon eingeloggt
     */
    public int login(String username, String password, Client client) {
        List<User> userToLogin = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList());
        if (userToLogin.size() == 0) {
            return 0;
        } else if (userToLogin.size() == 1) {
            User user = userToLogin.get(0);
            if (activeUsers.contains(user)) {
                return 2;
            }
            //todo: check password
            user.setClient(client);
            activeUsers.add(user);
            return 1;
        } else {
            throw new RuntimeException("Wir haben mehrere User mit dem gleichen Benutzername.");
        }
    }

    public void logout(User user) {
        activeUsers.remove(user);
    }
}
