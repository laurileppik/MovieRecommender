package com.cgi.lauri.movieRecommender.controller;

import com.cgi.lauri.movieRecommender.controller.response.AuthResponse;
import com.cgi.lauri.movieRecommender.dto.CustomerDto;
import com.cgi.lauri.movieRecommender.mapper.CustomerMapper;
import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.repository.CustomerRepository;
import com.cgi.lauri.movieRecommender.security.JwtProvider;
import com.cgi.lauri.movieRecommender.service.CustomerServiceImpl;
import com.cgi.lauri.movieRecommender.service.UserService;
import com.cgi.lauri.movieRecommender.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserServiceImplementation customUserDetails;
    @Autowired
    private CustomerServiceImpl customerService;

    //@Autowired
    //private UserService userService;




    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody Customer user)  {
        String email = user.getUserName();
        String password = user.getPassword();
        String fullName = user.getFirstName();
        String mobile = user.getLastName();
        String role = user.getRole();

        Optional<Customer> isEmailExist = userRepository.findByUserName(email);
        if (isEmailExist != null) {
            //throw new Exception("Email Is Already Used With Another Account");

        }
        Customer createdUser = new Customer();
        createdUser.setUserName(email);
        createdUser.setFirstName(fullName);
        createdUser.setLastName(mobile);
        createdUser.setRole(role);
        createdUser.setPassword(passwordEncoder.encode(password));

        Customer savedUser = userRepository.save(createdUser);
        userRepository.save(savedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

    }





    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody Customer loginRequest) {
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        String id = loginRequest.getUserName();

        CustomerDto customerDTo= customerService.getCustomerByUsername(username);

        System.out.println(username+"-------"+password);

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);
        authResponse.setId(customerDTo.getId());

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }




    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("Sig in in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }



}