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
	public ResponseEntity<?> getCustomer(@PathVariable long id) {
		Customer customer = customerService.getCustomer(id);
		if (customer == null) {
            CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.ok(customer);
        }
	}
	
	@PostMapping("")
	public Customer createCustomer(@RequestBody Customer cust) {
		return customerService.createCustomer(cust);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editCustomer(@PathVariable long id, @RequestBody Customer cust) {
		Customer customer = customerService.updateCustomer(id, cust);
		if (customer == null) {
            CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.ok(customer);
        }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomErrorMessage> deleteCustomer(@PathVariable long id) {
		boolean resp = customerService.deleteCustomer(id);
		if (!resp) {
			CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else {
			CustomErrorMessage response = new CustomErrorMessage("Customer deleted", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	@PutMapping("/purchase/{id}")
	public ResponseEntity<?> purchase(@PathVariable long id, @RequestParam(name = "purchase", defaultValue="0.0") double purchase) {
		Customer customer = customerService.purchase(id, purchase);
		if (customer == null) {
            CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.ok(customer);
        }
	}
	
	@PutMapping("/purchase/credit/{id}")
	public ResponseEntity<?> purchaseWithCredit(@PathVariable long id, @RequestParam(name = "purchase", defaultValue="0.0") double purchase) {
		Customer customer = customerService.purchaseWithCredit(id, purchase);
		if (customer == null) {
            CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.ok(customer);
        }
	}
	
	@PutMapping("/payment/{id}")
	public ResponseEntity<?> makePayment(@PathVariable long id, @RequestParam(name = "payment", defaultValue="0.0") double payment) {
		Customer customer = customerService.makePayment(id, payment);
		if (customer == null) {
            CustomErrorMessage errorResponse = new CustomErrorMessage("Customer not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.ok(customer);
        }
	}

}
