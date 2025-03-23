package gui;
import api.Movie;
import api.MovieAddHandler;
import api.Review;
import api.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import static gui.INITIALIZE.movieData;

public class ADDMOVIEWINDOW extends JFrame{
    public JFrame frame = new JFrame();
    public ADDMOVIEWINDOW(User user, DefaultListModel<String> myList, JList<String> movieList, Movie existingMovie) {
        JPanel panel = new JPanel();
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
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

        //title label operations
        JLabel titleLabel = new JLabel("Add Movie");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(80, 50, 150, 30);

        //user label creation
        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        //logout button operations
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(850,25,100,25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        //back button operations
        JButton backButton = new JButton("Back");
        backButton.setBounds(80,500,100,30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MOVIESWINDOW(user);
        });

        //Add Movie Text Fields
        //Add Title
        JTextField titleField = new JTextField(" Add Title");
        titleField.setBounds(80, 100, 300, 40);
        titleField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(titleField, " Add Title");

        JButton ageButton = new JButton(" Suitable for Minors");
        ageButton.setBounds(80, 140, 300, 40);
        ageButton.addActionListener(e -> {
            if (ageButton.isSelected()) {
                ageButton.setSelected(false);
                ageButton.setText(" Not Suitable for Minors");
            } else {
                ageButton.setSelected(true);
                ageButton.setText(" Suitable for Minors");
            }
        });

        JTextField firstReleaseField = new JTextField(" Add First Release Year");
        firstReleaseField.setBounds(80, 180, 300, 40);
        firstReleaseField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(firstReleaseField, " Add First Release Year");

        JTextField durationField = new JTextField(" Add Duration");
        durationField.setBounds(80, 220, 300, 40);
        durationField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(durationField, " Add Duration");

        JComboBox<String> genreField = new JComboBox<>(new String[]{"<html><font color ='gray'>Categories: </font></body></html>","Horror", "Drama", "Sci-Fi", "Comedy", "Adventure"});
        genreField.setBounds(80, 260, 300, 40);

        JTextField directorField = new JTextField(" Add Director");
        directorField.setBounds(80, 300, 300, 40);
        directorField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(directorField, " Add Director");


        JTextArea descriptionField = new JTextArea(" Add Description");
        descriptionField.setBounds(600, 100, 300, 150);
        descriptionField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(descriptionField , " Add Description");
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);


        JTextArea protagonistField = new JTextArea(" Add Protagonists");
        protagonistField.setBounds(600, 300, 300, 150);
        protagonistField.setForeground(Color.LIGHT_GRAY);
        textFieldFocusGained(protagonistField, " Add Protagonists");
        protagonistField.setLineWrap(true);
        protagonistField.setWrapStyleWord(true);

        Movie index;
        boolean exists;
        if(existingMovie != null){
            index = existingMovie;
            exists = true;
        } else {
            index = null;
            exists = false;
        }

        if(exists){
            titleField.setText(index.getTitle());
            titleField.setForeground(Color.BLACK);
            ageButton.setText(index.isSuitableForMinors()? " Suitable for minors":" Not Suitable for minors");
            firstReleaseField.setText(Integer.toString(index.getReleaseYear()));
            firstReleaseField.setForeground(Color.BLACK);
            durationField.setText(Integer.toString(index.getDuration()));
            durationField.setForeground(Color.BLACK);
            for(int i = 0; i < genreField.getItemCount(); i++){
                if(genreField.getItemAt(i).equals(index.getCategory())){
                    genreField.setSelectedIndex(i);
                }
            }
            directorField.setText(index.getDirector());
            directorField.setForeground(Color.BLACK);
            descriptionField.setText(index.getDescription());
            descriptionField.setForeground(Color.BLACK);
            protagonistField.setText(index.getProtagonists());
            protagonistField.setForeground(Color.BLACK);
        }

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.setBounds(800,500,100,30);
        addMovieButton.addActionListener(e -> {
            List<Review> reviews = new ArrayList<>();
            MovieAddHandler handler = new MovieAddHandler(titleField, ageButton, firstReleaseField, durationField, genreField, directorField, descriptionField, protagonistField,reviews,movieData,exists,index);
            String message = handler.handleAdd();
            switch(message) {
                case "fill":
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                    break;
                case "added":
                    JOptionPane.showMessageDialog(null, "Movie Added Successfully");
                    frame.dispose();
                    new MOVIESWINDOW(user);
                    break;
                case "exists":
                    JOptionPane.showMessageDialog(null, "Movie Already Exists");
                    break;
                case "duration exception":
                    JOptionPane.showMessageDialog(null, "Please enter a valid duration");
                    break;
                case "year exception":
                    JOptionPane.showMessageDialog(null, "Please enter a valid year");
                    break;
            }
        });


        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(titleField);
        panel.add(ageButton);
        panel.add(firstReleaseField);
        panel.add(durationField);
        panel.add(genreField);
        panel.add(directorField);
        panel.add(descriptionField);
        panel.add(protagonistField);
        panel.add(addMovieButton);
        frame.add(panel, BorderLayout.CENTER);
    }

    private void textFieldFocusGained(JTextField TF , String msg) {
        TF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(TF.getText().equals(msg)){
                    TF.setText("");
                    TF.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(TF.getText().isEmpty()) {
                    TF.setForeground(Color.LIGHT_GRAY);
                    TF.setText(msg);
                }
            }
        });
    }
    private void textFieldFocusGained(JTextArea TF , String msg) {
        TF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(TF.getText().equals(msg)){
                    TF.setText("");
                    TF.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(TF.getText().isEmpty()) {
                    TF.setForeground(Color.LIGHT_GRAY);
                    TF.setText(msg);
                }
            }
        });
    }


}

