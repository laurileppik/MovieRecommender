package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.Showtime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDto {
    private Long id;
    private int noOfSeats;
    private List<Boolean> occupiedSeats;
    private List<Showtime> showtimes;
    private int rows;
    private int seatsInRow;
    private List<Integer> recommendedSeats;
}
