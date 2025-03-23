package api;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Review object that contains the name, review, and rating of a user
 * for a video content.
 */
public class Review implements Serializable {
    private final String name;
    private String review;
    private String rating;

    /**
     * Constructor for creating a Review object
     * @param name the name of the user
     * @param review the review of the user
     * @param rating the rating of the user
     */
    public Review(String name,String review,String rating){
        this.name=name;
        this.review=review;
        this.rating=rating;
    }
    /**
     * Review name getter
     * @return the name of the user
     */
    public String getName(){
        return name;
    }
    /**
     * Review review getter
     * @return the review of the user
     */
    public String getReview(){ return review; }
    /**
     * Review rating getter
     * @return the rating of the user
     */
    public String getRating(){return rating;}
}
