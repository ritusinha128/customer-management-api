package com.example.customer_management_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer_management_api.service.CustomerService;
import com.example.customer_management_api.entity.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	public CustomerService customerService;
	
	@GetMapping("")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<List<Customer>>(customerService.getCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Customer>> getCustomer(@PathVariable long id) {
		return new ResponseEntity<Optional<Customer>>(customerService.getCustomer(id), HttpStatus.OK);
	}
	
	@PostMapping("")
	public Customer createCustomer(@RequestBody Customer cust) {
		return customerService.createCustomer(cust);
	}
	
	@PutMapping("/{id}")
	public Customer editCustomer(@PathVariable long id, @RequestBody Customer cust) {
		return customerService.updateCustomer(id, cust);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteCustomer(@PathVariable long id) {
		return customerService.deleteCustomer(id);
	}
	
	@PutMapping("/purchase/{id}")
	public Customer purchase(@PathVariable long id, @RequestParam(name = "purchase", defaultValue="0.0") double purchase) {
		return customerService.purchase(id, purchase);
	}
	
	@PutMapping("/purchase/credit/{id}")
	public Customer purchaseWithCredit(@PathVariable long id, @RequestParam(name = "purchase", defaultValue="0.0") double purchase) {
		return customerService.purchaseWithCredit(id, purchase);
	}
	
	@PutMapping("/payment/{id}")
	public Customer makePayment(@PathVariable long id, @RequestParam(name = "payment", defaultValue="0.0") double payment) {
		return customerService.makePayment(id, payment);
	}

}
