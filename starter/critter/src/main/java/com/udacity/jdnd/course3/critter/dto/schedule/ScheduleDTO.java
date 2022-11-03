package com.udacity.jdnd.course3.critter.dto.schedule;

import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private Set<Employee> employees;
    private Set<Pet> pets;
    private LocalDate date;
    private Set<Skill> activities;

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

    public Set<Skill> getActivities() {
        return activities;
    }

    public void setActivities(Set<Skill> activities) {
        this.activities = activities;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public void setEmployees(Set<Employee> employees) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return id == that.id && Objects.equals(employees, that.employees) && Objects.equals(pets, that.pets) && Objects.equals(date, that.date) && Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employees, pets, date, activities);
    }
}
