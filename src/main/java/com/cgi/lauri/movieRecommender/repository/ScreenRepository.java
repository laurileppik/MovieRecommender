package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen,Long> {
}
