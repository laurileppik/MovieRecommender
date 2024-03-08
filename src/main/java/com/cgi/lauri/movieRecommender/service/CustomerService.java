package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.model.Customer;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto getCustomerById(Long customerId);
}
