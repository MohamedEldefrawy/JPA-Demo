package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import com.udacity.jdnd.course3.critter.repository.DayRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DayRepository dayRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DayRepository dayRepository) {
        this.employeeRepository = employeeRepository;
        this.dayRepository = dayRepository;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        return this.employeeRepository.save(employeeDTO.toEmployee()).toEmployeeDto();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        return optionalEmployee.orElse(null);
    }

    public void setAvailabliity(List<Day> days, Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        optionalEmployee.ifPresent(employee -> employee.setDays(days));
    }

    public List<EmployeeDTO> findEmployeesByAvailabilityAndSkills(EmployeeRequestDTO requestDTO) {
        Day selectedDay = this.dayRepository.findById((long) requestDTO.getDate().getDayOfWeek().getValue()).get();
        List<EmployeeDTO> employeesDtos = new ArrayList<>();
        List<Employee> allEmployee = new ArrayList<>();
        Set<Employee> selectedEmployees = new HashSet<>();
        Iterable<Employee> employeeIterable = this.employeeRepository.findAll();
        employeeIterable.forEach(allEmployee::add);
        List<Employee> selectedEmployeesByDay = allEmployee.stream().filter(employee -> employee.getDays().contains(selectedDay)).collect(Collectors.toList());
        for (Skill skill : requestDTO.getSkills()
        ) {
            selectedEmployees.addAll(selectedEmployeesByDay.stream().filter(employee -> employee.getSkills().contains(skill)).collect(Collectors.toSet()));
        }
        selectedEmployees.forEach(employee -> employeesDtos.add(employee.toEmployeeDto()));
        return employeesDtos;
    }
}
