package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="genre")
    private String genre;

    @Column(name="language")
    private String language;

    @OneToMany(mappedBy="movie")
    @JsonIgnore
    private List<Showtime> showtimes;

    @Column(name="minimum_age")
    private Integer minimumAge;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private Set<MovieRating> ratings;

    @Column(name="imdb_rating")
    private Double imdbRating;
}
