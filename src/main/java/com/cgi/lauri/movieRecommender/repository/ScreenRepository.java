package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {
}
