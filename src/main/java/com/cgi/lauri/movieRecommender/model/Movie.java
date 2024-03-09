package com.cgi.lauri.movieRecommender.model;

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

    @Column(name="start_time")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ",shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;

    @Column(name="end_time")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ",shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;

    @Column(name="minimum_age")
    private Integer minimumAge;
}
