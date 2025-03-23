package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SeriesAddHandlerTest {
    private SeriesAddHandler seriesAddHandler;
    private List<Season> seasons = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private SeriesData seriesData;

    @Before
    public void setUp() {
        JTextField titleField = new JTextField("Test Series");
        JTextArea descriptionField = new JTextArea("Test Description");
        JButton ageButton = new JButton(" Suitable for Minors");
        JComboBox<String> genreField = new JComboBox<>(new String[]{"<html><font color ='gray'>Categories: </font></body></html>", "Drama"});
        genreField.setSelectedIndex(1);
        JTextArea protagonistsField = new JTextArea("Test Protagonists");
        boolean exists = false;
        Series oldSeries = null;
        seriesData = new SeriesData("src/test/api/series_add_test.ser");

        seriesAddHandler = new SeriesAddHandler(titleField, descriptionField, ageButton, genreField, seasons, protagonistsField, reviews, exists, oldSeries,seriesData);
    }

    @After
    public void tearDown() {
        seriesAddHandler = null;
    }

    @Test
    public void handleAdd_shouldReturnFillError_whenFieldsAreEmpty() {
        SeriesAddHandler seriesAddHandler1= new SeriesAddHandler(
                new JTextField(" Add Title"),
                new JTextArea("Description"),
                new JButton(" Not Suitable for Minors"),
                new JComboBox<>(new String[]{"Action"}),
                seasons,
                new JTextArea("Updated Protagonists"),
                new ArrayList<Review>(),
                true,
                null,
                seriesData
        );
        assertEquals("fill", seriesAddHandler1.handleAdd());
    }

    @Test
    public void handleAdd_shouldReturnNoSeasonError_whenSeasonsListIsEmpty() {
        seasons.clear();
        assertEquals("no season", seriesAddHandler.handleAdd());
    }

    @Test
    public void handleAdd_shouldReturnEdited_whenSeriesExistsAndIsUpdated() {
        Series oldSeries = new Series("Old Series", "Old Description", false, "Comedy", new ArrayList<Season>(), "Old Protagonists", new ArrayList<Review>());
        boolean exists = true;
        seasons.add(new Season(1,2023,new ArrayList<Episode>()));
        seriesAddHandler = new SeriesAddHandler(
                new JTextField("Updated Series"),
                new JTextArea("Updated Description"),
                new JButton(" Not Suitable for Minors"),
                new JComboBox<>(new String[]{"Action"}),
                seasons,
                new JTextArea("Updated Protagonists"),
                new ArrayList<Review>(),
                exists,
                oldSeries,
                seriesData
        );

        assertEquals("edited", seriesAddHandler.handleAdd());
    }
}
