package com.udacity.jdnd.course3.critter.entity.skill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "skills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "employees"})
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "skills")
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id) && name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
