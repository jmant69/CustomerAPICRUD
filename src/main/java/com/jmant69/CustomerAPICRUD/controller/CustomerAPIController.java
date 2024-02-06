package com.jmant69.CustomerAPICRUD.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmant69.CustomerAPICRUD.DTO.CustomerDTO;
import com.jmant69.CustomerAPICRUD.entity.Customer;
import com.jmant69.CustomerAPICRUD.exception.CustomerNotFoundException;
import com.jmant69.CustomerAPICRUD.service.CustomerService;

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

		return ResponseEntity.created(uri).body(customerDto);
	}

	@GetMapping("/{customerRef}")
	public ResponseEntity<?> get(@PathVariable("customerRef") Long customerRef) {
		try {
			Customer customer = service.get(customerRef);
			return ResponseEntity.ok(entity2Dto(customer));
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
//			return ResponseEntity.ok("Customer Ref " + customerRef + " not found");
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Customer customer) {
		try {
//			service.get(customer.getCustomerRef());
			Customer updatedCustomer = service.update(customer);

			CustomerDTO customerDto = entity2Dto(updatedCustomer);

			URI uri = URI.create("/customer/" + customerDto.getCustomerRef());

			return ResponseEntity.ok(customerDto);
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.notFound().build();
//			return ResponseEntity.ok("Customer Ref " + customer.getCustomerRef() + " not found");
		}
	}

	@DeleteMapping("/{customerRef}")
	public ResponseEntity<?> delete(@PathVariable("customerRef") Long customerRef) {
		try {
			service.delete(customerRef);
			return ResponseEntity.noContent().build();
//			return ResponseEntity.ok("Customer Ref " + customerRef + " deleted");
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.notFound().build();
//			return ResponseEntity.ok("Customer Ref " + customerRef + " not found");
		}
	}

	private CustomerDTO entity2Dto(Customer entity) {
		return modelMapper.map(entity, CustomerDTO.class);
	}
}
