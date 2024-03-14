package com.cgi.lauri.movieRecommender;

import com.cgi.lauri.movieRecommender.model.Screen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//exclude = SecurityAutoConfiguration.class
@SpringBootApplication()
public class MovieRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRecommenderApplication.class, args);
	}

}
