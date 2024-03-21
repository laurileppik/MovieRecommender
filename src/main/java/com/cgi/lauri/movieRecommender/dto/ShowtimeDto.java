package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.model.Showtime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeDto {
    private Long id;
    private String name;

    private Long movieId;

    private Long screenId;

    private LocalDateTime startTime;

    private Integer duration;

    public ShowtimeDto(Long id, Long movieId, Long screenId, LocalDateTime startTime, Integer duration) {
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.duration=duration;
    }
}
