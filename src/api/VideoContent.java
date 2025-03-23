package api;

import java.util.List;

/**
 * The VideoContent interface represents content that can be viewed as video.
 */
public interface VideoContent {

    /**
     * Gets the title of the video content.
     * @return the title of the video content
     */
    String getTitle();

    /**
     * Gets the description of the video content.
     * @return the description of the video content
     */
    String getDescription();

    /**
     * Gets the category of the video content.
     * @return the category of the video content
     */
    String getCategory();

    /**
     * Checks if the video content is suitable for minors.
     * @return true if the video content is suitable for minors, otherwise false
     */
    boolean isSuitableForMinors();

    /**
     * Checks whether this VideoContent object is equal to the provided object.
     * @param o the object to compare for equality
     * @return true if the given object is equal to this VideoContent, otherwise false
     */
    @Override
    boolean equals(Object o);

    /**
     * Gets the protagonists of the video content.
     * @return the protagonists of the video content
     */
    String getProtagonists();

    /**
     * Gets the reviews associated with the video content.
     * @return the list of reviews for the video content
     */
    List<Review> getReviews();

    /**
     * Sets the reviews for the video content.
     * @param reviews the list of reviews to set
     */
    void setReviews(List<Review> reviews);
    /**
     * Adds a review for the video content.
     * @param review the review to add
     */
    void addReview(Review review);
    /**
     * Calculates the average rating of the Video content
     * @return the average rating of the video content
     */
    double calculateRating();
    /**
     * Gets the average rating of the Video content
     * @return the average rating of the video content
     */
    double getAverageRating();
}
