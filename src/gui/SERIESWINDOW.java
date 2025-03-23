package gui;
import api.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gui.INITIALIZE.*;
import static gui.INITIALIZE.movieData;

public class SERIESWINDOW extends JFrame {
    public JFrame frame = new JFrame();
    public JPanel panel;
    private Series series;
    public JLabel titleLabel = new JLabel();
    public JLabel ageLabel = new JLabel();
    public JLabel categoriesLabel = new JLabel();
    public JTextArea descriptionArea = new JTextArea();
    public JTextArea protagonistsArea = new JTextArea();
    public JLabel descriptionLabel = new JLabel("Description: ");
    public JLabel protagonistsLabel = new JLabel("Protagonists: ");

    public SERIESWINDOW(User user, Series series, DefaultListModel<String> myList, JList<String> seriesList, boolean favorites) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        this.series = series;
        panel = new JPanel();
        titleLabel.setText("Title: " + series.getTitle());
        ageLabel.setText(series.isSuitableForMinors()? "Suitable For Minors" : "Not Suitable For Minors");
        categoriesLabel.setText("Category: "+ series.getCategory());
        descriptionArea.setText(series.getDescription());
        protagonistsArea.setText(series.getProtagonists());

        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        //Create removeSeries Button if user is admin or create add to favorites button if not admin
        if(user.isAdmin()){
            JButton deleteSeriesButton = new JButton("Delete Series");
            deleteSeriesButton.setBounds(80,450,180,30);
            panel.add(deleteSeriesButton);
            deleteSeriesButton.addActionListener(e -> {
                int selectedOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this series?", "Delete series", JOptionPane.YES_NO_OPTION);
                if(selectedOption == JOptionPane.YES_OPTION) {
                    seriesData.deleteSeries(series);
                    frame.dispose();
                    myList.clear();
                    for (Series ser : seriesData.getSeriesList()) {
                        myList.addElement(ser.getTitle());
                    }
                    seriesList.setModel(myList);
                    new ALLSERIESWINDOW(user);
                }
            });
            JButton editSeriesButton = new JButton("Edit Series");
            editSeriesButton.setBounds(260,450,180,30);
            panel.add(editSeriesButton);
            editSeriesButton.addActionListener(e -> {
                new ADDSERIESWINDOW(user,myList,seriesList,series,null);
                frame.dispose();
            });
        } else {
            JButton favoriteButton = new JButton();
            favoriteButton.setBounds(80,450,180,30);
            panel.add(favoriteButton);
            if(user.getFavorites().contains(series)){
                favoriteButton.setText("Remove from Favorites");
            } else {
                favoriteButton.setText("Add to Favorites");
            }
            favoriteButton.addActionListener(e -> {
                if(user.getFavorites().contains(series)){
                    JOptionPane.showMessageDialog(this,"Movie removed from Favorites");
                    userData.removeFromFavorites(series, (Subscriber) user);
                    favoriteButton.setText("Add to Favorites");
                } else {
                    JOptionPane.showMessageDialog(this, "Movie Added to Favorites");
                    userData.addToFavorites(series, (Subscriber) user);
                    favoriteButton.setText("Remove from Favorites");
                }
            });
        }

        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        //title label operations
        titleLabel.setBounds(80, 25, 500, 30);

        //logout button operations
        logoutButton.setBounds(850,25,100,25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        //back button operations
        backButton.setBounds(80,500,100,30);
        backButton.addActionListener(e -> {
            frame.dispose();
            if(favorites){
                new FAVORITESWINDOW((Subscriber) user);
            } else {
                new ALLSERIESWINDOW(user);
            }
        });

        List<String> relatedSeriesList = new ArrayList<>();
        relatedSeriesList.add(0,"<html><font color ='gray'>Related Series: </font></body></html>");
        for(Series series1:series.getRelatedSeries()){
            relatedSeriesList.add(series1.getTitle());
        }

        JComboBox<String> relatedSeriesBox = new JComboBox<>(relatedSeriesList.toArray(new String[0]));
        relatedSeriesBox.setBounds(50, 160 , 230, 30);
        relatedSeriesBox.addActionListener(e -> {
            if(relatedSeriesBox.getSelectedIndex() != 0) {
                for(Series series1: seriesData.getSeriesList()){
                    if (series1.getTitle().equals(relatedSeriesBox.getSelectedItem())){
                        frame.dispose();
                        new SERIESWINDOW(user, series1, myList, seriesList, favorites);
                    }
                }
            }
        });

        if (user.isAdmin()) {
            JButton addRelatedSeriesButton = new JButton("Add Related Series");
            addRelatedSeriesButton.setBounds(200, 500, 180, 30);
            panel.add(addRelatedSeriesButton);
            addRelatedSeriesButton.addActionListener(e -> {
                String title = JOptionPane.showInputDialog(this, "Enter the title of the related series you want to add", "Add Related Movie", JOptionPane.PLAIN_MESSAGE);
                if(title!= null) {
                    Series relatedSeries = null;
                    for(Series series1:seriesData.getSeriesList()) {
                        if(series1.getTitle().equals(title)){
                            relatedSeries = series1;
                        }
                    }
                    if(relatedSeries != null) {
                        if (!series.getRelatedSeries().contains(relatedSeries)) {
                            if(!series.equals(relatedSeries)) {
                                JOptionPane.showMessageDialog(this, "Series added to related series");
                                relatedSeriesList.add(title);
                                relatedSeriesBox.addItem(title);
                                seriesData.addRelatedSeriesToFile(series, relatedSeries);
                            } else {
                                JOptionPane.showMessageDialog(this, "Can't relate series to itself");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Series already in related series");
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "Series not found");
                    }
                }
            });

            JButton deleteRelatedSeriesButton = new JButton("Remove Related Series");
            deleteRelatedSeriesButton.setBounds(400, 500, 180, 30);
            panel.add(deleteRelatedSeriesButton);
            deleteRelatedSeriesButton.addActionListener(e -> {
                if (!series.getRelatedSeries().isEmpty()) {
                    Object[] options = relatedSeriesList.toArray();
                    int selectedOption = JOptionPane.showOptionDialog(frame,
                            "Select related series to remove:",
                            "Remove related series",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (selectedOption >= 0) {
                        String relatedSeriesTitle = relatedSeriesList.get(selectedOption);
                        for(Series series1:series.getRelatedSeries()) {
                            if(series1.getTitle().equals(relatedSeriesTitle)) {
                                int confirmDelete = JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove this series from related?",
                                        "Remove series",
                                        JOptionPane.YES_NO_OPTION);

                                if (confirmDelete == JOptionPane.YES_OPTION) {
                                    seriesData.removeRelatedSeriesFromFile(series,series1);
                                    relatedSeriesList.remove(relatedSeriesTitle);
                                    relatedSeriesBox.removeItem(relatedSeriesTitle);
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No related series to delete");
                }
            });
        }

        //Age Label operations
        ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ageLabel.setBounds(50, 80, 200, 30);

        //Categories Label operations
        categoriesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoriesLabel.setBounds(50, 120, 500, 30);

        //Description Area and Label operations
        descriptionArea.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionArea.setBounds(350, 80, 330, 200);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.GRAY);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionLabel.setBounds(250, 80, 100, 30);

        //Protagonists Area and Label operations
        protagonistsArea.setFont(new Font("Arial", Font.BOLD, 14));
        protagonistsArea.setBounds(350, 300, 330, 200);
        protagonistsArea.setEditable(false);
        protagonistsArea.setLineWrap(true);
        protagonistsArea.setWrapStyleWord(true);
        protagonistsArea.setBackground(Color.GRAY);
        protagonistsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        protagonistsLabel.setBounds(250, 300, 100, 30);

        //Reviews button operations
        JButton reviewsButton = new JButton("Reviews");
        reviewsButton.setFont(new Font("Arial", Font.BOLD, 14));
        reviewsButton.setBounds(50, 400, 180, 30);
        panel.add(reviewsButton);
        reviewsButton.addActionListener(e -> {
            new REVIEWSWINDOW(user,series,myList,seriesList);
            frame.dispose();
        });

        JTree seasonsTree = new JTree(createTreeModel(series));
        seasonsTree.setFont(new Font("Arial", Font.PLAIN, 14));
        seasonsTree.setBackground(Color.GRAY);
        seasonsTree.setForeground(Color.GRAY);
        seasonsTree.setEditable(false);
        seasonsTree.setBounds(new Rectangle(700, 80, 250, 400));

        JScrollPane scrollPane = new JScrollPane(seasonsTree);
        scrollPane.setBounds(new Rectangle(700, 80, 250, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));


        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTv");
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(seasonsTree);
        panel.add(ageLabel);
        panel.add(categoriesLabel);
        panel.add(descriptionLabel);
        panel.add(descriptionArea);
        panel.add(protagonistsLabel);
        panel.add(protagonistsArea);
        panel.add(relatedSeriesBox);

        frame.add(panel, BorderLayout.CENTER);
    }

    private DefaultTreeModel createTreeModel(Series series) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(series.getTitle());

        for (Season season : series.getSeasons()) {
            DefaultMutableTreeNode seasonNode = new DefaultMutableTreeNode("Season " + season.getSeasonNumber());
            root.add(seasonNode);

            for (Episode episode : season.getEpisodes()) {
                DefaultMutableTreeNode episodeNode = new DefaultMutableTreeNode("Episode: " + episode.getNumber() + " Duration: " + episode.getDuration());
                seasonNode.add(episodeNode);
            }
        }

        return new DefaultTreeModel(root);
    }
    public void updateTitle(String seriesTitle){
        titleLabel.setText(seriesTitle);
    }
}





