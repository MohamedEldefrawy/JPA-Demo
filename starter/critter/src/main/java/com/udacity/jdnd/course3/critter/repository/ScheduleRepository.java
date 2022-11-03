package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findSchedulesByPetsContaining(Pet pet);

    List<Schedule> findSchedulesByEmployeesContaining(Employee employee);

    List<Schedule> findSchedulesByCustomersContaining(Customer customer);

}
