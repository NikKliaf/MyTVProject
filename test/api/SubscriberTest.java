package api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SubscriberTest {

    @Test
    public void testGetFirstName() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());
        assertEquals("John", subscriber.getFirstName());
    }

    @Test
    public void testGetLastName() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());
        assertEquals("Doe", subscriber.getLastName());
    }

    @Test
    public void testGetUsername() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());
        assertEquals("john_doe", subscriber.getUsername());
    }

    @Test
    public void testGetPassword() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());
        assertEquals("password", subscriber.getPassword());
    }

    @Test
    public void testIsAdmin() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());
        assertFalse(subscriber.isAdmin());
    }

    @Test
    public void testGetFavorites() {
        List<VideoContent> favorites = new ArrayList<>();
        Movie movie = new Movie("Movie Title", "Description", true, 2023, 120,"Adventure","Director","Actor1, Actor2", new ArrayList<Review>());
        favorites.add(movie);

        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", favorites);
        assertEquals(favorites, subscriber.getFavorites());
    }

    @Test
    public void testAddToFavorites() {
        Subscriber subscriber = new Subscriber("John", "Doe", "john_doe", "password", new ArrayList<VideoContent>());

        Movie movie = new Movie("Movie Title", "Description", true, 2023, 120,"Adventure","Director","Actor1, Actor2", new ArrayList<Review>());
        subscriber.addToFavorites(movie);

        List<VideoContent> favorites = subscriber.getFavorites();
        assertTrue(favorites.contains(movie));
    }
}
