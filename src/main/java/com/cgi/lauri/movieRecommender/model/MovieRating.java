package com.cgi.lauri.movieRecommender.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieRating {
    @EmbeddedId
    private MovieRatingKey id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private int rating;

    public MovieRating(Customer customer, Movie movie, int rating) {
        this.customer = customer;
        this.movie = movie;
        this.rating = rating;
    }
}
