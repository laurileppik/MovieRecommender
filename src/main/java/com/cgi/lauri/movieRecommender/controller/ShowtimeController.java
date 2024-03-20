package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.service.MovieService;
import com.cgi.lauri.movieRecommender.service.ShowtimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private ShowtimeService showtimeService;
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<ShowtimeDto> createShowtime(@RequestBody ShowtimeDto showtimeDto) {
        Long movieId = movieService.getMovieIdByName(showtimeDto.getName());
        showtimeDto.setMovieId(movieId);
        ShowtimeDto savedShowTimeDto = showtimeService.createShowTime(showtimeDto);
        return new ResponseEntity<>(savedShowTimeDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes() {
        List<ShowtimeDto> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShowtimeDto> getShowtimeById(@PathVariable("id") Long showId){
        ShowtimeDto showtimeDto = showtimeService.getShowtimeById(showId);
        return ResponseEntity.ok(showtimeDto);
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimesById(@PathVariable("id") Long movieId){
        List<ShowtimeDto> allShowtimes = showtimeService.getAllShowtimesById(movieId);
        return ResponseEntity.ok(allShowtimes);
    }
}
