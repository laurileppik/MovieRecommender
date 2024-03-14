package com.cgi.lauri.movieRecommender.dto;

import com.cgi.lauri.movieRecommender.model.MovieRating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<MovieRating> ratings;
    private String userName;
    private String password;
    private String role;
}