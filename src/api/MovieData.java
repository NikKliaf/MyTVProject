package api;

import gui.INITIALIZE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Manages the storing, loading, adding, deleting, and updating of Movie objects to/from a file using serialization.
 */
public class MovieData implements Serializable {
    private final String fileName;
    private List<Movie> movies;
    /**
     * Constructor of MovieData class.
     * @param fileName The name of the file to store movie data.
     */
    public MovieData(String fileName) {
        this.fileName = fileName;
        this.movies = new ArrayList<>();
        loadMovies();
    }
    /**
     * Adds a movie to the list of movies.
     * @param movie The movie to be added.
     */
    public boolean addMovie(Movie movie) {
        if(!movies.contains(movie)) {
            movies.add(movie);
            saveMovies();
            return true;
        }
        return false;
    }
    /**
     * Saves a list of movies to the file.
     */
    private void saveMovies() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads a list of movies from the file.
     */
    public void loadMovies() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            movies = (List<Movie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gets the list of movies.
     * @return The list of movies.
     */
    public List<Movie> getMovies() {
        return movies;
    }
    /**
     * Deletes a movie from the list of movies.
     * @param movie The movie to be deleted.
     */
    public void deleteMovie(Movie movie, UserData userData) {
        movies.remove(movie);
        Map<String, User> Map = userData.getUsersByUsername();
        for (User user : Map.values()) {
            if(!(user instanceof Admin)) {
                user.getFavorites().remove(movie);
            }
        }
        saveMovies();
        userData.saveUsers();
    }
    /**
     * Updates a movie in the list of movies.
     * @param oldMovie The old movie.
     * @param newMovie The new movie.
     */
    public void updateMovie(Movie oldMovie, Movie newMovie) {
        int index = movies.indexOf(oldMovie);
        if (index != -1) {
            movies.set(index, newMovie);
            saveMovies();
        }
    }

    /**
     * Searches for a movie by its title.
     * @param title The title of the movie to search for.
     * @return The movie found with the provided title, or null if not found.
     */
    public Movie searchByTitle(String title) {
        List<Movie> movies = getMovies();

        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }
    /**
     * Adds a review to a movie.
     * @param movie The movie to add the review to.
     * @param review The review to be added.
     */
    public boolean addReview(Review review, Movie movie) {
        if(movie.getReviews().contains(review)) {
            return false;
        } else {
            movie.getReviews().add(review);
            saveMovies();
            return true;
        }
    }
    /**
     * Removes a review from a movie.
     * @param movie The movie to remove the review from.
     * @param review The review to be removed.
     */
    public void removeReview(Review review, Movie movie) {
        movie.getReviews().remove(review);
        saveMovies();
    }

    /**
     * Adds a related movie to a movie. Also adds the parent movie to the movie's
     * related movies list.
     * @param parentMovie The movie to add the related movie to.
     * @param movie The related movie to be added.
     */
    public void addRelatedMovieToFile(Movie parentMovie, Movie movie) {
        parentMovie.getRelatedMovies().add(movie);
        movie.getRelatedMovies().add(parentMovie);
        saveMovies();
    }
    /**
     * Removes a related movie from a movie. Also removes the parent movie from the movie's
     * related movies list.
     * @param parentMovie The movie to remove the related movie from.
     * @param movie The related movie to be removed.
     */
    public void removeRelatedMovieFromFile(Movie parentMovie, Movie movie) {
        parentMovie.getRelatedMovies().remove(movie);
        movie.getRelatedMovies().remove(parentMovie);
        saveMovies();
    }
}
