package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.ShowtimeMapper;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.model.Showtime;
import com.cgi.lauri.movieRecommender.repository.MovieRepository;
import com.cgi.lauri.movieRecommender.repository.ScreenRepository;
import com.cgi.lauri.movieRecommender.repository.ShowtimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
    private ShowtimeRepository showtimeRepository;
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    @Override
    public ShowtimeDto createShowTime(ShowtimeDto showtimeDto) {
        Movie movie = movieRepository.findById(showtimeDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie does not exist with the given id: " + showtimeDto.getMovieId())
        );
        Screen screen = screenRepository.findById(showtimeDto.getScreenId()).orElseThrow(
                () -> new ResourceNotFoundException("Screen does not exist with the given id: " + showtimeDto.getScreenId())
        );

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setScreen(screen);
        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setEndTime(showtimeDto.getEndTime());

        Showtime savedShowtime = showtimeRepository.save(showtime);
        return ShowtimeMapper.mapToShowtimeDto(savedShowtime);
    }

    @Override
    public List<ShowtimeDto> getAllShowtimes() {
        return showtimeRepository.findAll().stream().map((showtime) -> ShowtimeMapper.mapToShowtimeDto(showtime)).
                collect(Collectors.toList());
    }

    @Override
    public ShowtimeDto getShowtimeById(Long showId) {
        Showtime showtime = showtimeRepository.findById(showId).orElseThrow(
                () -> new ResourceNotFoundException("Showtime does not exist with the given id: " + showId)
        );
        return ShowtimeMapper.mapToShowtimeDto(showtime);
    }


}
