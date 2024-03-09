package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.CustomerMapper;
import com.cgi.lauri.movieRecommender.mapper.MovieMapper;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{
    private MovieRepository movieRepository;
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
        return null;
    }

    @Override
    public MovieDto updateMovie(Long movieId, MovieDto movieDto) {
        return null;
    }

    @Override
    public void deleteMovie(Long movieId) {

    }
}
