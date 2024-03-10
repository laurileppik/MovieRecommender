package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.service.ShowtimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<ShowtimeDto> createShowtime(@RequestBody ShowtimeDto showtimeDto) {
        ShowtimeDto savedShowTimeDto = showtimeService.createShowTime(showtimeDto);
        return new ResponseEntity<>(savedShowTimeDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes() {
        List<ShowtimeDto> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.ok(showtimes);
    }
}
