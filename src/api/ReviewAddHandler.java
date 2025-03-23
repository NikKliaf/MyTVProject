package api;

import javax.swing.*;


public class ReviewAddHandler {
    private MovieData movieData;
    private SeriesData seriesData;
    private String reviewText;
    private User user;
    private VideoContent video;
    private String rating;
    /**
     * Handles the addition of reviews for movies or series based on user input.
     * @param reviewArea  JTextArea for user review input
     * @param ratingLabel JLabel displaying the rating
     * @param user        User object representing the user adding the review
     * @param video       VideoContent object (either Movie or Series) for which the review is added
     */
    public ReviewAddHandler(JTextArea reviewArea, JLabel ratingLabel, User user, VideoContent video, MovieData movieData, SeriesData seriesData) {
        this.reviewText = reviewArea.getText();
        this.rating = ratingLabel.getText();
        this.user = user;
        this.video = video;
        this.movieData = movieData;
        this.seriesData = seriesData;
    }

    /**
     * Handles the addition of reviews for movies or series.
     */
    public String handleAdd() {
        if (reviewText.equals(" Add Your Review (Up to 500 characters)") || reviewText.isEmpty()) {
            return "fill";
        } else {
            try {
                this.rating = String.valueOf(Integer.parseInt(rating));
                Review review = new Review(
                        "User: "+ user.getFirstName() + " " + user.getLastName(),
                        "Review: "+ reviewText,
                        "Rating: " + rating
                );
                if(video instanceof Movie) {
                    if (movieData.addReview(review, (Movie) video)) {
                        return "added";
                    } else {
                        return "exists";
                    }
                } else {
                    if(seriesData.addReview(review, (Series) video)) {
                        return "added";
                    } else {
                        return "exists";
                    }
                }
            } catch (NumberFormatException e) {
                return "failed";
            }
        }
    }
}
