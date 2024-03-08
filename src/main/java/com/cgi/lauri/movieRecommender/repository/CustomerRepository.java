package com.cgi.lauri.movieRecommender.repository;

import com.cgi.lauri.movieRecommender.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
