package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.model.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto getCustomerById(Long customerId);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer);
    void deleteCustomer(Long customerId);
}
