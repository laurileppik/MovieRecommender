package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.model.Showtime;

public class ShowtimeMapper {
    public static ShowtimeDto mapToShowtimeDto(Showtime showtime) {
        return new ShowtimeDto(
                showtime.getId(), showtime.getMovie().getId(),showtime.getScreen().getId(),showtime.getStartTime(), showtime.getDuration()
        );
    }
}
