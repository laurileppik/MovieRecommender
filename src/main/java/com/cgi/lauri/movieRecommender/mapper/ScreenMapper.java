package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.model.Screen;

public class ScreenMapper {
    public static ScreenDto mapToScreenDTO(Screen screen) {
        return new ScreenDto(
                screen.getId(), screen.getNoOfSeats(),screen.getOccupiedSeats(),screen.getShowtimes(), screen.getRows(), screen.getSeatsInRow()
        );
    }

    public static Screen maptoScreen(ScreenDto screenDto) {
        return new Screen(
                screenDto.getId(), screenDto.getNoOfSeats(), screenDto.getOccupiedSeats(),screenDto.getShowtimes(), screenDto.getRows(), screenDto.getSeatsInRow()
        );
    }
}
