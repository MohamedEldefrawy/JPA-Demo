package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return this.customerRepository.save(customerDTO.toCustomer()).toCustomerDto();
    }

    public List<CustomerDTO> getCustomers() {
        Iterable<Customer> customerIterable = this.customerRepository.findAll();
        List<CustomerDTO> customers = new ArrayList<>();

        customerIterable.forEach(customer -> {
            customer.setPets(this.petRepository.findPetsByCustomer(customer));
            customers.add(customer.toCustomerDto());
        });
        return customers;
    }

    public CustomerDTO getCustomersByPetId(Long petId) {
        return this.customerRepository.findCustomerByPetsContaining(this.petRepository.findById(petId).get())
                .toCustomerDto();
    }
}