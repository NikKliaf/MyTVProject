package gui;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import static gui.INITIALIZE.seriesData;

public class ADDSERIESWINDOW extends JFrame{
    public JFrame frame = new JFrame();
    public static List<String> seasonsStr = new ArrayList<>();
    public JComboBox<String> seasonsBox = new JComboBox<>();
    public  JPanel panel = new JPanel();
    public List<Season> seasons = new ArrayList<>();

    public ADDSERIESWINDOW(User user, DefaultListModel<String> myList, JList<String> seriesList, Series existingSeries, List<Season> seasons) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        this.seasons = seasons;
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
        JLabel titleLabel = new JLabel("Add Series");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(80, 50, 150, 30);

        //user label creation and admin checker
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
            seasonsStr = new ArrayList<>();
            new ALLSERIESWINDOW(user);
        });

        //Add Series Text Fields
        JTextField titleField = new JTextField(" Add Title");
        titleField.setBounds(80, 100, 300, 40);
        titleField.setForeground(Color.GRAY);
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

        JComboBox<String> genreField = new JComboBox<>(new String[]{"<html><font color ='gray'>Categories: </font></body></html>","Horror", "Drama", "Sci-Fi", "Comedy", "Adventure"});
        genreField.setBounds(80, 180, 300, 40);

        List<Season> seasons1;
        if(existingSeries != null){
            seasons1 = existingSeries.getSeasons();
        } else {
            seasons1 = new ArrayList<>();
        }
        JButton addSeasonButton = new JButton(" Add Season");
        addSeasonButton.setBounds(80,220,300,40);
        addSeasonButton.addActionListener(e -> {
            frame.dispose();
            new ADDSEASONWINDOW(user,this,seasons1, myList, seriesList,existingSeries,false,null);
            updateSeasonsComboBox();
        });

        if(seasonsStr.isEmpty()) {
            seasonsStr.add("<html><font color ='gray'>Seasons: </font></body></html>");
        }


        JTextArea descriptionField = new JTextArea(" Add Description");
        descriptionField.setBounds(600, 100, 300, 150);
        descriptionField.setForeground(Color.GRAY);
        textFieldFocusGained(descriptionField, " Add Description");

        JTextArea protagonistField = new JTextArea(" Add Protagonists");
        protagonistField.setBounds(600, 300, 300, 150);
        protagonistField.setForeground(Color.GRAY);
        textFieldFocusGained(protagonistField, " Add Protagonists");

        Series index;
        boolean exists;
        List<Integer> existingSeasonNumbers = new ArrayList<>();
        if(existingSeries != null){
            index = existingSeries;
            exists = true;
            List<Season> existingSeasons = index.getSeasons();
            existingSeasonNumbers = new ArrayList<>();
            for(Season season : existingSeasons){
                existingSeasonNumbers.add(season.getSeasonNumber());
            }
        } else {
            index = null;
            exists = false;
        }

        if(exists){
            titleField.setText(index.getTitle());
            titleField.setForeground(Color.BLACK);
            ageButton.setText(index.isSuitableForMinors()? " Suitable for minors":" Not Suitable for minors");
            for(int i = 0; i < genreField.getItemCount(); i++){
                if(genreField.getItemAt(i).equals(index.getCategory())){
                    genreField.setSelectedIndex(i);
                }
            }
            descriptionField.setText(index.getDescription());
            descriptionField.setForeground(Color.BLACK);
            protagonistField.setText(index.getProtagonists());
            protagonistField.setForeground(Color.BLACK);
            for(Integer seasonNum : existingSeasonNumbers){
                if(!seasonsStr.contains("Season: "+ seasonNum)) {
                    seasonsStr.add("Season: " + seasonNum);
                }
            }
        }
        String[] seasonsArray = seasonsStr.toArray(new String[0]);
        seasonsBox = new JComboBox<>(seasonsArray);
        seasonsBox.setBounds(80,280,300,40);
        DefaultComboBoxModel<String> newSeasonsModel = new DefaultComboBoxModel<>(seasonsArray);
        seasonsBox.setModel(newSeasonsModel);
        panel.revalidate();
        panel.repaint();

        //Seasons List Box selection operations
        seasonsBox.addActionListener(e -> {
            if(index != null) {
                if (!seasonsBox.getSelectedItem().equals(seasonsArray[0])) {
                    List<Season> seasons2 = index.getSeasons();
                    Season existingSeason = null;
                    for (Season season : seasons2) {
                        if (seasonsArray[seasonsBox.getSelectedIndex()].equals("Season: " + season.getSeasonNumber())) {
                            existingSeason = season;
                        }
                    }
                    frame.dispose();
                    new ADDSEASONWINDOW(user, this, seasons1, myList, seriesList, existingSeries,true,existingSeason);
                    updateSeasonsComboBox();
                }
            } else {
                if (!seasonsBox.getSelectedItem().equals(seasonsArray[0])) {
                    frame.dispose();
                    new ADDSEASONWINDOW(user, this, seasons1, myList, seriesList, null,true,null);
                }
            }
        });

        JButton addSeriesButton = new JButton(" Add Series");
        addSeriesButton.setBounds(800,500,100,30);
        addSeriesButton.addActionListener(e-> {
            List<Review> reviews = new ArrayList<>();
            SeriesAddHandler seriesHandler = new SeriesAddHandler(titleField, descriptionField, ageButton, genreField, seasons1, protagonistField,reviews,exists,existingSeries,seriesData);
            String message = seriesHandler.handleAdd();
            boolean added = false;
            switch (message) {
                case "added":
                    JOptionPane.showMessageDialog(null, "Series added successfully");
                    added = true;
                    break;
                case "exists":
                    JOptionPane.showMessageDialog(null, "Series already exists");
                    break;
                case "edited":
                    JOptionPane.showMessageDialog(null, "Series edited successfully");
                    added = true;
                    break;
                case "fill":
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    break;
                case "no season":
                    JOptionPane.showMessageDialog(null, "Please enter at least one season");
                    break;
            }
            if(added){
                myList.clear();
                for(Series series : seriesData.getSeriesList()) {
                    myList.addElement(series.getTitle());
                }
                seriesList.setModel(myList);
                frame.dispose();
                new ALLSERIESWINDOW(user);
            }
        });

        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(titleField);
        panel.add(ageButton);
        panel.add(genreField);
        panel.add(addSeasonButton);
        panel.add(addSeriesButton);
        panel.add(seasonsBox);
        panel.add(descriptionField);
        panel.add(protagonistField);
        frame.add(panel, BorderLayout.CENTER);


    }
    public void updateSeasonsComboBox() {
        String[] seasonsArray = seasonsStr.toArray(new String[0]);
        DefaultComboBoxModel<String> newSeasonsModel = new DefaultComboBoxModel<>(seasonsArray);
        seasonsBox.setModel(newSeasonsModel);
        panel.revalidate();
        panel.repaint();
    }

    private void textFieldFocusGained(JTextField TF, String msg) {
        TF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(TF.getText().equals(msg)){
                    TF.setText("");
                    TF.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(TF.getText().isEmpty()) {
                    TF.setForeground(Color.GRAY);
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
