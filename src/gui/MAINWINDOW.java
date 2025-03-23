package gui;
import api.Subscriber;
import api.User;

import javax.swing.*;
import java.awt.*;

public class MAINWINDOW extends JFrame{
    public JFrame frame = new JFrame();
    public MAINWINDOW(User user) {
        ImageIcon icon = new ImageIcon("src/resources/tv.png");
        frame.setIconImage(icon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("MyTv APP");
        JButton moviesButton = new JButton("Movies");
        JButton seriesButton = new JButton("Series");
        //JButton adminButton = new JButton("Admin");
        JButton exitButton = new JButton("Exit");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTv");
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        icon.setImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(450, 100, icon.getIconWidth(), icon.getIconHeight());
        panel.add(iconLabel);

        //title label operations
        titleLabel.setBounds(80, 50, 100, 30);

        //user label creation and admin checker
        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        //logout button operations
        logoutButton.setBounds(850,25,100,25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        //movies button operations
        moviesButton.setBounds(80, 100, 100, 25);
        moviesButton.addActionListener(e -> {
            frame.dispose();
            new MOVIESWINDOW(user);
        });

        //series button operations
        seriesButton.setBounds(80, 150, 100, 25);
        seriesButton.addActionListener(e->{
            frame.dispose();
            new ALLSERIESWINDOW(user);
        });

        //favorites button operations
        if(!user.isAdmin()){
            JButton favoritesButton = new JButton("Favorites");
            favoritesButton.setBounds(80, 200, 100, 25);
            panel.add(favoritesButton);
            favoritesButton.addActionListener(e->{
                frame.dispose();
                new FAVORITESWINDOW((Subscriber)user);
            });
        }


        //exit button operations
        exitButton.setBounds(80, 300, 100, 25);
        exitButton.addActionListener(e -> System.exit(0));

        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(moviesButton);
        panel.add(seriesButton);
        //panel.add(adminButton);
        panel.add(exitButton);
        panel.add(logoutButton);
        frame.add(panel, BorderLayout.CENTER);
    }
}
