package com.jmant69.CustomerAPICRUD.service;

import com.jmant69.CustomerAPICRUD.entity.Customer;
import com.jmant69.CustomerAPICRUD.exception.CustomerNotFoundException;

public interface CustomerService {

    public Customer add(Customer customer);
 
    public Customer get(Long id) throws CustomerNotFoundException;
    
    public Customer update(Customer customer);

	public Customer delete(Long id);
	
}
