package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;
import com.example.customer_management_api.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService;
	
	List <Customer> customers;
	
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
		when(customerRepository.findAll()).thenReturn(customers);
		List<Customer> custs = customerService.getCustomers();
		assertEquals(custs.size(), customers.size());
		assertEquals(custs.get(0), customers.get(0));
		assertEquals(custs.get(1), customers.get(1));
		verify(customerRepository).findAll();
	}
	
	@Test
	public void testGetCustomerById() {
		when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customers.get(0)));
		Optional<Customer> customer = customerService.getCustomer(1);
		assertNotNull(customer.get());
		assertEquals(customer.get(), customers.get(0));
		verify(customerRepository).findById((long)1);
	}
	
	@Test
	public void testCreateCustomer() {
		when(customerRepository.save(customers.get(0))).thenReturn(customers.get(0));
		Customer cust = customerService.createCustomer(customers.get(0));
		assertEquals(cust, customers.get(0));
		verify(customerRepository).save(customers.get(0));
	}
	
	@Test
	public void testUpdateCustomer() {
		when(customerRepository.findById((long)1)).thenReturn(Optional.of(customers.get(0)));
		Customer testC = customers.get(0);
		testC.setName("Ritu Singha");
		when(customerRepository.save(testC)).thenReturn(testC);
		Customer cust = customerService.updateCustomer(1, testC);
		assertEquals(cust, testC);
		verify(customerRepository).findById((long)1);
		verify(customerRepository).save(testC);
	}
	
	@Test
	public void testDeleteCustomer() {
		doNothing().when(customerRepository).deleteById((long)1);
		when(customerRepository.findById((long)1)).thenReturn(Optional.of(customers.get(0)));
		boolean res = customerService.deleteCustomer((long)1);
		assertEquals(res, true);
		verify(customerRepository).deleteById((long)1);
	}
	
}
