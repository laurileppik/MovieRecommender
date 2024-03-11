package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.repository.ScreenRepository;
import com.cgi.lauri.movieRecommender.service.ScreenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/screens")
public class ScreenController {
    private ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenDto> createScreen(@RequestBody ScreenDto screenDto) {
        ScreenDto savedScreen = screenService.createScreen(screenDto);
        return new ResponseEntity<>(savedScreen, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ScreenDto> getScreenById(@PathVariable("id") Long screenId) {
        ScreenDto screenDto = screenService.getScreen(screenId);
        return ResponseEntity.ok(screenDto);
    }
}
