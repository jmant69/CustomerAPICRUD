package com.jmant69.CustomerAPICRUD.service;

import com.jmant69.CustomerAPICRUD.entity.Customer;
import com.jmant69.CustomerAPICRUD.exception.CustomerNotFoundException;

public interface CustomerService {

    public Customer add(Customer customer);
 
    public Customer get(Long customerRef) throws CustomerNotFoundException;
    
    public Customer update(Customer customer) throws CustomerNotFoundException;

	public void delete(Long customerRef) throws CustomerNotFoundException;
	
}
