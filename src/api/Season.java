package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Season object used in the series class with various details like seasonNumber, releaseYear and an episode list.
 */
public class Season implements Serializable {
    private int seasonNumber;
    private int releaseYear;
    private List<Episode> episodes;

    /**
     * Constructor for creating a Season Object
     *
     * @param seasonNumber the season number
     * @param releaseYear the release year
     * @param episodes the episode list
     */
    public Season(int seasonNumber, int releaseYear,List<Episode> episodes) {
        this.seasonNumber = seasonNumber;
        this.releaseYear = releaseYear;
        this.episodes = episodes;
    }
    /**
     * Getter for the season number
     * @return the season number
     */
    public int getSeasonNumber(){return seasonNumber;}
    /**
     * Setter for the Season number
     * @param seasonNumber the new season number
     */
    public void setSeasonNumber(int seasonNumber){this.seasonNumber = seasonNumber;}

    /**
     * Getter for the release year
     * @return the release year
     */
    public int getReleaseYear(){
        return releaseYear;
    }
    /**
     * Setter for the release year
     * @param releaseYear the new release year
     */
    public void setReleaseYear(int releaseYear){this.releaseYear = releaseYear;}

    /**
     * Getter for the episode list
     * @return the episode list
     */
    public List<Episode> getEpisodes(){
        return episodes;
    }
    /**
     * Setter for the episode list
     * @param episodes the new episode list
     */
    public void setEpisodes(List<Episode> episodes){this.episodes = episodes;}
}
