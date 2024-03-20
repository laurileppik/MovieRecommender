package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.service.MovieService;
import com.cgi.lauri.movieRecommender.service.OmdbService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;
    private OmdbService omdbService;
    //Create movie REST API
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        String imdbRating = omdbService.fetchImdbRating(movieDto.getName());
        String genre = omdbService.fetchGenre(movieDto.getName());
        String language = omdbService.fetchLanguage(movieDto.getName());
        movieDto.setImdbRating(Double.valueOf(imdbRating));
        movieDto.setGenre(genre);
        movieDto.setLanguage(language);
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
    public ResponseEntity<List<MovieDto>> getAllMovies(@RequestParam(required = false) String genre, @RequestParam(required = false) Integer minAge,
                                                       @RequestParam(required = false) String language,@RequestParam(required = false) String date,
                                                       @RequestParam(required = false) Long movieId) {
        List<MovieDto> filteredMovieDtos = movieService.getFilteredMovies(genre, minAge,language,date,movieId);
        return ResponseEntity.ok(filteredMovieDtos);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        List<String> movieGenres = movieService.getAllGenres();
        return ResponseEntity.ok(movieGenres);
    }

    @GetMapping("/ages")
    public ResponseEntity<List<String>> getAllAges() {
        List<String> movieAges = movieService.getAllAges();
        return ResponseEntity.ok(movieAges);
    }

    @GetMapping("/languages")
    public ResponseEntity<List<String>> getAllLanguages() {
        List<String> movieLanguages = movieService.getAllLanguages();
        return ResponseEntity.ok(movieLanguages);
    }

    @GetMapping("/sorted/{id}")
    public ResponseEntity<List<MovieDto>> getAllRecommendedMovies(@PathVariable("id") Long customerId) {
        List<MovieDto> recommendedMovies = movieService.getAllRecommendedMovies(customerId);
        return ResponseEntity.ok(recommendedMovies);
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

    /**@PostMapping("/{id}/screens")
    public ResponseEntity<MovieDto> addScreensToMovie(@PathVariable Long id, @RequestBody List<Long> screenIds) {
        MovieDto savedMovie = movieService.addScreensToMovie(id, screenIds);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }**/
}
