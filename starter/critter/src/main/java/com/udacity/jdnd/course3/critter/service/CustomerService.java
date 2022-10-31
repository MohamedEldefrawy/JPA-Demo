package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDTO.toCustomer();
        return this.customerRepository.save(customer);
    }

    public List<CustomerDTO> getCustomers() {
        Iterable<Customer> customerIterable = this.customerRepository.findAll();
        List<CustomerDTO> customers = new ArrayList<>();
        customerIterable.forEach(customer -> {
            customers.add(customer.toCustomerDto());
        });
        return customers;
    }
}