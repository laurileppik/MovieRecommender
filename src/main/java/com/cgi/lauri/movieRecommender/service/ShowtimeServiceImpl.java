package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ShowtimeDto;
import com.cgi.lauri.movieRecommender.mapper.ShowtimeMapper;
import com.cgi.lauri.movieRecommender.model.Showtime;
import com.cgi.lauri.movieRecommender.repository.ShowtimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
    private ShowtimeRepository showtimeRepository;
    @Override
    public ShowtimeDto createShowTime(ShowtimeDto showtimeDto) {
        Showtime showtime = ShowtimeMapper.maptoShowtime(showtimeDto);
        Showtime savedShowtime = showtimeRepository.save(showtime);
        return ShowtimeMapper.mapToShowtimeDto(savedShowtime);
    }
}
