package api;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeriesDataTest {

    private SeriesData seriesData;
    private String testFileName = "test_series_data.ser";

    @BeforeEach
    public void setUp() {
        seriesData = new SeriesData(testFileName);
    }
    @AfterEach
    public void tearDown() {
        File file = new File(testFileName);
        file.delete();
    }

    @Test
    void addSeries() {
        Series series1 = new Series("Title 1", "Description 1", true, "Drama", new ArrayList<Season>(), "Protagonist 1", new ArrayList<Review>());
        Series series2 = new Series("Title 2", "Description 2", false, "Comedy", new ArrayList<Season>(), "Protagonist 2", new ArrayList<Review>());

        assertTrue(seriesData.addSeries(series1));
        assertTrue(seriesData.addSeries(series2));

        assertFalse(seriesData.addSeries(series1)); // Adding the same series again should return false
    }

    @Test
    void deleteSeries() {
        Series series = new Series("Title", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        seriesData.addSeries(series);

        List<Series> seriesListBeforeDeletion = seriesData.getSeriesList();
        assertEquals(1, seriesListBeforeDeletion.size());

        seriesData.deleteSeries(series);
        List<Series> seriesListAfterDeletion = seriesData.getSeriesList();
        assertEquals(0, seriesListAfterDeletion.size());
    }

    @Test
    void updateSeries() {
        Series oldSeries = new Series("Old Title", "Old Description", true, "Sci-Fi", new ArrayList<Season>(), "Main", new ArrayList<Review>());
        Series newSeries = new Series("New Title", "New Description", false, "Adventure", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        seriesData.addSeries(oldSeries);

        seriesData.updateSeries(oldSeries, newSeries);
        assertEquals("New Title", seriesData.getSeriesList().get(0).getTitle());
        assertEquals("New Description", seriesData.getSeriesList().get(0).getDescription());
        assertEquals("Adventure", seriesData.getSeriesList().get(0).getCategory());
    }

    @Test
    void searchByTitle() {
        Series series = new Series("Title", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        seriesData.addSeries(series);

        Series foundSeries = seriesData.searchByTitle("Title");
        assertNotNull(foundSeries);
        assertEquals("Title", foundSeries.getTitle());

        Series notFoundSeries = seriesData.searchByTitle("Non-existent Title");
        assertNull(notFoundSeries);
    }

    @Test
    void addReview() {
        Series series = new Series("Title", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        seriesData.addSeries(series);

        Review review = new Review("User", "Great series!", "Rating: 5");

        assertTrue(seriesData.addReview(review, series));
        assertFalse(seriesData.addReview(review, series)); // Adding the same review again should return false
    }

    @Test
    void removeReview() {
        Series series = new Series("Title", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        Review review = new Review("User", "Great series!", "Rating: 5");
        series.getReviews().add(review);
        seriesData.addSeries(series);

        seriesData.removeReview(review, series);
        assertEquals(0, series.getReviews().size());
    }

    @Test
    void addRelatedSeriesToFile() {
        Series parentSeries = new Series("Parent", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        Series relatedSeries = new Series("Related", "Description", true, "Adventure", new ArrayList<Season>(), "Hero", new ArrayList<Review>());
        seriesData.addSeries(parentSeries);
        seriesData.addSeries(relatedSeries);

        seriesData.addRelatedSeriesToFile(parentSeries, relatedSeries);
        assertEquals(1, parentSeries.getRelatedSeries().size());
        assertEquals(1, relatedSeries.getRelatedSeries().size());
    }

    @Test
    void removeRelatedSeriesFromFile() {
        Series parentSeries = new Series("Parent", "Description", true, "Action", new ArrayList<Season>(), "Lead", new ArrayList<Review>());
        Series relatedSeries = new Series("Related", "Description", true, "Adventure", new ArrayList<Season>(), "Hero", new ArrayList<Review>());
        seriesData.addSeries(parentSeries);
        seriesData.addSeries(relatedSeries);

        seriesData.addRelatedSeriesToFile(parentSeries, relatedSeries);

        seriesData.removeRelatedSeriesFromFile(parentSeries, relatedSeries);
        assertEquals(0, parentSeries.getRelatedSeries().size());
        assertEquals(0, relatedSeries.getRelatedSeries().size());
    }
}
