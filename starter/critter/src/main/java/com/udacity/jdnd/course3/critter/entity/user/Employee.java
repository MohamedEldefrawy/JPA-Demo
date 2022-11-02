package com.udacity.jdnd.course3.critter.entity.user;

import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Employee extends User {

    @ManyToMany(mappedBy = "employees")
    private List<Skill> skills;

    @ManyToMany(mappedBy = "employees")
    private List<Day> days;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }


    public EmployeeDTO toEmployeeDto() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(this.getName());
        employeeDTO.setId(this.getId());
        employeeDTO.setSkills(this.getSkills());
        employeeDTO.setDays(this.getDays());

        return employeeDTO;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
