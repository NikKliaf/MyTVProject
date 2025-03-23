package api;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import static gui.INITIALIZE.userData;

/**
 * Represents a Series object with various details like title, description, Seasons, etc.
 */
public class Series implements Serializable, VideoContent {
    private final String title;
    private final String description;
    private final boolean suitableForMinors;
    private final String category;
    private final String protagonists;
    private final List<Season> seasons;
    private List<Review> reviews;
    private List<Series> relatedSeries;
    private double averageRating;

    /**
     * Constructor for creating a Series object.
     *
     * @param title              the title of the series
     * @param description        a description of the series
     * @param suitableForMinors  indicates if the series is suitable for minors
     * @param category           the category/genre of the series
     * @param seasons            a list of seasons in the series
     * @param protagonists       the main protagonists in the series
     * @param reviews            list of reviews for the series
     */
    public Series(String title, String description, boolean suitableForMinors,
                  String category, List<Season> seasons, String protagonists, List<Review> reviews) {
        this.title = title;
        this.description = description;
        this.suitableForMinors = suitableForMinors;
        this.category = category;
        this.protagonists = protagonists;
        this.seasons = seasons;
        this.reviews = reviews;
        this.relatedSeries = new ArrayList<>();
        averageRating = calculateRating();
    }
    /**
     * Getter for the series title.
     * @return the title of the series
     */
    public String getTitle() {
        return title;
    }
    /**
     * Getter for the series description.
     * @return the description of the series
     */
    public String getDescription() {
        return description;
    }
    /**
     * Checks if the series is suitable for minors.
     * @return true if suitable for minors, false otherwise
     */
    public boolean isSuitableForMinors() {
        return suitableForMinors;
    }
    /**
     * Getter for the category/genre of the series.
     * @return the category/genre of the series
     */
    public String getCategory() {
        return category;
    }
    /**
     * Getter for the main protagonists in the series.
     * @return the main protagonists in the series
     */
    public String getProtagonists() {
        return protagonists;
    }
    /**
     * Getter for the list of seasons in the series.
     * @return the list of seasons in the series
     */
    public List<Season> getSeasons() {return seasons;}
    /**
     * Getter for the list of reviews for the series.
     * @return the list of reviews for the series
     */
    public List<Review> getReviews() { return reviews; }
    /**
     * Setter for the list of reviews for the series.
     * @param reviews user inputted reviews
     */
    public void setReviews(List<Review> reviews) {this.reviews = reviews != null ? new ArrayList<>(reviews) : new ArrayList<>();}

    /**
     * Adds a review to the list of reviews for the series.
     * @param review the review to add
     */
    public void addReview(Review review) { this.reviews.add(review); }
    /**
     * Retrieves the list of related series.
     * @return the list of related series
     */
    public List<Series> getRelatedSeries() {return relatedSeries;}
    /**
     * Calculates the average rating of the series.
     * @return the average rating of the series.
     */
    public double calculateRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (Review review : reviews) {
            totalRating += Integer.parseInt(review.getRating().replaceAll("Rating: ",""));
        }
        return totalRating / reviews.size();
    }
    /**
     * Series average rating getter
     * @return The series average rating.
     */
    public double getAverageRating() { return calculateRating(); }

    /**
     * Overriding equals helps comparing this Series object to another object for equality. Required for contains() function in Lists
     *
     * @param o The object to compare with this Series.
     * @return {@code true} if the specified object is equal to this Series,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Series series = (Series) o;
        return Objects.equals(title, series.title);
    }
}





