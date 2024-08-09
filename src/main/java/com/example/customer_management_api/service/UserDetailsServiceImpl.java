package com.example.customer_management_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .roles("USER") 
                .build();
    }
}
