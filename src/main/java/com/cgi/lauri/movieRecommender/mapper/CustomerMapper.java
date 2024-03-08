package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.model.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDTO(Customer customer) {
        return new CustomerDto(
                customer.getId(), customer.getFirstName(), customer.getLastName()
        );
    }

    public static Customer maptoCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getFirstName(), customerDto.getLastName());
    }
}
