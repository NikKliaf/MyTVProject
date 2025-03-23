package api;

import java.io.Serializable;


/**
 * Represents an Episode object used in season class with a number and a duration.
 */
public class Episode implements Serializable {
    private int number;
    private int duration;
    /**
     * Constructor for creating an Episode object
     *
     * @param number the number of the episode
     * @param duration the duration of the episode
     */
    public Episode(int number, int duration){
        this.number = number;
        this.duration=duration;
    }
    /**
     * Episode number getter
     * @return The number of the
     */
    public int getNumber(){return number;}
    /**
     * Episode duration getter
     * @return The duration of the episode
     */
    public int getDuration(){return duration;}

    /**
     * Episode number setter
     * @param number the episode number to set
     */
    public void setNumber(int number){this.number = number;}
    /**
     * Episode duration setter
     * @param duration the episode duration to set
     */
    public void setDuration(int duration){this.duration = duration;}
}
