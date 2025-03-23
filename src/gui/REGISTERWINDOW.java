package gui;
import api.RegistrationHandler;

import javax.swing.*;
import java.awt.*;

import static gui.INITIALIZE.userData;


public class REGISTERWINDOW extends JFrame {
    public REGISTERWINDOW() {
        JFrame frame = new JFrame();
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        JPanel panel = new JPanel();
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPasswordField confirmPasswordField = new JPasswordField(10);
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");
        String showPasswordIconPath = "src/resources/show_password.png";
        ImageIcon showPasswordIcon = new ImageIcon(showPasswordIconPath);
        Image img = showPasswordIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        showPasswordIcon.setImage(img);
        JButton showPasswordButton = new JButton(showPasswordIcon);

        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTV Register");
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        //first name operations
        firstNameLabel.setBounds(130, 30, 100, 30);
        firstNameField.setBounds(200, 30, 100, 30);
        firstNameField.addActionListener(e -> {
            lastNameField.requestFocus();
        });

        //last name operations
        lastNameLabel.setBounds(130, 60, 100, 30);
        lastNameField.setBounds(200, 60, 100, 30);
        lastNameField.addActionListener(e -> {
            usernameField.requestFocus();
        });

        //username operations
        usernameLabel.setBounds(130, 90, 100, 30);
        usernameField.setBounds(200, 90, 100, 30);
        usernameField.addActionListener(e -> {
            passwordField.requestFocus();
        });

        //password operations
        passwordLabel.setBounds(130, 120, 100, 30);
        passwordField.setBounds(200, 120, 100, 30);
        passwordField.setEchoChar('*');
        passwordField.addActionListener(e -> {
            confirmPasswordField.requestFocus();
        });

        //confirm password operations
        confirmPasswordLabel.setBounds(80, 150, 120, 30);
        confirmPasswordField.setBounds(200, 150, 100, 30);
        confirmPasswordField.setEchoChar('*');
        confirmPasswordField.addActionListener(e -> {
            RegistrationHandler registrationHandler = new RegistrationHandler(firstNameField.getText(), lastNameField.getText(),usernameField.getText(), passwordField.getPassword(), confirmPasswordField.getPassword(), userData);
            JOptionPane.showMessageDialog(null, registrationHandler.registerUser());
            if (registrationHandler.flag){
                frame.dispose();
                new LOGINWINDOW();
            }
        });

        //register button operations
        registerButton.setBounds(200,200,100,25);
        registerButton.addActionListener( e -> {
            RegistrationHandler registrationHandler = new RegistrationHandler(firstNameField.getText(), lastNameField.getText(),usernameField.getText(), passwordField.getPassword(), confirmPasswordField.getPassword(), userData);
            JOptionPane.showMessageDialog(null, registrationHandler.registerUser());
            if (registrationHandler.registerUser().equals("Registration Successful")) {
                frame.dispose();
                new LOGINWINDOW();
            }
        });
        //back button operations
        backButton.setBounds(200, 230, 100, 25);
        backButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        //show password button operations
        showPasswordButton.setBounds(300,120,50,30);
        showPasswordButton.setBackground(Color.GRAY);
        showPasswordButton.addActionListener(e -> {
            if(passwordField.getEchoChar() == '*'){
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        //panel operations
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(registerButton);
        panel.add(backButton);
        panel.add(showPasswordButton);
        frame.add(panel, BorderLayout.CENTER);
    }
}
