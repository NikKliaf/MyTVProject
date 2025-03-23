package api;

import javax.swing.*;
import java.util.List;

public class MovieAddHandler {
    private final MovieData movieData;
    private boolean added = false;
    private String title;
    private String age;
    private String firstReleaseYear;
    private String duration;
    private String genre;
    private String director;
    private String description;
    private String protagonists;
    private boolean exists;
    private List<Review> reviews;
    private Movie oldMovie;

    /**
     * Handles the addition of movies based on user input.
     * @param titleField        JTextField for the movie title
     * @param ageButton         JButton to set the suitability for minors
     * @param firstReleaseField JTextField for the first release year
     * @param durationField     JTextField for the movie duration
     * @param genreField        JComboBox for movie genres
     * @param directorField     JTextField for the movie director
     * @param descriptionField  JTextArea for the movie description
     * @param protagonistField  JTextArea for the movie protagonists
     * @param reviews           List of reviews for the movie
     */
    public MovieAddHandler(JTextField titleField, JButton ageButton, JTextField firstReleaseField, JTextField durationField, JComboBox<String> genreField, JTextField directorField, JTextArea descriptionField, JTextArea protagonistField, List<Review> reviews, MovieData movieData, boolean exists, Movie oldMovie) {
        title = titleField.getText();
        age = ageButton.getText();
        firstReleaseYear = firstReleaseField.getText();
        duration = durationField.getText();
        genre = genreField.getSelectedItem().toString();
        director = directorField.getText();
        description = descriptionField.getText();
        protagonists = protagonistField.getText();
        this.exists = exists;
        this.movieData = movieData;
        this.reviews = reviews;
        this.oldMovie = oldMovie;
    }

    /**
     * Handles adding the movie.
     */
    public String handleAdd() {
        if (title.equals(" Add Title") || firstReleaseYear.equals(" Add First Release Year") || duration.equals(" Add Duration") || genre.equals("<html><font color ='gray'>Categories: </font></body></html>") || director.equals(" Add Director") || description.equals(" Add Description") || protagonists.equals(" Add Protagonists")) {
            return "fill";
        } else {
            try {
                int releaseYear = Integer.parseInt(firstReleaseYear);
                int movieDuration = Integer.parseInt(duration);

                Movie movie = new Movie(title, description, age.equals(" Suitable for Minors"), releaseYear, movieDuration, genre, director, protagonists, reviews);

                if(exists){
                    movieData.updateMovie(oldMovie, movie);
                    return "edited";
                } else {
                    if (movieData.addMovie(movie)) {
                        return "added";
                    } else {
                        return "exists";
                    }
                }
            } catch (NumberFormatException e) {
                return handleNumberFormatException();
            }
        }
    }
    /**
     * Handles NumberFormatExceptions for first release year and duration fields.
     */
    private String handleNumberFormatException() {
        if (!firstReleaseYear.equals(" Add First Release Year")) {
            try {
                Integer.parseInt(firstReleaseYear);
            } catch (NumberFormatException ex) {
                return "year exception";
            }
        }

        if (!duration.equals(" Add Duration")) {
            try {
                Integer.parseInt(duration);
            } catch (NumberFormatException ex) {
                return "duration exception";
            }
        }
        return "error";
    }
}
