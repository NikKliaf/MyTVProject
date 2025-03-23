import gui.*;
import api.*;

/**
 * Το πρόγραμμά σας πρέπει να έχει μόνο μία main, η οποία πρέπει να είναι η παρακάτω.
 * <p>
 * <p>
 * ************* ΜΗ ΣΒΗΣΕΤΕ ΑΥΤΗ ΤΗΝ ΚΛΑΣΗ ************
 */
public class Main {
    public static void main(String[] args) {
        String moviefilename = "src/resources/movies_data.ser";
        String userfilename = "src/resources/users_data.ser";
        String seriesfilename = "src/resources/series_data.ser";
        UserData userData = new UserData(userfilename);
        MovieData movieData = new MovieData(moviefilename);
        SeriesData seriesData = new SeriesData(seriesfilename);

        new INITIALIZE(userData,movieData,seriesData);
    }
}
