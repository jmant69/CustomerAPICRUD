package com.jmant69.CustomerAPI.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmant69.CustomerAPICRUD.DTO.CustomerDTO;
import com.jmant69.CustomerAPICRUD.controller.CustomerAPIController;
import com.jmant69.CustomerAPICRUD.entity.Customer;
import com.jmant69.CustomerAPICRUD.exception.CustomerNotFoundException;
import com.jmant69.CustomerAPICRUD.service.CustomerService;

@WebMvcTest(CustomerAPIController.class)
public class CustomerAPIControllerTest {
	
    private static final String END_POINT_PATH = "/customer";
    
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private CustomerService service;
    @MockBean private ModelMapper modelMapper;
    ModelMapper modelMapperMock = new ModelMapper();
    
    @Test
    public void testAddShouldReturn201NullId() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerRef(1L);
        newCustomer.setCustomerName("Jimmy");
       
        String requestBody = objectMapper.writeValueAsString(newCustomer);
        
        Mockito.when(service.add(newCustomer)).thenReturn(newCustomer);

        Mockito.when(modelMapper.map(newCustomer, CustomerDTO.class)).thenReturn(modelMapperMock.map(newCustomer, CustomerDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
                .content(requestBody))
        		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/customer/1"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }
    
    @Test
    public void testGetShouldReturn200OK() throws Exception {
    	Long customerRef = 1L;

    	String requestUri = END_POINT_PATH + "/" + customerRef;
    	
    	Customer newCustomer = new Customer();
    	newCustomer.setCustomerRef(customerRef);
    	String expectedName = "John Smith";
		newCustomer.setCustomerName(expectedName);
    	newCustomer.setAddressLine1("22 Acacia Ave");
    	    	
        String requestBody = objectMapper.writeValueAsString(newCustomer);
        
        Mockito.when(service.get(customerRef)).thenReturn(newCustomer);
        Mockito.when(modelMapper.map(newCustomer, CustomerDTO.class)).thenReturn(modelMapperMock.map(newCustomer, CustomerDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.get(requestUri).contentType("application/json")
                .content(requestBody))
        		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(expectedName))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

   
    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Long customerRef = 500L;
        String requestURI = END_POINT_PATH + "/" + customerRef;
     
        Mockito.when(service.get(customerRef)).thenThrow(CustomerNotFoundException.class);
     
        mockMvc.perform(MockMvcRequestBuilders.get(requestURI))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
    }
 
}
