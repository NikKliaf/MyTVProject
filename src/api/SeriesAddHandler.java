package api;

import javax.swing.*;
import java.util.List;


public class SeriesAddHandler {
    private String title;
    private String description;
    private String age;
    private String genre;
    private List<Season> seasons;
    private String protagonists;
    private List<Review> reviews;
    private boolean exists;
    private Series oldSeries;
    private SeriesData seriesData;
    /**
     * Handles the addition of new series based on user input.
     * @param titleField       JTextField for series title input
     * @param descriptionField JTextArea for series description input
     * @param ageButton        JButton for suitability selection
     * @param genreField       JComboBox for genre selection
     * @param seasons          List of Season objects for the series
     * @param protagonistsField JTextArea for series protagonists input
     * @param reviews          List of Review objects for the series
     */
    public SeriesAddHandler(JTextField titleField, JTextArea descriptionField, JButton ageButton, JComboBox<String> genreField,  List<Season> seasons, JTextArea protagonistsField, List<Review> reviews, boolean exists, Series oldSeries, SeriesData seriesData) {
        this.title = titleField.getText();
        this.description = descriptionField.getText();
        this.age = ageButton.getText();
        this.genre = genreField.getSelectedItem().toString();
        this.seasons = seasons;
        this.protagonists = protagonistsField.getText();
        this.reviews = reviews;
        this.exists = exists;
        this.oldSeries = oldSeries;
        this.seriesData = seriesData;
    }

    /**
     * Handles the addition of new series.
     */
    public String handleAdd(){
        if(title.equals(" Add Title") || genre.equals("<html><font color ='gray'>Categories: </font></body></html>") || description.equals(" Add Description") || protagonists.equals(" Add Protagonists")){
            return "fill";
        } else if (seasons.isEmpty()){
            return "no season";
        } else {
            Series series = new Series(
                    title,
                    description,
                    age.equals(" Suitable for Minors"),
                    genre,
                    seasons,
                    protagonists,
                    reviews
            );
            if(exists) {
                seriesData.updateSeries(oldSeries, series);
                return "edited";
            } else {
                if (seriesData.addSeries(series)) {
                    return "added";
                } else {
                    return "exists";
                }
            }
        }
    }
}
