package api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class MovieAddHandlerTest {
	private static final String TEST_FILE_NAME = "test_movie_add.ser";
	private MovieData movieData;

	@BeforeEach
	void setUp() {
		File file = new File(TEST_FILE_NAME);
		if(file.exists()) {
			file.delete();
		}
		movieData = new MovieData(TEST_FILE_NAME);
	}
	@AfterEach
	void tearDown() {
		File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
	}

	@Test
	public void addMovieTest() {
		List<Review> reviews = new ArrayList<>();

		JComboBox<String> genreField = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Adventure"}));
		genreField.setSelectedIndex(0);

		MovieAddHandler handler = new MovieAddHandler(new JTextField("Test Movie"), new JButton(""), new JTextField("2023"), new JTextField("1"), genreField, new JTextField("director"), new JTextArea("Description"), new JTextArea("actor1, actor2"), reviews, movieData, false,null);
		assertEquals("added", handler.handleAdd());
	}

	@Test
	public void editedMovieTest() {
		List<Review> reviews = new ArrayList<>();

        JComboBox<String> genreField = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Adventure"}));
        genreField.setSelectedIndex(0);

        MovieAddHandler handler = new MovieAddHandler(new JTextField("Test Movie"), new JButton(""), new JTextField("2023"), new JTextField("1"), genreField, new JTextField("director"), new JTextArea("Description"), new JTextArea("actor1, actor2"), reviews, movieData, true,null);
        assertEquals("edited", handler.handleAdd());
	}

	@Test
	public void existsTest() {
		List<Review> reviews = new ArrayList<>();

        JComboBox<String> genreField = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Adventure"}));
        genreField.setSelectedIndex(0);

		movieData.addMovie(new Movie("Test Movie", "Description", false, 2023, 1, "Adventure", "director","actor1, actor2", reviews));

        MovieAddHandler handler = new MovieAddHandler(new JTextField("Test Movie"), new JButton(""), new JTextField("2023"), new JTextField("1"), genreField, new JTextField("director"), new JTextArea("Description"), new JTextArea("actor1, actor2"), reviews, movieData, false,null);
        assertEquals("exists", handler.handleAdd());
	}

	@Test
	public void exceptionsTest() {
		List<Review> reviews = new ArrayList<>();

		JComboBox<String> genreField = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Adventure"}));
		genreField.setSelectedIndex(0);

		MovieAddHandler handler = new MovieAddHandler(new JTextField("Test Movie"), new JButton(""), new JTextField("text"), new JTextField("1"), genreField, new JTextField("director"), new JTextArea("Description"), new JTextArea("actor1, actor2"), reviews, movieData, false,null);
		assertEquals("year exception", handler.handleAdd());

		MovieAddHandler handler1 = new MovieAddHandler(new JTextField("Test Movie"), new JButton(""), new JTextField("2023"), new JTextField("text"), genreField, new JTextField("director"), new JTextArea("Description"), new JTextArea("actor1, actor2"), reviews, movieData, false,null);
		assertEquals("duration exception", handler1.handleAdd());
	}
}
