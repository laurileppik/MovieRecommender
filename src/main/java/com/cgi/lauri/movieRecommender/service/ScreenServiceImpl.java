package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.logic.ScreenLogic;
import com.cgi.lauri.movieRecommender.mapper.ScreenMapper;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.repository.ScreenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService{
    private ScreenRepository screenRepository;
    private ScreenLogic screenLogic;
    @Override
    public ScreenDto createScreen(ScreenDto screenDto) {
        Screen screen = ScreenMapper.maptoScreen(screenDto);
        Screen savedScreen = screenRepository.save(screen);
        return ScreenMapper.mapToScreenDTO(savedScreen);
    }

    @Override
    public ScreenDto getScreen(Long screenId) {
        Screen screen = screenRepository.findById(screenId).orElseThrow(
                () -> new ResourceNotFoundException("Screen not found with id: " + screenId)
        );
        screen.setOccupiedSeats(screenLogic.generateRandomSeats(screen.getNoOfSeats()));
        return ScreenMapper.mapToScreenDTO(screen);
    }

    @Override
    public ScreenDto getScreen(Long screenId, Integer noOfTickets) {
        Screen screen = screenRepository.findById(screenId).orElseThrow(
                () -> new ResourceNotFoundException("Screen not found with id: " + screenId)
        );
        List<Boolean> occupiedSeats=screenLogic.generateRandomSeats(screen.getNoOfSeats());
        screen.setOccupiedSeats(occupiedSeats);
        screen.setRecommendedSeats(screenLogic.generateRecommendSeats(occupiedSeats,noOfTickets,screen.getRows(),screen.getSeatsInRow()));
        return ScreenMapper.mapToScreenDTO(screen);

    }


}
