package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.ScreenMapper;
import com.cgi.lauri.movieRecommender.model.Screen;
import com.cgi.lauri.movieRecommender.repository.ScreenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService{
    private ScreenRepository screenRepository;
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
        screen.setOccupiedSeats(generateRandomSeats(screen.getNoOfSeats()));
        return ScreenMapper.mapToScreenDTO(screen);
    }

    private List<Boolean> generateRandomSeats(int noOfSeats) {
        List<Boolean> seats=new ArrayList<>();
        for (int i = 0; i < noOfSeats; i++) {
            Random rn = new Random();
            int tester = rn.nextInt(10) + 1;
            if (tester%2==0)
                seats.add(true);
            else seats.add(false);
        }
        return seats;
    }
}
