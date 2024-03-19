package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.logic.MovieRecommenderLogic;
import com.cgi.lauri.movieRecommender.mapper.CustomerMapper;
import com.cgi.lauri.movieRecommender.mapper.MovieMapper;
import com.cgi.lauri.movieRecommender.mapper.MovieRatingMapper;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.repository.CustomerRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRatingRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{
    private MovieRepository movieRepository;
    private MovieRecommenderLogic movieRecommenderLogic;
    private CustomerServiceImpl customerService;
    private MovieRatingServiceImpl movieRatingService;
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
    public List<MovieDto> getFilteredMovies(String genre, Integer minAge, String language, String date) {
        List<Predicate<Movie>> filters = new ArrayList<>();

        if (genre != null) {
            filters.add(movie -> movie.getGenre().equals(genre));
        }
        if (minAge != null) {
            filters.add(movie -> movie.getMinimumAge() <= minAge);
        }
        if (language != null) {
            filters.add(movie -> movie.getLanguage().equals(language));
        }

        Predicate<Movie> combinedFilter = filters.stream().reduce(Predicate::and).orElse(movie -> true);
        List<MovieDto> filteredMovies = movieRepository.findAll().stream()
                .filter(combinedFilter)
                .map(MovieMapper::mapToMovieDTO)
                .collect(Collectors.toList());

        if (date != null) {
            String dateWithYear = date + "." + Year.now();
            LocalDate formattedDate = LocalDate.parse(dateWithYear, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            for (MovieDto movie : filteredMovies) {
                movie.setShowtimes(movie.getShowtimes().stream()
                        .filter(showtime -> showtime.getStartTime().toLocalDate().equals(formattedDate))
                        .collect(Collectors.toList()));
            }
        }

        return filteredMovies;
    }


    @Override
    public List<MovieDto> getAllRecommendedMovies(Long customerId) {
        Customer customer = CustomerMapper.maptoCustomer(customerService.getCustomerById(customerId));

        List<MovieRating> ratings = movieRatingService.getAllRatingsByCustomerId(customerId).stream().map(movieRatingDto ->
                MovieRatingMapper.mapToMovieRating(movieRatingDto)).collect(Collectors.toList());
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> recommendedMovies = movieRecommenderLogic.recommendedMovies(ratings,allMovies,customer);
        return recommendedMovies.stream().map((movie -> MovieMapper.mapToMovieDTO(movie))).
                collect(Collectors.toList());
    }

    @Override
    public Long getMovieIdByName(String name) {
        Movie movie = movieRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given name " + name)
        );
        return movie.getId();
    }

    @Override
    public List<String> getAllGenres() {
        return movieRepository.findAllGenres();
    }

    @Override
    public List<String> getAllAges() {
        return movieRepository.findAllAges();
    }

    @Override
    public List<String> getAllLanguages() {
        return movieRepository.findAllLanguages();
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
