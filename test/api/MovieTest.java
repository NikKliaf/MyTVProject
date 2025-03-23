package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        List<Review> reviews = new ArrayList<>();
        movie = new Movie("Test Movie", "Description", true, 2023, 120, "Adventure", "Director", "Actor1, Actor2", reviews);
    }

    @AfterEach
    public void tearDown() throws Exception {
        movie = null;
    }

    @Test
    public void getTitle() {
        assertEquals("Test Movie", movie.getTitle());
    }

    @Test
    public void getDescription() {
        assertEquals("Description", movie.getDescription());
    }

    @Test
    public void isSuitableForMinors() {
        assertTrue(movie.isSuitableForMinors());
    }

    @Test
    public void getReleaseYear() {
        assertEquals(2023, movie.getReleaseYear());
    }

    @Test
    public void getDuration() {
        assertEquals(120, movie.getDuration());
    }

    @Test
    public void getCategory() {
        assertEquals("Adventure", movie.getCategory());
    }

    @Test
    public void getDirector() {
        assertEquals("Director", movie.getDirector());
    }

    @Test
    public void getProtagonists() {
        assertEquals("Actor1, Actor2", movie.getProtagonists());
    }

    @Test
    public void getReviews() {
        assertNotNull(movie.getReviews());
        assertEquals(0, movie.getReviews().size());
    }

    @Test
    public void setReviews() {
        List<Review> newReviews = new ArrayList<>();
        newReviews.add(new Review("User", "Good movie", "4.5"));
        movie.setReviews(newReviews);

        assertEquals(1, movie.getReviews().size());
        assertEquals("Good movie", movie.getReviews().get(0).getReview());
    }

    @Test
    public void addReview() {
        Review review = new Review("User", "Great movie", "5.0");
        movie.addReview(review);

        assertEquals(1, movie.getReviews().size());
        assertEquals("Great movie", movie.getReviews().get(0).getReview());
    }

    @Test
    public void getRelatedMovies() {
        assertNotNull(movie.getRelatedMovies());
        assertEquals(0, movie.getRelatedMovies().size());
    }

    @Test
    public void testEquals() {
        List<Review> reviews = new ArrayList<>();
        Movie sameMovie = new Movie("Test Movie", "Description", true, 2023, 120, "Action", "Director", "Actor1, Actor2", reviews);
        Movie differentMovie = new Movie("Different Title", "Description", true, 2023, 120, "Action", "Director", "Actor1, Actor2", reviews);

        assertTrue(movie.equals(sameMovie));
        assertFalse(movie.equals(differentMovie));
    }
}
