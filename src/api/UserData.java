package api;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages user data including storing, loading, and authenticating users.
 */
public class UserData implements Serializable {
    private final String fileName;
    private Map<String, User> usersByUsername;

    /**
     * Constructor of a UserData object with the specified file name.
     * @param fileName The name of the file to store user data.
     */
    public UserData(String fileName) {
        this.fileName = fileName;
        this.usersByUsername = new HashMap<>();
        loadUsers();
    }
    /**
     * Adds a new subscriber.
     * @param sub the subscriber object to be added.
     */
    public void addUser(Subscriber sub) {
        usersByUsername.put(sub.getUsername(), sub);
        saveUsers();
    }

    /**
     * Adds a new admin, will only be used twice
     * @param admin the admin object to be added.
     */
    public void addUser(Admin admin){
        usersByUsername.put(admin.getUsername(), admin);
        saveUsers();
    }
    /**
     * Saves the user data to the file.
     */
    void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(usersByUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns the users data by username.
     * @return a map of Users by username
     */
    public Map<String, User> getUsersByUsername() {
        return usersByUsername;
    }
    /**
     * Loads the user data from the file.
     */
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            usersByUsername = (HashMap<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Adds a favorite video content to the user's favorites in the file.
     * @param videoContent the video content to be added to the user's favorites.
     */
    public void addToFavorites(VideoContent videoContent, Subscriber sub) {
        Subscriber user = (Subscriber) usersByUsername.get(sub.getUsername());
        user.addToFavorites(videoContent);
        saveUsers();
    }
    /**
     * Removes a favorite video content from the user's favorites in the file.
     * @param videoContent the video content to be removed from the user's favorites.
     */
    public void removeFromFavorites(VideoContent videoContent, Subscriber sub) {
        sub.getFavorites().remove(videoContent);
        saveUsers();
    }
}
