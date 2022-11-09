package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import com.udacity.jdnd.course3.critter.repository.DayRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DayRepository dayRepository;
    private final SkillRepository skillRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DayRepository dayRepository, SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.dayRepository = dayRepository;
        this.skillRepository = skillRepository;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Set<Skill> skills = new HashSet<>();
        employeeDTO.getSkills().forEach(skill -> skills.add(this.skillRepository.findByName(skill.getName())));
        Employee newEmployee = employeeDTO.toEmployee();
        newEmployee.setSkills(skills);
        return this.employeeRepository.save(newEmployee).toEmployeeDto();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        return optionalEmployee.orElse(null);
    }

    public boolean setAvailabliity(Set<Day> days, Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        Set<Day> selectedDays = new HashSet<>();
        days.forEach(day -> selectedDays.add(this.dayRepository.findDayByDay(day.getDay())));

        if (optionalEmployee.isPresent()) {
            Employee selectedEmployee = optionalEmployee.get();
            selectedEmployee.setDays(selectedDays);
            this.employeeRepository.save(selectedEmployee);
            return true;
        }
        return false;
    }

    public List<EmployeeDTO> findEmployeesByAvailabilityAndSkills(EmployeeRequestDTO requestDTO) {
        Day selectedDay = this.dayRepository.findById((long) requestDTO.getDate().getDayOfWeek().getValue()).get();
        List<EmployeeDTO> employeesDtos = new ArrayList<>();
        List<Employee> allEmployee = new ArrayList<>();
        Set<Employee> selectedEmployees = new HashSet<>();
        Iterable<Employee> employeeIterable = this.employeeRepository.findAll();
        employeeIterable.forEach(allEmployee::add);
        List<Employee> selectedEmployeesByDay = allEmployee.stream().filter(employee -> employee.getDays().contains(selectedDay)).collect(Collectors.toList());
        List<Skill> skills = new ArrayList<>();
        requestDTO.getSkills().forEach(skill -> skills.add(this.skillRepository.findByName(skill.getName())));
        for (Skill skill : skills) {
            selectedEmployees.addAll(selectedEmployeesByDay.stream().filter(employee -> employee.getSkills().contains(skill)).collect(Collectors.toSet()));
        }
        selectedEmployees.forEach(employee -> employeesDtos.add(employee.toEmployeeDto()));
        return employeesDtos;
    }
}
