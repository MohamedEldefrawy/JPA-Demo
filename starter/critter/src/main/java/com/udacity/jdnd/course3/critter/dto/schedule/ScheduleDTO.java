package com.udacity.jdnd.course3.critter.dto.schedule;

import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Employee> employees;
    private List<Pet> pets;
    private LocalDate date;
    private List<Skill> activities;

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

    public List<Skill> getActivities() {
        return activities;
    }

    public void setActivities(List<Skill> activities) {
        this.activities = activities;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Schedule toSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(this.getDate());
        schedule.setSkills(this.getActivities());
        schedule.setEmployees(this.getEmployees());
        schedule.setPets(this.getPets());
        schedule.setId(this.getId());
        return schedule;
    }
}
