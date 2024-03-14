package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.MovieRatingMapper;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.repository.CustomerRepository;
import com.cgi.lauri.movieRecommender.repository.MovieRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class MovieRatingServiceImpl implements MovieRatingService{
    private MovieRatingRepository movieRatingRepository;
    private CustomerRepository customerRepository;
    @Override
    public List<MovieRatingDto> getAllRatingsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with given id: " + customerId));
        List<MovieRating> movieRatings = movieRatingRepository.findAllByCustomer_Id(customerId);
        return movieRatings.stream().map((movieRating -> MovieRatingMapper.mapToMovieRatingDTO(movieRating))).collect(Collectors.toList());
    }
}
