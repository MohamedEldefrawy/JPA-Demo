package com.udacity.jdnd.course3.critter.entity.schedule;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "schedules")
    private Set<Employee> employees;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Customer_Schedule",
            joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id", referencedColumnName = "id")}
    )
    private Set<Customer> customers = new java.util.LinkedHashSet<>();


    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Pet> pets = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Skill> skills = new java.util.LinkedHashSet<>();
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(employees, schedule.employees) && Objects.equals(customers, schedule.customers) && Objects.equals(pets, schedule.pets) && Objects.equals(skills, schedule.skills) && Objects.equals(date, schedule.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employees, customers, pets, skills, date);
    }
}
