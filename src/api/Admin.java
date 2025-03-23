package api;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an Admin object that implements the User interface
 * with a firstName, lastName, username and password.
 * NOTE: only two amdministrators will be created in this program
 */
public class Admin implements User, Serializable {
    private final AdminInfo adminInfo;
    /**
     * Constructor for creating an Admin object
     * @param adminInfo the AdminInfo of the admin
     */
    public Admin(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }

    /**
     * Admin first name getter
     * @return the first name of the admin
     */
    public String getFirstName() {
        return adminInfo.getFirstName();
    }
    /**
     * Admin last name getter
     * @return the last name of the admin
     */
    public String getLastName() {return adminInfo.getLastName();}
    /**
     * Admin username getter
     * @return the username of the admin
     */
    public String getUsername() {
        return adminInfo.getUsername();
    }
    /**
     * Admin password getter
     * @return the password of the admin
     */
    public String getPassword() {
        return adminInfo.getPassword();
    }
    /**
     * Admin isAdmin getter
     * @return always returns true for Admin
     */
    public boolean isAdmin() {
        return true;
    }
    /**
     * Admin favorites getter
     * @return always returns null for Admin
     */
    public List<VideoContent> getFavorites() {
        return null;
    }
}
