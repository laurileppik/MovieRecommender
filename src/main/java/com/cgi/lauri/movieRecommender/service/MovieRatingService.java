package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.model.MovieRating;

import java.util.List;

public interface MovieRatingService {
    List<MovieRatingDto> getAllRatingsByCustomerId(Long customerId);
}
