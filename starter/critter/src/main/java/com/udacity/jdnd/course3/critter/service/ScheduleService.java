package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import com.udacity.jdnd.course3.critter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, SkillRepository skillRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        Set<Employee> selectedEmployees = new HashSet<>();
        Set<Customer> selectedCustomers = new HashSet<>();

        scheduleDTO.getEmployees().forEach(employee -> selectedEmployees.add(this.employeeRepository.findById(employee.getId()).get()));
        scheduleDTO.getCustomers().forEach(pet -> selectedCustomers.add(this.customerRepository.findById(pet.getId()).get()));
        scheduleDTO.setCustomers(selectedCustomers);
        scheduleDTO.setEmployees(selectedEmployees);
        Schedule newSchedule = scheduleDTO.toSchedule();
        return this.scheduleRepository.save(newSchedule);
    }

    public List<ScheduleDTO> getSchedules() {
        Iterable<Schedule> schedulesList = this.scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedulesList
        ) {
            scheduleDTOS.add(schedule.toScheduleDto());
        }

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findSchedulesByPetId(Long petId) {
        Optional<Pet> selectedPetOptional = this.petRepository.findById(petId);
        if (!selectedPetOptional.isPresent())
            return new ArrayList<>();
        Customer customer = this.customerRepository.findCustomerByPetsContaining(selectedPetOptional.get());
        List<Schedule> schedules = this.scheduleRepository.findScheduleByCustomersContaining(customer);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        schedules.forEach(schedule -> {
            scheduleDTOS.add(schedule.toScheduleDto());
        });
        return scheduleDTOS;
    }

    public List<ScheduleDTO> findSchedulesByEmployeeId(Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent())
            return new ArrayList<>();
        List<Schedule> scheduleList = this.scheduleRepository.findSchedulesByEmployeesContaining(optionalEmployee.get());
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOS.add(schedule.toScheduleDto());
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findSchedulesByCustomerId(Long customerId) {
        Optional<Customer> customerOptional = this.customerRepository.findById(customerId);
        if (!customerOptional.isPresent())
            return new ArrayList<>();
        List<Schedule> schedules = this.scheduleRepository.findScheduleByCustomersContaining(customerOptional.get());
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        schedules.forEach(schedule -> {
            scheduleDTOS.add(schedule.toScheduleDto());
        });

        return scheduleDTOS;
    }
}
