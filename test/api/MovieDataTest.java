package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MovieDataTest {
    private final String TEST_FILE_NAME = "test_movie_data.ser";
    private MovieData movieData;

    @Before
    public void setUp() throws Exception {
        movieData = new MovieData(TEST_FILE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        movieData = null;
    }

    @Test
    public void addMovie() {
        List<Review> reviews = new ArrayList<>();
        assertTrue(movieData.addMovie(new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", reviews)));
        assertFalse(movieData.addMovie(new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", reviews)));
    }

    @Test
    public void getMovies() {
        assertTrue(movieData.getMovies().isEmpty());
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.getMovies().contains(movie));
    }

    @Test
    public void loadMovies() {
    }

    @Test
    public void deleteMovie() {
        assertTrue(movieData.getMovies().isEmpty());
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.getMovies().contains(movie));
        movieData.deleteMovie(movie,mock(UserData.class));
        assertFalse(movieData.getMovies().contains(movie));
    }

    @Test
    public void updateMovie() {
        assertTrue(movieData.getMovies().isEmpty());
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.getMovies().contains(movie));
        Movie movie1 = new Movie("Test Movie 1", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.updateMovie(movie, movie1 );
        assertFalse(movieData.getMovies().contains(movie));
        assertTrue(movieData.getMovies().contains(movie1));
    }

    @Test
    public void searchByTitle() {
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.getMovies().contains(movie));
        assertTrue(movieData.searchByTitle("Test Movie").equals(movie));
    }

    @Test
    public void addReview() {
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.addReview(new Review("John Doe","Test review", Integer.toString(5)), movie));
    }

    @Test
    public void removeReview() {
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        assertTrue(movieData.addReview(new Review("John Doe","Test review", Integer.toString(5)), movie));
        movieData.removeReview(new Review("John Doe","Test review", Integer.toString(5)), movie);
        assertFalse(movie.getReviews().contains(new Review("John Doe","Test review", Integer.toString(5))));
    }

    @Test
    public void addRelatedMovieToFile() {
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        Movie movie1 = new Movie("Test Movie 1", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addRelatedMovieToFile(movie, movie1);
        assertTrue(movieData.searchByTitle("Test Movie").getRelatedMovies().contains(movie1));
    }

    @Test
    public void removeRelatedMovieFromFile() {
        Movie movie = new Movie("Test Movie", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addMovie(movie);
        Movie movie1 =  new Movie("Test Movie 1", "Description", false,2023,1,"Adventure","Director","actor1, actor2", new ArrayList<Review>());
        movieData.addRelatedMovieToFile(movie, movie1);
        assertTrue(movieData.searchByTitle("Test Movie").getRelatedMovies().contains(movie1));
        movieData.removeRelatedMovieFromFile(movie,movie1);
        assertFalse(movieData.searchByTitle("Test Movie").getRelatedMovies().contains(movie1));
    }
}