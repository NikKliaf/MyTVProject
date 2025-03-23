package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gui.INITIALIZE.movieData;

/**
 * Represents a Movie object with various details like title, description, duration, etc.
 */
public class Movie implements Serializable, VideoContent {
    private final String title;
    private final String description;
    private final boolean suitableForMinors;
    private final int releaseYear;
    private final int duration;
    private final String category;
    private final String director;
    private final String protagonists;
    private List<Review> reviews;
    private List<Movie> relatedMovies;
    private double averageRating;
    /**
     * Constructor for creating a movie Object
     *
     * @param title the tile of the film
     * @param description a description
     * @param suitableForMinors can people <18 watch it
     * @param releaseYear what year was it released
     * @param duration in minutes
     * @param category Horror Drama Sci-Fi Comedy and Adventure
     * @param director the director of the movie
     * @param protagonists  the major actors who play the protagonists
     */
    public Movie(String title, String description, boolean suitableForMinors, int releaseYear, int duration, String category, String director, String protagonists, List<Review> reviews) {
        this.title = title;
        this.description = description;
        this.suitableForMinors = suitableForMinors;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.category = category;
        this.director = director;
        this.protagonists = protagonists;
        this.reviews = reviews != null ? new ArrayList<>(reviews) : new ArrayList<>();
        this.relatedMovies = new ArrayList<>();
        averageRating = calculateRating();
    }
    /**
     * Movie title getter
     * @return The movie title
     */
    public String getTitle () {return title;}

    /**
     * Movie description getter
     * @return The movie Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Movie Suitability for Minors getter
     * @return The movie Suitability for Minors
     */
    public boolean isSuitableForMinors() {
        return suitableForMinors;
    }
    /**
     * Movie release year getter
     * @return The movie release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }
    /**
     * Movie duration getter
     * @return The movie duration
     */
    public int getDuration() {
        return duration;
    }
    /**
     * Movie category getter
     * @return The movie category
     */
    public String getCategory()  {
        return category;
    }
    /**
     * Movie director getter
     * @return The movie director
     */
    public String getDirector() {return director;}
    /**
     * Movie cast getter
     * @return The movie cast
     */
    public String getProtagonists() {return protagonists;};

    /**
     * Movie reviews getter method
     * @return The list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }
    /**
     * Movie reviews setter method
     * @param reviews user inputted reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews != null ? new ArrayList<>(reviews) : new ArrayList<>();    }

    @Override
    public void addReview(Review review) { this.reviews.add(review); }

    /**
     * Retrieves the list of related movies.
     * @return The list of movies related to this movie.
     */
    public List<Movie> getRelatedMovies() {
        return relatedMovies;
    }

    /**
     * Calculates the average rating of the movie.
     * @return the average rating of the movie.
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
     * Movie average rating gatter
     * @return The movie average rating.
     */
    public double getAverageRating() { return calculateRating(); }

    /**
     * Overriding equals helps comparing this Movie object to another object for equality. Required for contains() function in Lists
     *
     * @param o The object to compare with this Movie.
     * @return {@code true} if the specified object is equal to this Movie,
     *         {@code false} otherwise.
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title);
    }
}
