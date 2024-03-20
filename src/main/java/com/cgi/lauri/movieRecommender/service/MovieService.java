package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto);
    MovieDto getMovieById(Long movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long movieId, MovieDto movieDto);
    void deleteMovie(Long movieId);

    List<MovieDto> getFilteredMovies(String genre,Integer minAge,String language, String date,Long movieId);

    List<MovieDto> getAllRecommendedMovies(Long customerId);
    //MovieDto addScreensToMovie(Long movieId, List<Long> screenIds);
    Long getMovieIdByName(String name);

    List<String> getAllGenres();

    List<String> getAllAges();

    List<String> getAllLanguages();
}
