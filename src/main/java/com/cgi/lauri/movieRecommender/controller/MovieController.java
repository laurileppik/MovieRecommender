package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.mapper.MovieMapper;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;
    //Create movie REST API
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        MovieDto savedMovie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    //Get movie REST API
    @GetMapping("{id}")
    //@PathVariable necessary because we want to pass id as movieId
    public ResponseEntity<MovieDto> getMoviebyId(@PathVariable("id") Long movieId) {
        MovieDto movieDto = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movieDto);
    }

    //Get all movies REST API
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<MovieDto> allMovieDtos = movieService.getAllMovies();
        return ResponseEntity.ok(allMovieDtos);
    }

    //Update movie REST API
    @PutMapping("{id}")
    //@PathVariable necessary because we want to pass id as movieId
    public ResponseEntity<MovieDto> updateMovie(@PathVariable("id") Long movieId,
                                                @RequestBody MovieDto updatedMovie) {
        MovieDto movieDto = movieService.updateMovie(movieId,updatedMovie);
        return ResponseEntity.ok(movieDto);
    }

    //Delete movie REST API
    @DeleteMapping("{id}")
    //@PathVariable necessary because we want to pass id as movieId
    public ResponseEntity<String> deleteMovie(@PathVariable("id")Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok("Movie deleted succesfully");
    }
}
