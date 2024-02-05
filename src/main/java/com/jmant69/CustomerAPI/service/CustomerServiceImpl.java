package com.jmant69.CustomerAPI.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmant69.CustomerAPI.entity.Customer;
import com.jmant69.CustomerAPI.exception.CustomerNotFoundException;
import com.jmant69.CustomerAPI.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired 
    private CustomerRepository repo;
    
    public Customer add(Customer customer) {
        return repo.save(customer);
    }
 
    public Customer get(Long customerRef) throws CustomerNotFoundException {
        Optional<Customer> result = repo.findById(customerRef);
 
        if (result.isPresent()) {
            return result.get();
        }
 
        throw new CustomerNotFoundException();
    }
 
}
