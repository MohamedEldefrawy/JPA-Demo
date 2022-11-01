package com.udacity.jdnd.course3.critter.entity.schedule;

import com.udacity.jdnd.course3.critter.entity.user.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue
    private Long id;

    public Day() {
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Employee_Day",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "day_id")}
    )


    private List<Employee> employees;

    private String day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Day(Long id, String day) {
        this.id = id;
        this.day = day;
    }
}
