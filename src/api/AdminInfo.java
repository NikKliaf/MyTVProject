package api;

import java.io.Serializable;

/**
 * This is a utility class containing admin-specific details.
 */
public class AdminInfo implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;

    /**
     * Constructor for creating an AdminInfo Object
     * @param firstName the first name of the admin
     * @param lastName the last name of the admin
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public AdminInfo(String firstName, String lastName, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /**
     * Admin first name getter
     * @return the admin's first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Admin last name getter
     * @return the admin's last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Admin username getter
     * @return the admin's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Admin password getter
     * @return the admin's password
     */
    public String getPassword() {
        return password;
    }
}
