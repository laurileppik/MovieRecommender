package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.mapper.ScreenMapper;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.repository.ScreenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService{
    private ScreenRepository screenRepository;
    @Override
    public ScreenDto createScreen(ScreenDto screenDto) {
        Screen screen = ScreenMapper.maptoMovie(screenDto);
        Screen savedScreen = screenRepository.save(screen);
        return ScreenMapper.mapToScreenDTO(savedScreen);
    }
}
