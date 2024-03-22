package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByName(String name);
    @Query("""
             SELECT DISTINCT m.genre
             FROM Movie m
             ORDER BY m.genre ASC""")
    List<String> findAllGenres();

    @Query("""
            SELECT DISTINCT m.minimumAge
            FROM Movie m
            ORDER BY m.minimumAge ASC""")
    List<String> findAllAges();

    @Query("""
            SELECT DISTINCT m.language
            FROM Movie m
            ORDER BY m.language ASC""")
    List<String> findAllLanguages();
}
