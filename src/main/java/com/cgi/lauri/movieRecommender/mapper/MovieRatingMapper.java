package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.model.MovieRating;

public class MovieRatingMapper {
    public static MovieRatingDto mapToMovieRatingDTO(MovieRating movieRating) {
        return new MovieRatingDto (movieRating.getCustomer(),movieRating.getMovie(), movieRating.getRating());
    }

    public static MovieRating mapToMovieRating(MovieRatingDto movieRatingDto) {
        return new MovieRating (movieRatingDto.getCustomer(),movieRatingDto.getMovie(),movieRatingDto.getRating());
    }
    public static MovieRatingDto mapToMovieRatingDTOwithoutRating(MovieRating movieRating) {
        return new MovieRatingDto (movieRating.getCustomer(),movieRating.getMovie());
    }
    public static MovieRating mapToMovieRatingwithoutRating(MovieRatingDto movieRatingDto) {
        return new MovieRating (movieRatingDto.getCustomer(),movieRatingDto.getMovie());
    }
}
