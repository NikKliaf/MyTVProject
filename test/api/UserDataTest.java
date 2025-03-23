package api;

import api.Admin;
import api.Subscriber;
import api.UserData;
import api.VideoContent;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {
    private static final String TEST_FILE_NAME = "test_users.ser";
    private UserData userData;

    @BeforeEach
    void setUp() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        userData = new UserData(TEST_FILE_NAME);
    }
    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void addUser_AddSubscriber_Success() {
        List<VideoContent> favorites = new ArrayList<>();
        Subscriber subscriber = new Subscriber("John","Doe","subscriber1", "password", favorites);
        userData.addUser(subscriber);
        Map<String, User> usersByUsername = userData.getUsersByUsername();
        assertTrue(usersByUsername.containsKey("subscriber1"));
        assertEquals(subscriber, usersByUsername.get("subscriber1"));
    }

    @Test
    void addUser_AddAdmin_Success() {
        Admin admin = new Admin(new AdminInfo("John", "Doe","admin1", "password"));
        userData.addUser(admin);
        Map<String, User> usersByUsername = userData.getUsersByUsername();
        assertTrue(usersByUsername.containsKey("admin1"));
        assertEquals(admin, usersByUsername.get("admin1"));
    }

    @Test
    void addToFavorites_Success() {
        List<VideoContent> favorites = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Review Title", "review123",Integer.toString(1)));
        Subscriber subscriber = new Subscriber("John","Doe","subscriber2", "password", favorites);
        VideoContent videoContent = new Movie("Video Title", "video123",true,2004,200,"Horror","director", "actor1, actor2",reviews);
        userData.addUser(subscriber);
        userData.addToFavorites(videoContent, subscriber);
        Map<String, User> usersByUsername = userData.getUsersByUsername();
        assertTrue(usersByUsername.containsKey("subscriber2"));
        assertEquals(1, ((Subscriber) usersByUsername.get("subscriber2")).getFavorites().size());
        assertTrue(((Subscriber) usersByUsername.get("subscriber2")).getFavorites().contains(videoContent));
    }

    @Test
    void removeFromFavorites_Success() {
        List<VideoContent> favorites = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        List<Season> seasons = new ArrayList<>();
        Subscriber subscriber = new Subscriber("John","Doe","subscriber3", "password",favorites);
        VideoContent videoContent = new Series("Video Title", "video456",true,"Horror",seasons,"director", reviews);
        subscriber.addToFavorites(videoContent);
        userData.addUser(subscriber);
        userData.removeFromFavorites(videoContent, subscriber);
        Map<String, User> usersByUsername = userData.getUsersByUsername();
        assertTrue(usersByUsername.containsKey("subscriber3"));
        assertEquals(0, ((Subscriber) usersByUsername.get("subscriber3")).getFavorites().size());
        assertFalse(((Subscriber) usersByUsername.get("subscriber3")).getFavorites().contains(videoContent));
    }
}
