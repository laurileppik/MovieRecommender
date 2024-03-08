package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.exception.ResourceNotFoundException;
import com.cgi.lauri.movieRecommender.mapper.CustomerMapper;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    //@Autowired
    private CustomerRepository customerRepository;


    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer= CustomerMapper.maptoCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.mapToCustomerDTO(savedCustomer
        );
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with given id: " + customerId));
        return CustomerMapper.mapToCustomerDTO(customer);
    }
}
