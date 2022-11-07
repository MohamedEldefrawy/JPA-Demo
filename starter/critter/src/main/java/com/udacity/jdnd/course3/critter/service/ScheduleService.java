package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        return this.scheduleRepository.save(scheduleDTO.toSchedule());
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
        Pet selectedPet = this.petRepository.findById(petId).get();
        Iterable<Schedule> schedulesList = this.scheduleRepository.findAll();
        List<Schedule> scheduleList = new ArrayList<>();
        schedulesList.forEach(schedule -> {
            if (schedule.getPets().contains(selectedPet))
                scheduleList.add(schedule);
        });
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOS.add(schedule.toScheduleDto());
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findSchedulesByEmployeeId(Long employeeId) {
        Employee selectedEmployee = this.employeeRepository.findById(employeeId).get();
        List<Schedule> scheduleList = this.scheduleRepository.findSchedulesByEmployeesContaining(selectedEmployee);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOS.add(schedule.toScheduleDto());
        });

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findSchedulesByCustomerId(Long customerId) {
        throw new ExceptionInInitializerError();
    }
}
