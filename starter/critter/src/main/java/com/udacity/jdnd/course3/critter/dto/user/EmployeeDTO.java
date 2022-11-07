package com.udacity.jdnd.course3.critter.dto.user;

import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;

import java.util.List;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private long id;
    private String name;
    private List<Skill> skills;
    private Set<Schedule> schedules;

    private Set<Day> days;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setDays(this.getDays());
        employee.setSchedules(this.getSchedules());
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setSkills(this.getSkills());
        return employee;
    }
}
