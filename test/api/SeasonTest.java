package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SeasonTest {
    private Season season;

    @Before
    public void setUp() {
        // Initializing a Season object for testing
        List<Episode> episodes = new ArrayList<>();
        episodes.add(new Episode(1, 20));
        episodes.add(new Episode(2, 30));
        season = new Season(1, 2023, episodes);
    }

    @After
    public void tearDown() {
        // Clearing the season object after each test
        season = null;
    }

    @Test
    public void getSeasonNumber_shouldReturnCorrectSeasonNumber() {
        assertEquals(1, season.getSeasonNumber());
    }

    @Test
    public void getReleaseYear_shouldReturnCorrectReleaseYear() {
        assertEquals(2023, season.getReleaseYear());
    }

    @Test
    public void getEpisodes_shouldReturnCorrectEpisodesList() {
        List<Episode> episodes = season.getEpisodes();
        assertNotNull(episodes);
        assertEquals(2, episodes.size());
        assertEquals(20, episodes.get(0).getDuration());
        assertEquals(30, episodes.get(1).getDuration());
    }

    @Test
    public void setSeasonNumber_shouldUpdateSeasonNumber() {
        season.setSeasonNumber(2);
        assertEquals(2, season.getSeasonNumber());
    }

    @Test
    public void setReleaseYear_shouldUpdateReleaseYear() {
        season.setReleaseYear(2024);
        assertEquals(2024, season.getReleaseYear());
    }

    @Test
    public void setEpisodes_shouldUpdateEpisodesList() {
        List<Episode> newEpisodes = new ArrayList<>();
        newEpisodes.add(new Episode(3, 10));
        newEpisodes.add(new Episode(4, 10));

        season.setEpisodes(newEpisodes);
        List<Episode> updatedEpisodes = season.getEpisodes();

        assertNotNull(updatedEpisodes);
        assertEquals(2, updatedEpisodes.size());
        assertEquals(3, updatedEpisodes.get(0).getNumber());
        assertEquals(4, updatedEpisodes.get(1).getNumber());
    }
}
