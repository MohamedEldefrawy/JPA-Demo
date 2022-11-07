package com.udacity.jdnd.course3.critter.entity.schedule;

import com.udacity.jdnd.course3.critter.entity.user.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue
    private Long id;

    private String day;

    @ManyToMany
    @JoinTable(
            name = "Employee_Schedule",
            joinColumns = {@JoinColumn(name = "schedule_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")}
    )
    private List<Employee> employees;

    public Day() {
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;
        Day day1 = (Day) o;
        return Objects.equals(getDay(), day1.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDay());
    }
}
