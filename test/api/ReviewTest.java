package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {
    private Review review;

    @Before
    public void setUp() {
        review = new Review("John Doe", "This is a good movie!", "5");
    }

    @After
    public void tearDown() {
        review = null;
    }

    @Test
    public void getName_shouldReturnCorrectName() {
        assertEquals("John Doe", review.getName());
    }

    @Test
    public void getReview_shouldReturnCorrectReview() {
        assertEquals("This is a good movie!", review.getReview());
    }

    @Test
    public void getRating_shouldReturnCorrectRating() {
        assertEquals("5", review.getRating());
    }

    @Test
    public void getName_shouldNotBeNull() {
        assertNotNull(review.getName());
    }

    @Test
    public void getReview_shouldNotBeNull() {
        assertNotNull(review.getReview());
    }

    @Test
    public void getRating_shouldNotBeNull() {
        assertNotNull(review.getRating());
    }
}
