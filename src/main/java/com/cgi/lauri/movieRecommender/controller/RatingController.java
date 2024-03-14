package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.MovieRatingDto;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.service.MovieRatingService;
import lombok.AllArgsConstructor;
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
    //@PathVariable necessary because we want to pass id as movieId
    public ResponseEntity<List<MovieRatingDto>> getRatingsById(@PathVariable("id") Long movieId) {
        List<MovieRatingDto> ratings = movieRatingService.getAllRatingsByCustomerId(movieId);
        return ResponseEntity.ok(ratings);
    }
}
