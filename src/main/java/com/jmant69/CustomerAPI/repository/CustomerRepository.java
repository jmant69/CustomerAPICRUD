package com.jmant69.CustomerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmant69.CustomerAPI.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
