package com.cgi.lauri.movieRecommender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
