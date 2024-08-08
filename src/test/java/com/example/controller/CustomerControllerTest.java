package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.example.customer_management_api.controller.CustomerController;
import com.example.customer_management_api.entity.CustomErrorMessage;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.service.CustomerService;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers=CustomerController.class)
public class CustomerControllerTest {
	
	@Mock 
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	List<Customer> customers;
	
	@BeforeEach
	public void setup() {
		customers = new ArrayList<>();
		Customer c1 = new Customer(1, "Ritu");
		Customer c2 = new Customer(2, "Pooja");
		customers.add(c1);
		customers.add(c2);
	}
	
	@Test
	public void testGetAllCustomers() {
		when(customerService.getCustomers()).thenReturn(customers);
		ResponseEntity<List<Customer>> response = customerController.getAllCustomers();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), customers.size());
		assertEquals(response.getBody().get(0), customers.get(0));
		assertEquals(response.getBody().get(1), customers.get(1));
		verify(customerService).getCustomers();
	}
	
	@Test
	public void testGetCustomerById() {
		when(customerService.getCustomer((long)1)).thenReturn(customers.get(0));
		ResponseEntity<?> response = customerController.getCustomer((long)1);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Customer cust = (Customer) response.getBody();
		assertEquals(cust, customers.get(0));
		verify(customerService).getCustomer((long)1);
	}
	
	@Test
	public void testCreateCustomer() {
		when(customerService.createCustomer(customers.get(0))).thenReturn(customers.get(0));
		Customer response = customerController.createCustomer(customers.get(0));
		assertEquals(response, customers.get(0));
		verify(customerService).createCustomer(customers.get(0));
	}
	
	@Test
	public void testUpdateCustomer() {
		Customer c = customers.get(0);
		c.setName("Ritu Sinha");
		when(customerService.updateCustomer((long)1, c)).thenReturn(c);
		ResponseEntity<?> resp = customerController.editCustomer((long)1, c);
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
		assertEquals((Customer)resp.getBody(), c);
		verify(customerService).updateCustomer((long)1, c);
	}
	
	@Test
	public void testDeleteCustomer() {
		when(customerService.deleteCustomer((long)1)).thenReturn(true);
		ResponseEntity<CustomErrorMessage>res = customerController.deleteCustomer((long)1);
		assertEquals(res.getBody().getMessage(), "Customer deleted");
		verify(customerService).deleteCustomer((long)1);
	}
	
	
}
