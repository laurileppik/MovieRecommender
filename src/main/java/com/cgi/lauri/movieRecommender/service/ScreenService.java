package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.dto.ScreenDto;
import com.cgi.lauri.movieRecommender.model.Screen;

public interface ScreenService {
    ScreenDto createScreen(ScreenDto screenDto);
    ScreenDto getScreen(Long screenId);
    ScreenDto getScreen(Long screenId,Integer noOfTickets);
}
