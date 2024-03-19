package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findByGenre(String genre);
    @Query("SELECT m FROM Movie m WHERE m.minimumAge <= :age")
    List<Movie> findMoviesWithAgeFilter(@Param("age") int age);

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
