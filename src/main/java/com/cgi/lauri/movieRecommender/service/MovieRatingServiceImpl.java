package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.MovieRatingMapper;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.model.MovieRatingKey;
import com.cgi.lauri.movieRecommender.repository.CustomerRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRatingRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class MovieRatingServiceImpl implements MovieRatingService{
    private MovieRatingRepository movieRatingRepository;
    private CustomerRepository customerRepository;
    private MovieRepository movieRepository;
    @Override
    public List<MovieRatingDto> getAllRatingsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with given id: " + customerId));
        List<MovieRating> movieRatings = movieRatingRepository.findAllByCustomer_Id(customerId);
        return movieRatings.stream().map((movieRating -> MovieRatingMapper.mapToMovieRatingDTO(movieRating))).collect(Collectors.toList());
    }

    @Override
    public MovieRatingDto createMovieRating(MovieRatingKey movieRatingKey) {
        Long customerId = movieRatingKey.getCustomerId();
        Long movieId = movieRatingKey.getMovieId();
        System.out.println();
        System.out.println(" a sd asdasd s " + customerId.getClass() +  "   " + customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id "+ customerId)
        );
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie not found with id "+ movieId)
        );

        MovieRating movieRating = new MovieRating(customer,movie,2);
        System.out.println(movieRating + "   " + movieRating.getMovie().getId() + "     " + movieRating.getCustomer().getUserName());
        //MovieRating savedMovie =
        //movieRatingRepository.saveRating(movieId,customerId);
        MovieRating savedMovieRating = movieRatingRepository.save(movieRating);
        return MovieRatingMapper.mapToMovieRatingDTO(savedMovieRating);
    }
}
