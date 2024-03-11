package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.Showtime;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDto {
    private Long id;

    private int noOfSeats;

    //private List<Movie> movies;

    private List<Boolean> occupiedSeats;

    private List<Showtime> showtimes;
}
