package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.EmployeeDTO;
import com.cgi.lauri.movieRecommender.dto.LoginDTO;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.repository.EmployeeRepo;
import com.cgi.lauri.movieRecommender.security.LoginMesage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeIMPL implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String addEmployee(EmployeeDTO employeeDTO) {
        Customer customer = new Customer(
                employeeDTO.getId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getRatings(),
                employeeDTO.getUserName(),
                this.passwordEncoder.encode(employeeDTO.getPassword()),
                employeeDTO.getRole()
        );
        employeeRepo.save(customer);
        return customer.getFirstName();
    }
    EmployeeDTO employeeDTO;
    @Override
    public LoginMesage loginEmployee(LoginDTO loginDTO) {
        String msg = "";
        Customer customer1 = employeeRepo.findByUserName(loginDTO.getUsername());
        if (customer1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = customer1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Customer> customer = employeeRepo.findOneByUserNameAndPassword(loginDTO.getUsername(), encodedPassword);
                if (customer.isPresent()) {
                    return new LoginMesage("Login Success", true);
                } else {
                    return new LoginMesage("Login Failed", false);
                }
            } else {
                return new LoginMesage("password Not Match", false);
            }
        }else {
            return new LoginMesage("Email not exits", false);
        }
    }
}
