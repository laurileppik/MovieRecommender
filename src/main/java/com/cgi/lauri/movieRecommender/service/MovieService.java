package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.dto.MovieDto;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto);
    MovieDto getMovieById(Long movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long movieId, MovieDto movieDto);
    void deleteMovie(Long movieId);
    MovieDto addScreensToMovie(Long movieId, List<Long> screenIds);
}
