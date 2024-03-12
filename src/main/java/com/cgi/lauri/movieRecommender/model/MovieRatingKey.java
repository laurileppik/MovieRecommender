package com.cgi.lauri.movieRecommender.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MovieRatingKey implements Serializable {

    @Column(name = "customer_id")
    Long customerId;

    @Column(name = "movie_id")
    Long movieId;
}
