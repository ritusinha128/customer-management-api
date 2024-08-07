package com.example.customer_management_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer_management_api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
