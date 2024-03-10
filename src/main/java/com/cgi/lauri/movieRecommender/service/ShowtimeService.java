package com.cgi.lauri.movieRecommender.service;


import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;

import java.util.List;

public interface ShowtimeService {
    ShowtimeDto createShowTime(ShowtimeDto showtimeDto);
    List<ShowtimeDto> getAllShowtimes();
}
