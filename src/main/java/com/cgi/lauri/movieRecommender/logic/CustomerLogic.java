package com.cgi.lauri.movieRecommender.logic;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomerLogic {
    public String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
