package gui;


import api.*;

import java.util.ArrayList;
import java.util.List;

public class INITIALIZE {
    public static UserData userData;
    public static MovieData movieData;
    public static SeriesData seriesData;
    public INITIALIZE(UserData userData, MovieData movieData, SeriesData seriesData) {
        INITIALIZE.userData = userData;
        INITIALIZE.movieData = movieData;
        INITIALIZE.seriesData = seriesData;

        userData.addUser(new Admin(new AdminInfo("Nikiforos", "Kliafas", "admin1", "password1")));
        userData.addUser(new Admin(new AdminInfo("Giorgos", "Papanikolaou", "admin2", "password2")));
        if(userData.getUsersByUsername().get("user1") == null) {
            userData.addUser(new Subscriber("John", "Doe", "user1", "password1", new ArrayList<>()));
            userData.addToFavorites(seriesData.searchByTitle("Series 1"),(Subscriber) userData.getUsersByUsername().get("user1"));
            userData.addToFavorites(movieData.searchByTitle("The Lord of the Rings: The Fellowship of the Ring"),(Subscriber) userData.getUsersByUsername().get("user1"));
        } else {
            List<VideoContent> favorites1 = userData.getUsersByUsername().get("user1").getFavorites();
            userData.addUser(new Subscriber("John", "Doe", "user1", "password1", favorites1));
        }
        if(userData.getUsersByUsername().get("user2") == null) {
            userData.addUser(new Subscriber("Jane", "Doe", "user2", "password2", new ArrayList<>()));
            userData.addToFavorites(movieData.searchByTitle("Inception"),(Subscriber) userData.getUsersByUsername().get("user2"));
            userData.addToFavorites(seriesData.searchByTitle("Series 5"),(Subscriber) userData.getUsersByUsername().get("user2"));
        } else {
            List<VideoContent> favorites2 = userData.getUsersByUsername().get("user2").getFavorites();
            userData.addUser(new Subscriber("Jane", "Doe", "user2", "password2", favorites2));
        }

        List<Review> movieReviews1 = new ArrayList<>();
        movieReviews1.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "This is the best movie ever", "5"));
        movieReviews1.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Mediocre movie", "3"));
        List<Review> movieReviews2 = new ArrayList<>();
        movieReviews2.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Amazing movie", "4"));
        movieReviews2.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Bad movie", "2"));
        List<Review> movieReviews3 = new ArrayList<>();
        movieReviews3.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Pretty good", "3"));
        movieReviews3.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "The best movie", "5"));
        List<Review> movieReviews4 = new ArrayList<>();
        movieReviews4.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Very Good movie", "3"));
        movieReviews4.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Mediocre movie", "3"));
        List<Review> movieReviews5 = new ArrayList<>();
        movieReviews5.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "bad Movie", "2"));
        movieReviews5.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "The worst movie ever", "0"));


        movieData.addMovie(new Movie("The Lord of the Rings: The Fellowship of the Ring", "The first of an epic trilogy", false,2001,178,"Adventure","Peter Jackson", "Elijah Wood, Ian McKellen, Viggo Mortensen", movieReviews1));
        movieData.addMovie(new Movie("The Shawshank Redemption", "The dramatic story of a wrongly accused man", false,1994, 142,"Drama","Frank Darabont","Tim Robbins, Morgan Freeman, Bob Gunton", movieReviews2));
        movieData.addMovie(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", false, 2010, 148,"Sci-Fi","Christopher Nolan", "Leonardo Di-Caprio, Joseph Gordon-Levitt, Cillian Murphy", movieReviews3 ));
        movieData.addMovie(new Movie("Movie 4", "Sample Description", true, 2023, 123,"Drama","Sample director", "Actor1, actor2", movieReviews4));
        movieData.addMovie(new Movie("Movie 5", "Sample Description", false, 2012, 133,"Horror","John Smith", "Actor3, actor4, actor5", movieReviews5));

        List<Review> seriesReviews1 = new ArrayList<>();
        seriesReviews1.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "This is the best series ever", "5"));
        seriesReviews1.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Mediocre series", "3"));
        List<Review> seriesReviews2 = new ArrayList<>();
        seriesReviews2.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Amazing series", "4"));
        seriesReviews2.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Bad series", "2"));
        List<Review> seriesReviews3 = new ArrayList<>();
        seriesReviews3.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Pretty good", "3"));
        seriesReviews3.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Very Bad series", "1"));
        List<Review> seriesReviews4 = new ArrayList<>();
        seriesReviews4.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "Very Good series", "3"));
        seriesReviews4.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "Mediocre series", "3"));
        List<Review> seriesReviews5 = new ArrayList<>();
        seriesReviews5.add(new Review(userData.getUsersByUsername().get("user1").getFirstName()+" "+userData.getUsersByUsername().get("user1").getLastName(), "bad series", "2"));
        seriesReviews5.add(new Review(userData.getUsersByUsername().get("user2").getFirstName()+" "+userData.getUsersByUsername().get("user2").getLastName(), "The worst series ever", "0"));

        List<Season> seasons1 = new ArrayList<>();
        List<Episode> episodes1_1 = new ArrayList<>();
        List<Episode> episodes1_2 = new ArrayList<>();
        episodes1_1.add(new Episode(1,50));
        episodes1_1.add(new Episode(2,53));
        episodes1_1.add(new Episode(3,55));
        episodes1_2.add(new Episode(1,47));
        episodes1_2.add(new Episode(2,39));
        episodes1_2.add(new Episode(3,54));
        seasons1.add(new Season(1, 2023, episodes1_1));
        seasons1.add(new Season(2, 2023, episodes1_2));
        List<Season> seasons2 = new ArrayList<>();
        List<Episode> episodes2_1 = new ArrayList<>();
        List<Episode> episodes2_2 = new ArrayList<>();
        episodes2_1.add(new Episode(1, 45));
        episodes2_1.add(new Episode(2, 48));
        episodes2_1.add(new Episode(3, 51));
        episodes2_2.add(new Episode(1, 50));
        episodes2_2.add(new Episode(2, 47));
        episodes2_2.add(new Episode(3, 49));
        seasons2.add(new Season(1, 2024, episodes2_1));
        seasons2.add(new Season(2, 2024, episodes2_2));

        List<Season> seasons3 = new ArrayList<>();
        List<Episode> episodes3_1 = new ArrayList<>();
        List<Episode> episodes3_2 = new ArrayList<>();
        episodes3_1.add(new Episode(1, 42));
        episodes3_1.add(new Episode(2, 44));
        episodes3_1.add(new Episode(3, 46));
        episodes3_2.add(new Episode(1, 38));
        episodes3_2.add(new Episode(2, 41));
        episodes3_2.add(new Episode(3, 43));
        seasons3.add(new Season(1, 2025, episodes3_1));
        seasons3.add(new Season(2, 2025, episodes3_2));

        List<Season> seasons4 = new ArrayList<>();
        List<Episode> episodes4_1 = new ArrayList<>();
        List<Episode> episodes4_2 = new ArrayList<>();
        episodes4_1.add(new Episode(1, 47));
        episodes4_1.add(new Episode(2, 49));
        episodes4_1.add(new Episode(3, 52));
        episodes4_2.add(new Episode(1, 43));
        episodes4_2.add(new Episode(2, 46));
        episodes4_2.add(new Episode(3, 50));
        seasons4.add(new Season(1, 2026, episodes4_1));
        seasons4.add(new Season(2, 2026, episodes4_2));

        List<Season> seasons5 = new ArrayList<>();
        List<Episode> episodes5_1 = new ArrayList<>();
        List<Episode> episodes5_2 = new ArrayList<>();
        episodes5_1.add(new Episode(1, 44));
        episodes5_1.add(new Episode(2, 46));
        episodes5_1.add(new Episode(3, 49));
        episodes5_2.add(new Episode(1, 41));
        episodes5_2.add(new Episode(2, 44));
        episodes5_2.add(new Episode(3, 47));
        seasons5.add(new Season(1, 2027, episodes5_1));
        seasons5.add(new Season(2, 2027, episodes5_2));
        // Initialize 5 series with at least 2 seasons, each season with at least 3 episodes and reviews
        Series series1 = new Series("Series 1", "This is the first series", false, "Adventure", seasons1, "Actor1, actor2, actor3",seriesReviews1);
        Series series2 = new Series("Series 2", "This is the second series", false, "Comedy", seasons2, "Actor2, Actor5, Actor10", seriesReviews2);
        Series series3 = new Series("Series 3", "This is the third series", true, "Horror", seasons3, "actor53, actor26, actor33", seriesReviews3);
        Series series4 = new Series("Series 4", "This is the fourth series", false, "Action", seasons4, "actor94, actor62, actor67", seriesReviews4);
        Series series5 = new Series("Series 5", "This is the fifth series", true, "Sci-Fi", seasons5, "actor9, actor12, actor7", seriesReviews5);

        seriesData.addSeries(series1);
        seriesData.addSeries(series2);
        seriesData.addSeries(series3);
        seriesData.addSeries(series4);
        seriesData.addSeries(series5);

        if(!movieData.searchByTitle("The Shawshank Redemption").getRelatedMovies().contains(movieData.searchByTitle("Movie 4"))) {
            movieData.addRelatedMovieToFile(movieData.searchByTitle("The Shawshank Redemption"), movieData.searchByTitle("Movie 4"));
        }
        if (!seriesData.searchByTitle("Series 1").getRelatedSeries().contains(seriesData.searchByTitle("Series 5"))){
            seriesData.addRelatedSeriesToFile(seriesData.searchByTitle("Series 1"), seriesData.searchByTitle("Series 5"));
        }
        new LOGINWINDOW();
    }
}
