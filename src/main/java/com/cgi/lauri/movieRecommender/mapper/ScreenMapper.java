package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.Screen;

public class ScreenMapper {
    public static ScreenDto mapToScreenDTO(Screen screen) {
        return new ScreenDto(
                screen.getId(), screen.getNoOfSeats(),screen.getMovies(),screen.getOccupiedSeats(),screen.getShowtimes()
        );
    }

    public static Screen maptoMovie(ScreenDto screenDto) {
        return new Screen(
                screenDto.getId(), screenDto.getNoOfSeats(), screenDto.getMovies(), screenDto.getOccupiedSeats(),screenDto.getShowtimes()
        );
    }
}
