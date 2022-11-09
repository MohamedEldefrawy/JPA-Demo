package com.udacity.jdnd.course3.critter.dto.schedule;

import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.entity.user.Employee;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private Set<Employee> employees;
    private Set<Customer> customers;
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Schedule toSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(this.getDate());
        schedule.setEmployees(this.getEmployees());
        schedule.setCustomers(this.getCustomers());
        schedule.setId(this.getId());
        return schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return id == that.id && Objects.equals(employees, that.employees) && Objects.equals(customers, that.customers) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employees, customers, date);
    }
}
