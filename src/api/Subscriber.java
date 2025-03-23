package api;
import java.io.Serializable;
import java.util.List;

/**
 * Subscriber class that implements the user interface with basic information including
 * first name, last name, username, password, and admin status.
 */
public class Subscriber implements Serializable, User{
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private List<VideoContent> favorites;

    /**
     * Constructor of a Subscriber object with the provided information.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param favorites The list of videos the user has added to favorites.
     */
    public Subscriber(String firstName, String lastName, String username, String password, List<VideoContent> favorites) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.favorites = favorites;
    }
    /**
     * Returns the first name of the subscriber.
     * @return The first name of the subscriber.
     */
    @Override
    public String getFirstName(){return firstName;}
    /**
     * Returns the last name of the subscriber.
     * @return The last name of the subscriber.
     */
    @Override
    public String getLastName(){return lastName;}
    /**
     * Returns the username of the subscriber.
     * @return The username of the subscriber.
     */
    @Override
    public String getUsername(){
        return username;
    }
    /**
     * Returns the password of the subscriber.
     * @return The password of the subscriber.
     */
    @Override
    public String getPassword(){return password;}
    /**
     * Admin status getter.
     * @return always returns false for subscribers
     */
    @Override
    public boolean isAdmin(){
        return false;
    }
    /**
     * Returns the list of videos the subscriber has added to favorites.
     * @return The list of videos the subscriber has added to favorites.
     */
    public List<VideoContent> getFavorites(){return favorites;}

    /**
     * Adds a video to the list of the subscriber's favorites.
     * @param video the VideoContent object to be added to favorites.
     */
    public void addToFavorites(VideoContent video){
        favorites.add(video);
    }

}
