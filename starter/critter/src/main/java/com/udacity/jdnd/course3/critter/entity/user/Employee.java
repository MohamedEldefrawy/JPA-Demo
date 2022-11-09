package com.udacity.jdnd.course3.critter.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends User {

    @ManyToMany
    @JoinTable(
            name = "Employee_Skill",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id")}
    )
    private Set<Skill> skills;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    private List<Schedule> schedules;

    @ManyToMany
    @JoinTable(
            name = "Employee_Day",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "day_id", referencedColumnName = "id")}
    )
    private Set<Day> days;

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }


    public EmployeeDTO toEmployeeDto() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(this.getName());
        employeeDTO.setId(this.getId());
        employeeDTO.setSkills(this.getSkills());
        employeeDTO.setSchedules(this.getSchedules());
        employeeDTO.setDays(this.getDays());

        return employeeDTO;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedule) {
        this.schedules = schedule;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getSkills(), employee.getSkills()) && Objects.equals(getSchedules(), employee.getSchedules()) && Objects.equals(getDays(), employee.getDays());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkills(), getSchedules(), getDays());
    }
}
