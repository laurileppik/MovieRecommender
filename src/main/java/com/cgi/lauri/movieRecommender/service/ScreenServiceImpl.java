package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
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

    @Override
    public ScreenDto getScreen(Long screenId) {
        Screen screen = screenRepository.findById(screenId).orElseThrow(
                () -> new ResourceNotFoundException("Screen not found with id: " + screenId)
        );
        return ScreenMapper.mapToScreenDTO(screen);
    }
}
