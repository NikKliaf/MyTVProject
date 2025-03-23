package gui;
import api.Movie;
import api.Subscriber;
import api.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gui.INITIALIZE.movieData;
import static gui.INITIALIZE.userData;

public class MOVIEWINDOW extends JFrame {
    public JFrame frame = new JFrame();
    public JPanel panel;
    private Movie movie;
    public JLabel titleLabel = new JLabel();
    public JLabel ageLabel = new JLabel();
    public JLabel firstReleaseLabel = new JLabel();
    public JLabel durationLabel = new JLabel();
    public JLabel categoriesLabel = new JLabel();
    public JLabel directorLabel = new JLabel();
    public JTextArea descriptionArea = new JTextArea();
    public JTextArea protagonistsArea = new JTextArea();
    public JLabel descriptionLabel = new JLabel("Description: ");
    public JLabel protagonistsLabel = new JLabel("Protagonists: ");


    public MOVIEWINDOW(User user, Movie movie, DefaultListModel<String> myList, JList<String> movieList, boolean favorites) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        this.movie = movie;
        panel = new JPanel();
        titleLabel.setText("Title: " + movie.getTitle());
        ageLabel.setText(movie.isSuitableForMinors()? "Suitable For Minors" : "Not Suitable For Minors");
        firstReleaseLabel.setText("First Released: "+ (movie.getReleaseYear()));
        durationLabel.setText("Duration: "+ (movie.getDuration()) + " minutes");
        categoriesLabel.setText("Category: "+ movie.getCategory());
        directorLabel.setText("Director: "+ movie.getDirector());
        descriptionArea.setText(movie.getDescription());
        protagonistsArea.setText(movie.getProtagonists());

        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        //Create removeMovie Button if user is admin or create add to favorites button if not admin
        if(user.isAdmin()){
            JButton deleteMovieButton = new JButton("Delete Movie");
            deleteMovieButton.setBounds(80,450,180,30);
            panel.add(deleteMovieButton);
            deleteMovieButton.addActionListener(e -> {
                int selectedOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this movie?", "Delete movie", JOptionPane.YES_NO_OPTION);
                if(selectedOption == JOptionPane.YES_OPTION) {
                    movieData.deleteMovie(movie,userData);
                    frame.dispose();
                    myList.clear();
                    for (Movie mov : movieData.getMovies()) {
                        myList.addElement(mov.getTitle());
                    }
                    movieList.setModel(myList);
                    new MOVIESWINDOW(user);
                }
            });
            JButton editMovieButton = new JButton("Edit Movie");
            editMovieButton.setBounds(260,450,180,30);
            panel.add(editMovieButton);
            editMovieButton.addActionListener(e-> {
                new ADDMOVIEWINDOW(user,myList,movieList,movie);
                frame.dispose();
            });
        } else {
            JButton favoriteButton = new JButton();
            favoriteButton.setBounds(80,450,180,30);
            panel.add(favoriteButton);
            if(user.getFavorites().contains(movie)){
                favoriteButton.setText("Remove from Favorites");
            } else {
                favoriteButton.setText("Add to Favorites");
            }
            favoriteButton.addActionListener(e -> {
                if(user.getFavorites().contains(movie)){
                    JOptionPane.showMessageDialog(this,"Movie removed from Favorites");
                    userData.removeFromFavorites(movie, (Subscriber) user);
                    favoriteButton.setText("Add to Favorites");
                } else {
                    JOptionPane.showMessageDialog(this, "Movie Added to Favorites");
                    userData.addToFavorites(movie, (Subscriber) user);
                    favoriteButton.setText("Remove from Favorites");
                }
            });
        }

        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        //title label operations
        titleLabel.setBounds(80, 25, 600, 30);

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
            }else{
                new MOVIESWINDOW(user);
            }
        });

        List<String> relatedMoviesList = new ArrayList<>();
        relatedMoviesList.add(0,"<html><font color ='gray'>Related Movies: </font></body></html>");
        for(Movie movie1:movie.getRelatedMovies()){
            relatedMoviesList.add(movie1.getTitle());
        }
        JComboBox<String> relatedMovieBox = new JComboBox<>(relatedMoviesList.toArray(new String[0]));
        relatedMovieBox.setBounds(80, 280 , 280, 30);
        relatedMovieBox.addActionListener(e -> {
           if(relatedMovieBox.getSelectedIndex() != 0) {
               for(Movie movie1: movieData.getMovies()){
                   if (movie1.getTitle().equals(relatedMovieBox.getSelectedItem())){
                       frame.dispose();
                       new MOVIEWINDOW(user, movie1, myList, movieList, favorites);
                   }
               }
           }
        });

        if (user.isAdmin()) {
            JButton addRelatedMovieButton = new JButton("Add Related Movie");
            addRelatedMovieButton.setBounds(200, 500, 180, 30);
            panel.add(addRelatedMovieButton);
            addRelatedMovieButton.addActionListener(e -> {
                String title = JOptionPane.showInputDialog(this, "Enter the title of the related movie you want to add", "Add Related Movie", JOptionPane.PLAIN_MESSAGE);
                if(title!= null) {
                    Movie relatedMovie = null;
                    for(Movie movie1:movieData.getMovies()) {
                        if(movie1.getTitle().equals(title)){
                            relatedMovie = movie1;
                        }
                    }
                    if(relatedMovie != null) {
                        if (!movie.getRelatedMovies().contains(relatedMovie)) {
                            if(!movie.equals(relatedMovie)) {
                                JOptionPane.showMessageDialog(this, "Movie added to related movies");
                                relatedMoviesList.add(title);
                                relatedMovieBox.addItem(title);
                                movieData.addRelatedMovieToFile(movie, relatedMovie);
                            } else {
                                JOptionPane.showMessageDialog(this, "Can't relate movie to itself");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Movie already in related movies");
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "Movie not found");
                    }
                }
            });

            JButton deleteRelatedMovieButton = new JButton("Remove Related Movie");
            deleteRelatedMovieButton.setBounds(400, 500, 180, 30);
            panel.add(deleteRelatedMovieButton);
            deleteRelatedMovieButton.addActionListener(e -> {
                if (!movie.getRelatedMovies().isEmpty()) {
                    Object[] options = relatedMoviesList.toArray();
                    int selectedOption = JOptionPane.showOptionDialog(frame,
                            "Select related movie to remove:",
                            "Remove related movie",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (selectedOption >= 0) {
                        String relatedMovieTitle = relatedMoviesList.get(selectedOption);
                        for(Movie movie1:movie.getRelatedMovies()) {
                            if(movie1.getTitle().equals(relatedMovieTitle)) {
                                int confirmDelete = JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove this movie from related?",
                                        "Remove movie",
                                        JOptionPane.YES_NO_OPTION);

                                if (confirmDelete == JOptionPane.YES_OPTION) {
                                    movieData.removeRelatedMovieFromFile(movie,movie1);
                                    relatedMoviesList.remove(relatedMovieTitle);
                                    relatedMovieBox.removeItem(relatedMovieTitle);
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No related movies to delete");
                }
            });
        }



        //Age Label operations
        ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ageLabel.setBounds(80, 80, 200, 30);

        //First Release Label operations
        firstReleaseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        firstReleaseLabel.setBounds(80, 120, 200, 30);

        //Duration Label operations
        durationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        durationLabel.setBounds(80, 160, 200, 30);

        //Categories Label operations
        categoriesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoriesLabel.setBounds(80, 200, 500, 30);

        //Director Label operations
        directorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        directorLabel.setBounds(80, 240, 200, 30);

        //Description Area and Label operations
        descriptionArea.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionArea.setBounds(480, 80, 350, 200);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.GRAY);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionLabel.setBounds(380, 80, 100, 30);


        //Protagonists Area and Label operations
        protagonistsArea.setFont(new Font("Arial", Font.BOLD, 14));
        protagonistsArea.setBounds(480, 300, 350, 200);
        protagonistsArea.setEditable(false);
        protagonistsArea.setLineWrap(true);
        protagonistsArea.setWrapStyleWord(true);
        protagonistsArea.setBackground(Color.GRAY);
        protagonistsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        protagonistsLabel.setBounds(380, 300, 100, 30);

        //Reviews button operations
        JButton reviewsButton = new JButton("Reviews");
        reviewsButton.setFont(new Font("Arial", Font.BOLD, 14));
        reviewsButton.setBounds(80, 400, 180, 30);
        panel.add(reviewsButton);
        reviewsButton.addActionListener(e -> {
            new REVIEWSWINDOW(user,movie,myList,movieList);
            frame.dispose();
        });

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
        panel.add(ageLabel);
        panel.add(firstReleaseLabel);
        panel.add(durationLabel);
        panel.add(categoriesLabel);
        panel.add(directorLabel);
        panel.add(descriptionLabel);
        panel.add(descriptionArea);
        panel.add(protagonistsLabel);
        panel.add(protagonistsArea);
        panel.add(relatedMovieBox);

        frame.add(panel, BorderLayout.CENTER);
    }
    public void updateTitle(String movieTitle){
        titleLabel.setText(movieTitle);
    }
}





