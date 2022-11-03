package com.udacity.jdnd.course3.critter.entity.schedule;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Employee_Schedule",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private List<Employee> employees;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "schedule", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Pet> pets;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "schedule", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Skill> skills;
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate day) {
        this.date = day;
    }

    public ScheduleDTO toScheduleDto() {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setEmployees(this.getEmployees());
        scheduleDTO.setPets(this.getPets());
        scheduleDTO.setId(this.getId());
        scheduleDTO.setDate(this.getDate());
        scheduleDTO.setActivities(this.getSkills());
        return scheduleDTO;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
