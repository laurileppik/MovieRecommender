package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.logic.MovieRecommenderLogic;
import com.cgi.lauri.movieRecommender.mapper.MovieMapper;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.repository.MovieRatingRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{
    private MovieRepository movieRepository;
    private MovieRatingRepository movieRatingRepository;
    private MovieRecommenderLogic movieRecommenderLogic;
    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = MovieMapper.maptoMovie(movieDto);
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.mapToMovieDTO(savedMovie);
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given id: " + movieId)
        );

        return MovieMapper.mapToMovieDTO(movie);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map((movie -> MovieMapper.mapToMovieDTO(movie))).
                collect(Collectors.toList());
    }

    @Override
    public MovieDto updateMovie(Long movieId, MovieDto updatedMovie) {
        Movie movie= movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given id: " + movieId)
        );

        movie.setName(updatedMovie.getName());
        movie.setGenre(updatedMovie.getGenre());
        movie.setLanguage(updatedMovie.getLanguage());
        movie.setMinimumAge(updatedMovie.getMinimumAge());
        //EI OLE UPDATE showtime/screen

        Movie updatedMovieObj = movieRepository.save(movie);
        return MovieMapper.mapToMovieDTO(updatedMovieObj);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given id: " + movieId)
        );
        movieRepository.deleteById(movieId);
    }

    @Override
    public List<MovieDto> getFilteredMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre).stream().map((movie -> MovieMapper.mapToMovieDTO(movie))).
                collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAllRecommendedMovies(Long customerId) {
        List<MovieRating> ratings = movieRatingRepository.findAllByCustomer_Id(customerId);
        List<Movie> recommendedMovies = movieRecommenderLogic.recommendedMovies(ratings);
        return recommendedMovies.stream().map((movie -> MovieMapper.mapToMovieDTO(movie))).
                collect(Collectors.toList());
    }

    /**@Override
    public MovieDto addScreensToMovie(Long movieId, List<Long> screenIds) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given id: " + movieId)
        );
        List<Screen> screens = screenRepository.findAllById(screenIds);
        movie.getScreens().addAll(screens);
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.mapToMovieDTO(savedMovie);
    }**/


}
