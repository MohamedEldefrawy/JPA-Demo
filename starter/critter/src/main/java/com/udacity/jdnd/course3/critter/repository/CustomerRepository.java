package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.user.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
