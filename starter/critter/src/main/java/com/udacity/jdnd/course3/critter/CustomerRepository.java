package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Long, Customer> {
}
