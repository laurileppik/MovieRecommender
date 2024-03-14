package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.security.CustomerDetailService;
import com.cgi.lauri.movieRecommender.security.JwtTokenUtil;
import com.cgi.lauri.movieRecommender.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService customerService;

    //Add Customer REST API
    @PostMapping("/register")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    //Get Customer REST API
    @GetMapping("{id}")
    //@PathVariable necessary because we want to pass id as customerId
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long customerId) {
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customerDto);
    }

    //Get all customers REST API
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    //Update customer REST API
    //@PathVariable necessary because we want to pass id as customerId
    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id")Long customerId,
                                                      @RequestBody CustomerDto updatedCustomer) {
        CustomerDto customerDto = customerService.updateCustomer(customerId,updatedCustomer);
        return ResponseEntity.ok(customerDto);
    }

    //Delete customer REST API
    //@PathVariable necessary because we want to pass id as customerId
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id")Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfuly.");
    }
}
