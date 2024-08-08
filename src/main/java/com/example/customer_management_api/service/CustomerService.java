package com.example.customer_management_api.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer_management_api.entity.*;
import com.example.customer_management_api.repository.CustomerRepository;

@Service
public class CustomerService {
	
	//private List<Customer> customers;
	// private static long id;
	
	@Autowired
	CustomerRepository customerRepository;
	
	public CustomerService() {
	}

	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}
	
	public Customer getCustomer(long id){
		return customerRepository.findById(id).orElse(null);
		/*
		for (Customer customer : customers) {
			if (customer.getId() == id) {
				return Optional.ofNullable(customer);
			}
		}
		return Optional.empty();
		*/
	}
	
	public Customer createCustomer(Customer customer) {
		//customer.setId(id++);
		//Customer newCustomer = new Customer(id++, name);
		//customers.add(customer);
		customerRepository.save(customer);
		return customer;
	}
	
	public Customer updateCustomer(long id, Customer cust) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			return null;
		} else {
			Customer c = customer.get();
			c.setName(cust.getName());
			customerRepository.save(c);
			return c;
		}
			
		/*for (Customer customer : customers) {
			if (customer.getId() == id) {
				customer.setName(cust.getName());
				return customer;
			}
		}
		return null;*/
	}
	
	public boolean deleteCustomer(long id) {
		Optional<Customer> cust = customerRepository.findById(id);
		if (cust.isEmpty()) {
			return false;
		}
		customerRepository.deleteById(id);
		return true;
		/*
		int index = -1;
		for (int i = 0; i < customers.size(); i++) {
			if (id ==customers.get(i).getId()) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			return false;
		}
		customers.remove(index);
		return true;
		*/
	}
	
	public Customer purchase (long id, double purchaseAmount) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			return null;
		}
		Customer cust = customer.get();
		cust.setTotalSales(cust.getTotalSales() + purchaseAmount);
		customerRepository.save(cust);
		return cust;
	}
	
	public Customer purchaseWithCredit (long id, double purchaseAmount) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			return null;
		}
		Customer cust = customer.get();
		cust.setTotalSales(cust.getTotalSales() + purchaseAmount);
		cust.setBalanceDue(cust.getBalanceDue() + purchaseAmount);
		customerRepository.save(cust);
		return cust;
	}
	
	public Customer makePayment (long id, double payment) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			return null;
		}
		Customer cust = customer.get();
		cust.setBalanceDue(cust.getBalanceDue() - payment);
		customerRepository.save(cust);
		return cust;
	}

}
