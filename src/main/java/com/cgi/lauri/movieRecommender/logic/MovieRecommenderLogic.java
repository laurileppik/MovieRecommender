package com.cgi.lauri.movieRecommender.logic;

import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieRecommenderLogic {
    public List<Movie> recommendedMovies(List<MovieRating> ratings) {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(ratings.get(0).getMovie());
        return movieList;
    }
}
