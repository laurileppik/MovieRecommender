package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating,Long> {
    List<MovieRating> findAllByCustomer_Id(Long customerId);
    @Modifying
    @Query(value = " INSERT INTO movie_rating (rating, customer_id, movie_id) VALUES (1, :customerId, :movieId)", nativeQuery = true)
    void saveRating(@Param("movieId") Long movieId, @Param("customerId") Long customerId);
}
