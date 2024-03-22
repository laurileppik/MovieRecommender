package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.MovieRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<MovieRating> ratings;
    private String userName;
    private Date birthDate;
    private String role;
}
