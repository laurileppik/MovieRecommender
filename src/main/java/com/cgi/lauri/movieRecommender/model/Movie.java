package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    /**@ManyToMany
    @JoinTable(
            name = "movie_screen",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "screen_id"))
    private List<Screen> screens;**/
}
