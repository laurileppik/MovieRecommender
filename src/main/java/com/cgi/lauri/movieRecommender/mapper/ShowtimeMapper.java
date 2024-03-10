package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.model.Showtime;

public class ShowtimeMapper {
    public static ShowtimeDto mapToShowtimeDto(Showtime showtime) {
        return new ShowtimeDto(
                showtime.getId(), showtime.getMovie(),showtime.getStartTime(),showtime.getEndTime(),showtime.getScreen()
        );
    }

    public static Showtime maptoShowtime(ShowtimeDto showtimeDto) {
        return new Showtime(
                showtimeDto.getId(), showtimeDto.getMovie(),showtimeDto.getStartTime(),showtimeDto.getEndTime(),showtimeDto.getScreen()
        );
    }
}
