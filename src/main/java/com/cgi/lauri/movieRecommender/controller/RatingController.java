package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.mapper.CustomerMapper;
import com.cgi.lauri.movieRecommender.mapper.MovieMapper;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.model.MovieRatingKey;
import com.cgi.lauri.movieRecommender.service.CustomerService;
import com.cgi.lauri.movieRecommender.service.CustomerServiceImpl;
import com.cgi.lauri.movieRecommender.service.MovieRatingService;
import com.cgi.lauri.movieRecommender.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/user/ratings")
public class RatingController {
    private MovieRatingService movieRatingService;
    private CustomerService customerService;
    private MovieService movieService;

    @GetMapping("{id}")
    //@PathVariable necessary because we want to pass id as customerId
    public ResponseEntity<List<MovieRatingDto>> getRatingsById(@PathVariable("id") Long customerId) {
        List<MovieRatingDto> ratings = movieRatingService.getAllRatingsByCustomerId(customerId);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping
    public ResponseEntity<MovieRatingDto> createRating(@RequestBody MovieRatingDto movieRatingDto) {
        MovieRatingDto savedMovieRating = movieRatingService.createMovieRating(movieRatingDto);
        return new ResponseEntity<>(savedMovieRating, HttpStatus.CREATED);
    }
}
