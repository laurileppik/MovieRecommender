package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @Column(name="start_time")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name="screen_id", nullable=false)
    private Screen screen;

    @Column(name="duration")
    private Integer duration;
}
