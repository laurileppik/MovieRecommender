package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating,Long> {
    List<MovieRating> findAllByCustomer_Id(Long customerId);
}
