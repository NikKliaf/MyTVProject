package api;

import javax.swing.*;
import java.util.ArrayList;

public class RegistrationHandler {
    public boolean flag = false;
    private String firstName;
    private String lastName;
    private String username;
    private char[] pw;
    private char[] cpw;
    private UserData userData;
    public RegistrationHandler(String firstName, String lastName, String username, char[] pw, char[] cpw, UserData userData){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pw = pw;
        this.cpw = cpw;
        this.userData = userData;
    }

    /**
     * Registers a user based on the provided information.
     */
    public String registerUser(){
        String password = new String(pw);
        String confirmPassword = new String(cpw);

        if(userData.getUsersByUsername().containsKey(username)) {
            return "Username already exists";
        } else if (username.isEmpty()) {
            return "Please enter a Username";
        } else if (password.isEmpty()) {
            return "Please enter a Password";
        } else if (!password.equals(confirmPassword)) {
            return "Passwords Do Not Match";
        } else {
            userData.addUser(new Subscriber(firstName, lastName, username, password, new ArrayList<>()));
            flag = true;
            return "Registration Successful";
        }
    }
}
