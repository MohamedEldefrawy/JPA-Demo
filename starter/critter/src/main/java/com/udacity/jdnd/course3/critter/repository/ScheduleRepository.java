package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findSchedulesByEmployeesContaining(Employee employee);
    List<Schedule> findScheduleByCustomersContaining(Customer customers);
}
