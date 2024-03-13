package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    //maybe implement if time
    //https://www.baeldung.com/spring-security-login
    //https://www.baeldung.com/spring-security-login-react

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<MovieRating> ratings;

    @Column(name="user_name", nullable=false)
    private String userName;

    @Column(name="password", nullable=false)
    private String password;

    private String role;
}
