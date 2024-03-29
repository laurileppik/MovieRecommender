package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.service.MovieRatingService;
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
