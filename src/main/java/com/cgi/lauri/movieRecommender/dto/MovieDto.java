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
public class MovieDto {
    private Long id;
    private String name;
    private String genre;
    private String language;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer minimumAge;
}
