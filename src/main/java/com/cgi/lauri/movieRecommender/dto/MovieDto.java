package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.cgi.lauri.movieRecommender.model.Showtime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String name;
    private String genre;
    private String language;
    private List<Showtime> showtimes;
    private Integer minimumAge;
    private Set<MovieRating> ratings;
    private Double imdbRating;
}
