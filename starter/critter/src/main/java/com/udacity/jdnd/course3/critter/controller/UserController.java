package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.schedule.DayDto;
import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {

        CustomerDTO createdCustomer = this.customerService.createCustomer(customerDTO);
        if (createdCustomer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(this.customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<CustomerDTO> getOwnerByPet(@PathVariable long petId) {
        CustomerDTO selectedCustomer = this.customerService.getCustomersByPetId(petId);
        System.out.println(selectedCustomer.toCustomer().getPets());
        return new ResponseEntity<>(selectedCustomer, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = this.employeeService.createEmployee(employeeDTO);
        if (createdEmployee == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employee = this.employeeService.getEmployee(employeeId).toEmployeeDto();
        if (employee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Void> setAvailability(@RequestBody DayDto daysAvailable, @PathVariable long employeeId) {
        boolean result = this.employeeService.setAvailabliity(daysAvailable.getDays(), employeeId);
        if (!result)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Employee found with id: " + employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employee/availability")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employees = this.employeeService.findEmployeesByAvailabilityAndSkills(employeeDTO);
        if (employees.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
