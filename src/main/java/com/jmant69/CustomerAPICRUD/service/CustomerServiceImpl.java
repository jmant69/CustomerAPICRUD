package com.jmant69.CustomerAPICRUD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmant69.CustomerAPICRUD.entity.Customer;
import com.jmant69.CustomerAPICRUD.exception.CustomerNotFoundException;
import com.jmant69.CustomerAPICRUD.repository.CustomerRepository;

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
    
    public Customer update(Customer customer) throws CustomerNotFoundException {
    	if (!repo.existsById(customer.getCustomerRef())) {
    		throw new CustomerNotFoundException();
    	}
        return repo.save(customer);
    }

	@Override
	public void delete(Long customerRef) throws CustomerNotFoundException {
    	if (!repo.existsById(customerRef)) {
    		throw new CustomerNotFoundException();
    	}
        repo.deleteById(customerRef);
	}
 
}
