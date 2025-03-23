package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ReviewAddHandlerTest {
    private JTextArea reviewArea;
    private JLabel ratingLabel;
    private User user;
    private Movie movie;
    private Series series;
    private MovieData movieData;
    private SeriesData seriesData;

    @Before
    public void setUp() throws Exception {
        reviewArea = new JTextArea();
        ratingLabel = new JLabel("5");
        user = new Subscriber("John", "Doe","username","password",new ArrayList<VideoContent>());

        movie = new Movie("Test","test",true, 2023,100,"Horror","Test", "Test",new ArrayList<Review>());
        series = new Series("Test","test",true, "Horror",new ArrayList<Season>(),"Test", new ArrayList<Review>());

        movieData = new MovieData("src/test/api/movie_data_text.ser");
        seriesData = new SeriesData("src/test/api/series_data_text.ser");
    }

    @After
    public void tearDown() throws Exception {
        reviewArea = null;
        ratingLabel = null;
        user = null;
        movie = null;
        series = null;
        movieData = null;
        seriesData = null;
    }

    @Test
    public void addTest() {
        reviewArea.setText("Test review");
        ratingLabel.setText("5");
        ReviewAddHandler reviewAddHandler = new ReviewAddHandler(reviewArea,ratingLabel,user,series,movieData,seriesData);

        assertEquals("added",reviewAddHandler.handleAdd());
    }
    @Test
    public void fillTest(){
        reviewArea.setText(" Add Your Review (Up to 500 characters)");
        ratingLabel.setText("5");
        ReviewAddHandler reviewAddHandler1 = new ReviewAddHandler(reviewArea,ratingLabel,user,movie,movieData,seriesData);

        assertEquals("fill",reviewAddHandler1.handleAdd());
    }

    @Test
    public void failedTest(){
        reviewArea.setText("Test review");
        ratingLabel.setText("text");
        ReviewAddHandler reviewAddHandler3 = new ReviewAddHandler(reviewArea, ratingLabel, user,movie,movieData,seriesData);

        assertEquals("failed",reviewAddHandler3.handleAdd());
    }
}