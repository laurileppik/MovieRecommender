package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.dto.EmployeeDTO;
import com.cgi.lauri.movieRecommender.dto.LoginDTO;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.security.LoginMesage;

import java.util.List;

public interface EmployeeService {
    String addEmployee(EmployeeDTO employeeDTO);
    LoginMesage loginEmployee(LoginDTO loginDTO);
}
