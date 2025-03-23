package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EpisodeTest {

    private Episode episode;

    @Before
    public void setUp() {
        episode = new Episode(1, 45);
    }

    @After
    public void tearDown() {
        episode = null;
    }

    @Test
    public void getNumber() {
        assertEquals(1, episode.getNumber());
    }

    @Test
    public void getDuration() {
        assertEquals(45, episode.getDuration());
    }

    @Test
    public void setNumber() {
        episode.setNumber(2);
        assertEquals(2, episode.getNumber());
    }

    @Test
    public void setDuration() {
        episode.setDuration(50);
        assertEquals(50, episode.getDuration());
    }
}
