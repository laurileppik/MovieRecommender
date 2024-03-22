package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
    List<Showtime> findAllByMovieId(Long movieId);
}
