package com.cgi.lauri.movieRecommender.logic;

import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class MovieRecommenderLogic {

    public List<Movie> recommendedMovies(List<MovieRating> ratings, List<Movie> allMovies, Customer customer) {
        //First filter out all of the movies that have a lower minimum age requirement than the age of the customer.
        filterByAge(allMovies,customer.getBirthDate());
        //Filter movies the user has seen
        filterByAlreadySeen(ratings,allMovies);
        //Set<String> allGenres = allMovies.stream().map((Movie::getGenre)).collect(Collectors.toSet());
        //Count all watched genres into a Hashmap
        HashMap<String,Integer> watchedGenresCounted = countWatchedGenres(ratings);
        //Watched score 1 ==-2, 2==-1 ...
        HashMap<String, Double> watchedGenresScore = assignGenresScore(ratings);
        //Assign multipliers
        addMultiplierToScores(watchedGenresCounted,watchedGenresScore);
        //Get how many of each genre to return
        HashMap<String,Integer> howManyRecommGenres = getQuantityOfRecommendedGenres(watchedGenresScore);
        //Get all the returnable movies
        return getRecommendedMovies(howManyRecommGenres,allMovies);
    }

    private List<Movie> getRecommendedMovies(HashMap<String,Integer> howManyRecommGenres, List<Movie> allMovies) {
        List<Movie> recommendedMovies = new ArrayList<>();
        Random random = new Random();
        for (String genre: howManyRecommGenres.keySet()) {
            int howManyMovies = howManyRecommGenres.get(genre);
            List<Movie> correctGenreMovies = getcorrectGenreMovies(genre,howManyMovies,allMovies);
            for (int i = 0; i < howManyMovies; i++) {
                if (correctGenreMovies.isEmpty())
                    break;
                int randomIndex = random.nextInt(correctGenreMovies.size());
                recommendedMovies.add(correctGenreMovies.get(randomIndex));
                correctGenreMovies.remove(randomIndex);
            }
        }
        return recommendedMovies;
    }

    private List<Movie> getcorrectGenreMovies(String genre, int howManyMovies, List<Movie> allMovies) {
        List<Movie> correctGenreMovies=new ArrayList<>();
        int count=0;
        for (Movie movie: allMovies) {
            if (count==howManyMovies)
                return correctGenreMovies;
            if (movie.getGenre().equals(genre)) {
                correctGenreMovies.add(movie);
                count++;
            }
        }
        return correctGenreMovies;
    }

    private HashMap<String,Integer> getQuantityOfRecommendedGenres(HashMap<String, Double> watchedGenresScore) {
        HashMap<String,Integer> recommendation=new HashMap<>();
        //0.09=1 0.99=10
        double totalScore=0;
        for(double val: watchedGenresScore.values()){
            totalScore+=val;
        }

        for (String genre: watchedGenresScore.keySet() ) {
            double relativeFrequency = watchedGenresScore.get(genre)/totalScore;
            double roundRelativeFreq = Math.ceil(relativeFrequency*10.0) /10.0;
            recommendation.put(genre,(int)(roundRelativeFreq*10));
        }
        return recommendation;
    }

    private void addMultiplierToScores(HashMap<String,Integer> watchedGenresCounted, HashMap<String, Double> watchedGenresScore) {
        List<String> sortedGenres = new ArrayList<>(watchedGenresCounted.keySet());
        sortedGenres.sort((a, b) -> watchedGenresCounted.get(b).compareTo(watchedGenresCounted.get(a)));
        String genre = sortedGenres.get(0);
        watchedGenresScore.put(genre,watchedGenresScore.get(genre)*1.5);
        for (int i = 1; i < sortedGenres.size(); i++) {
            genre=sortedGenres.get(i);
            double multiplier;
            if (i==1)
                multiplier=1.25;
            else if (i==2)
                multiplier=1.1;
            else break;
            watchedGenresScore.put(genre,watchedGenresScore.get(genre)*multiplier);
        }
    }

    private HashMap<String, Double> assignGenresScore(List<MovieRating> ratings) {
        HashMap<String, Double> genreScores=new HashMap<>();
        for (MovieRating rating: ratings) {
            String genre = rating.getMovie().getGenre();
            if (!genreScores.containsKey(genre)) {
                genreScores.put(genre, rating.getRating()*1.0);
            } else {
                genreScores.put(genre, genreScores.get(genre)+rating.getRating());
            }
        }
        return genreScores;
    }

    private HashMap<String, Integer> countWatchedGenres(List<MovieRating> ratings) {
        HashMap<String, Integer> watchedGenres=new HashMap<>();
        for (MovieRating rating: ratings) {
            String genre = rating.getMovie().getGenre();
            if (!watchedGenres.containsKey(genre)) {
                watchedGenres.put(genre,1);
            } else {
                watchedGenres.put(genre, watchedGenres.get(genre)+1);
            }
        }
        return watchedGenres;
    }

    private void filterByAlreadySeen(List<MovieRating> ratings, List<Movie> allMovies) {
        for (MovieRating movieRating: ratings) {
            allMovies.remove(movieRating.getMovie());
        }
    }

    public void filterByAge(List<Movie> movies, Date birthDate) {
        LocalDate currentDate = LocalDate.now();
        //SQL to localdate
        LocalDate localBirthDate = birthDate.toLocalDate();
        Period period = Period.between(localBirthDate, currentDate);
        int ageInYears = period.getYears();
        movies.removeIf(movie -> movie.getMinimumAge() > ageInYears);
    }
}
