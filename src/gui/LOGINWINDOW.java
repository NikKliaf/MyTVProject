package gui;

import javax.swing.*;
import java.awt.*;

import api.Admin;
import api.AdminInfo;
import api.LoginHandler;
import api.Subscriber;

import static gui.INITIALIZE.userData;

public class LOGINWINDOW extends JFrame{

    public JFrame frame = new JFrame();
    public JLabel usernameLabel;
    public JLabel passwordLabel;
    public JTextField usernameField;
    public JPasswordField passwordField;


    public LOGINWINDOW() {
        // Window constructor
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        String showPasswordIconPath = "src/resources/show_password.png";
        ImageIcon showPasswordIcon = new ImageIcon(showPasswordIconPath);
        Image img = showPasswordIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        showPasswordIcon.setImage(img);
        JPanel panel = new JPanel();
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
        JButton registerButton = new JButton("Register");
        JButton showPasswordButton = new JButton(showPasswordIcon);

        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTV Login");
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        //username operations
        usernameLabel.setBounds(130, 50, 100, 30);
        usernameField.setBounds(200, 50, 100, 30);

        usernameField.addActionListener(e -> {
            passwordField.requestFocus();
        });

        //password operations
        passwordLabel.setBounds(130, 80, 100, 30);
        passwordField.setBounds(200, 80, 100, 30);
        passwordField.setEchoChar('*');

        passwordField.addActionListener(e->{
            char[] input = passwordField.getPassword();
            String username = usernameField.getText();
            String password = new String(input);
            LoginHandler loginHandler = new LoginHandler(username, password, userData);
            String message = loginHandler.handleLogin();
            switch(message){
                case "failed":
                    JOptionPane.showMessageDialog(null,"Login Failed");
                    break;
                case "admin":
                    JOptionPane.showMessageDialog(null,"Administrator login successful");
                    frame.dispose();
                    new MAINWINDOW(new Admin(new AdminInfo(userData.getUsersByUsername().get(username).getFirstName(),userData.getUsersByUsername().get(username).getLastName(),username,password)));
                    break;
                case "subscriber":
                    JOptionPane.showMessageDialog(null,"Login successful");
                    frame.dispose();
                    new MAINWINDOW(new Subscriber(userData.getUsersByUsername().get(username).getFirstName(),userData.getUsersByUsername().get(username).getLastName(),username,password,userData.getUsersByUsername().get(username).getFavorites()));
                    break;
            }
        });

        //login button operations
        loginButton.setBounds(200, 150, 100, 25);
        loginButton.addActionListener(e -> {
            char[] input = passwordField.getPassword();
            String username = usernameField.getText();
            String password = new String(input);
            LoginHandler loginHandler = new LoginHandler(username, password, userData);
            String message = loginHandler.handleLogin();
            switch(message){
                case "failed":
                    JOptionPane.showMessageDialog(null,"Login Failed");
                    break;
                case "admin":
                    JOptionPane.showMessageDialog(null,"Administrator login successful");
                    frame.dispose();
                    new MAINWINDOW(new Admin(new AdminInfo(userData.getUsersByUsername().get(username).getFirstName(),userData.getUsersByUsername().get(username).getLastName(),username,password)));
                case "subscriber":
                    JOptionPane.showMessageDialog(null,"Login successful");
                    frame.dispose();
                    new MAINWINDOW(new Subscriber(userData.getUsersByUsername().get(username).getFirstName(),userData.getUsersByUsername().get(username).getLastName(),username,password,userData.getUsersByUsername().get(username).getFavorites()));
            }
        });

        //exit button operations
        exitButton.setBounds(200, 180, 100, 25);
        exitButton.addActionListener(e -> System.exit(0));

        //register button operations
        registerButton.setBounds(200,210,100,25);
        registerButton.addActionListener( e -> {
            frame.dispose();
            new REGISTERWINDOW();
            usernameField.setText("");
            passwordField.setText("");
        });

        //show password button operations
        showPasswordButton.setBounds(300,80,50,30);
        showPasswordButton.setBackground(Color.GRAY);
        showPasswordButton.addActionListener(e -> {
            if(passwordField.getEchoChar() == '*'){
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        //panel operations
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(exitButton);
        panel.add(registerButton);
        panel.add(showPasswordButton);
        frame.add(panel, BorderLayout.CENTER);
    }
}


