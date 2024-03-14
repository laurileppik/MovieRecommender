package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.model.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<Customer> getAllUser();

    Customer findUserProfileByJwt(String jwt);

    Customer findUserByEmail(String email);

    Customer findUserById(String userId);

    List<Customer> findAllUsers();
}
