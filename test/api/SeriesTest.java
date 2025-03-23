package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SeriesTest {

    private Series series;

    @Before
    public void setUp() throws Exception {
        List<Season> seasons = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        series = new Series("Test Series", "Description", true, "Drama", seasons, "Actor1, Actor2", reviews);
    }

    @After
    public void tearDown() throws Exception {
        series = null;
    }

    @Test
    public void getTitle() {
        assertEquals("Test Series", series.getTitle());
    }

    @Test
    public void getDescription() {
        assertEquals("Description", series.getDescription());
    }

    @Test
    public void isSuitableForMinors() {
        assertTrue(series.isSuitableForMinors());
    }

    @Test
    public void getCategory() {
        assertEquals("Drama", series.getCategory());
    }

    @Test
    public void getProtagonists() {
        assertEquals("Actor1, Actor2", series.getProtagonists());
    }

    @Test
    public void getSeasons() {
        assertNotNull(series.getSeasons());
        assertEquals(0, series.getSeasons().size());
    }

    @Test
    public void getReviews() {
        assertNotNull(series.getReviews());
        assertEquals(0, series.getReviews().size());
    }

    @Test
    public void setReviews() {
        List<Review> newReviews = new ArrayList<>();
        newReviews.add(new Review("User", "Good series", "4.5"));
        series.setReviews(newReviews);

        assertEquals(1, series.getReviews().size());
        assertEquals("Good series", series.getReviews().get(0).getReview());
    }

    @Test
    public void addReview() {
        Review review = new Review("User", "Great series", "5.0");
        series.addReview(review);

        assertEquals(1, series.getReviews().size());
        assertEquals("Great series", series.getReviews().get(0).getReview());
    }

    @Test
    public void getRelatedSeries() {
        assertNotNull(series.getRelatedSeries());
        assertEquals(0, series.getRelatedSeries().size());
    }

    @Test
    public void testEquals() {
        Series sameSeries = new Series("Test Series", "Description", true, "Drama", new ArrayList<Season>(), "Actor1, Actor2", new ArrayList<Review>());
        Series differentSeries = new Series("Different Title", "Description", true, "Drama", new ArrayList<Season>(), "Actor1, Actor2", new ArrayList<Review>());

        assertTrue(series.equals(sameSeries));
        assertFalse(series.equals(differentSeries));
    }
}
