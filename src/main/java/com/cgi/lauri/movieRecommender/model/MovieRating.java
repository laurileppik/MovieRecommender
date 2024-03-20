package com.cgi.lauri.movieRecommender.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieRating {
    @EmbeddedId
    private MovieRatingKey id = new MovieRatingKey();

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Integer rating;

    public MovieRating(Customer customer, Movie movie) {
        this.customer = customer;
        this.movie = movie;
    }

    public MovieRating(Customer customer, Movie movie, Integer rating) {
        this.customer = customer;
        this.movie = movie;
        this.rating = rating;
    }

    public MovieRating(MovieRatingKey id, Customer customer, Movie movie) {
        this.id = id;
        this.customer = customer;
        this.movie = movie;
    }
}
