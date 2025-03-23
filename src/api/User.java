package api;

import java.util.List;

/**
 * The User interface represents a user of the system.
 */
public interface User {

    /**
     * Gets the first name of the user.
     * @return the first name of the user
     */
    public String getFirstName();

    /**
     * Gets the last name of the user.
     * @return the last name of the user
     */
    public String getLastName();

    /**
     * Gets the username of the user.
     * @return the username of the user
     */
    public String getUsername();

    /**
     * Gets the password of the user.
     * @return the password of the user
     */
    public String getPassword();

    /**
     * Checks if the user has administrative privileges.
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin();

    /**
     * Gets the list of video content marked as favorites by the user.
     * @return the list of favorite video content
     */
    List<VideoContent> getFavorites();
}
