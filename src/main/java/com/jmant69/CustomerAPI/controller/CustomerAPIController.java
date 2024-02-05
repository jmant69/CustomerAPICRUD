package com.jmant69.CustomerAPI.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmant69.CustomerAPI.DTO.CustomerDTO;
import com.jmant69.CustomerAPI.entity.Customer;
import com.jmant69.CustomerAPI.exception.CustomerNotFoundException;
import com.jmant69.CustomerAPI.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerAPIController {

	@Autowired
	private CustomerService service;
	
    @Autowired
    private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Customer customer) {
		Customer persistedCustomer = service.add(customer);

	    CustomerDTO customerDto = entity2Dto(persistedCustomer);

	    URI uri = URI.create("/customer/" + customerDto.getCustomerRef());

		return ResponseEntity.created(uri).body(persistedCustomer);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		try {
			Customer customer = service.get(id);
	        return ResponseEntity.ok(entity2Dto(customer));
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	

    private CustomerDTO entity2Dto(Customer entity) {
        return modelMapper.map(entity, CustomerDTO.class);
    }
}
