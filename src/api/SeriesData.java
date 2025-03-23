package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the storing, loading, adding, deleting, updating, and searching of Series objects to/from a file using serialization.
 */
public class SeriesData implements Serializable {
    private final String fileName;
    private List<Series> seriesList;

    /**
     * Constructor of SeriesData class.
     * @param fileName The name of the file to store series data.
     */
    public SeriesData(String fileName) {
        this.fileName = fileName;
        this.seriesList = new ArrayList<>();
        loadSeries();
    }

    /**
     * Adds a series to the list of series.
     * @param series The series to be added.
     * @return True if the series was added successfully, false if the series already exists.
     */
    public boolean addSeries(Series series) {
        if (!seriesList.contains(series)) {
            seriesList.add(series);
            saveSeries();
            return true;
        }
        return false;
    }

    /**
     * Saves a list of series to the file.
     */
    private void saveSeries() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(seriesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of series from the file.
     */
    public void loadSeries() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            seriesList = (List<Series>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the list of series.
     * @return The list of series.
     */
    public List<Series> getSeriesList() {
        return seriesList;
    }

    /**
     * Deletes a series from the list of series.
     * @param series The series to be deleted.
     */
    public void deleteSeries(Series series) {
        seriesList.remove(series);
        saveSeries();
    }

    /**
     * Updates a series in the list of series.
     * @param oldSeries The old series.
     * @param newSeries The new series.
     */
    public void updateSeries(Series oldSeries, Series newSeries) {
        int index = seriesList.indexOf(oldSeries);
        if (index != -1) {
            seriesList.set(index, newSeries);
            saveSeries();
        }
    }



    /**
     * Searches for a series by its title.
     * @param title The title of the series to search for.
     * @return The series found with the provided title, or null if not found.
     */
    public Series searchByTitle(String title) {
        List<Series> series = getSeriesList();

        for (Series seriesItem : series) {
            if (seriesItem.getTitle().equalsIgnoreCase(title)) {
                return seriesItem;
            }
        }
        return null;
    }
    /**
     * Adds a review to a series.
     * @param series The movie to add the review to.
     * @param review The review to be added.
     */
    public boolean addReview(Review review, Series series) {
        if(series.getReviews().contains(review)) {
            return false;
        } else {
            series.getReviews().add(review);
            saveSeries();
            return true;
        }
    }
    /**
     * Removes a review from a series.
     * @param review The movie to remove the review from.
     * @param series The review to be removed.
     */
    public void removeReview(Review review, Series series) {
        series.getReviews().remove(review);
        saveSeries();
    }

    /**
     * Adds a related series to a series. Also adds the parent series to the series
     * related series list.
     * @param parentSeries the series to add the related series to.
     * @param series the related series to be added.
     */
    public void addRelatedSeriesToFile(Series parentSeries, Series series){
        parentSeries.getRelatedSeries().add(series);
        series.getRelatedSeries().add(parentSeries);
        saveSeries();
    }
    /**
     * Removes a related series from a series. Also removes the parent series from the series
     * related series list.
     * @param parentSeries the series to remove the related series from.
     * @param series the related series to be removed.
     */
    public void removeRelatedSeriesFromFile(Series parentSeries, Series series){
        parentSeries.getRelatedSeries().remove(series);
        series.getRelatedSeries().remove(parentSeries);
        saveSeries();
    }
}
