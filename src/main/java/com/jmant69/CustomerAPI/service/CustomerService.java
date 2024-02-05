package com.jmant69.CustomerAPI.service;

import com.jmant69.CustomerAPI.entity.Customer;
import com.jmant69.CustomerAPI.exception.CustomerNotFoundException;

public interface CustomerService {

    public Customer add(Customer customer);
 
    public Customer get(Long id) throws CustomerNotFoundException;
	
}
