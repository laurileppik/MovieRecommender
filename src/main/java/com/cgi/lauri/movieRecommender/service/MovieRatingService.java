package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;

import java.util.List;

public interface MovieRatingService {
    List<MovieRatingDto> getAllRatingsByCustomerId(Long customerId);

    MovieRatingDto createMovieRating(MovieRatingDto movieRatingDto);
}
