package api;

import gui.LOGINWINDOW;
import gui.MAINWINDOW;

import javax.swing.*;
import java.util.List;


public class LoginHandler {
    private UserData userData;
    private String username;
    private String password;
    /**
     * Handles the login process based on the provided username and password.
     * @param username the username entered by the user
     * @param password the password entered by the user
     */
    public LoginHandler(String username, String password, UserData userData) {
        this.userData = userData;
        this.username = username;
        this.password = password;
    }
    public String handleLogin() {
        if (isAdminLogin(username, password)) {
            return handleAdminLogin();
        } else if (isValidLogin(username, password)) {
            return handleSubscriberLogin();
        } else {
            return "failed";
        }
    }
    /**
     * Validates whether the username and password combination is valid for a subscriber login.
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if the login is valid, otherwise false
     */
    private boolean isValidLogin(String username, String password) {
        return userData.getUsersByUsername().containsKey(username)
                && userData.getUsersByUsername().get(username).getPassword().equals(password);
    }

    /**
     * Validates whether the username and password combination is valid for an admin login.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if the login is valid, otherwise false
     */
    boolean isAdminLogin(String username, String password) {
        return (username.equals("admin1") && password.equals("password1")) ||
                (username.equals("admin2") && password.equals("password2"));
    }

    /**
     * Handles the login for an administrator.
     */
    private String handleAdminLogin() {
        return "admin";
    }

    /**
     * Handles the login for a subscriber.
     */
    private String handleSubscriberLogin() {
        return "subscriber";
    }
}
