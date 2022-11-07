package com.udacity.jdnd.course3.critter.entity.user;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedule;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public CustomerDTO toCustomerDto() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(this.getId());
        customerDTO.setName(this.getName());
        customerDTO.setNotes(this.getNotes());
        customerDTO.setPhoneNumber(this.getPhoneNumber());
        customerDTO.setPets(this.getPets());
        customerDTO.setSchedule(this.getSchedule());

        return customerDTO;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getPhoneNumber(), customer.getPhoneNumber()) && Objects.equals(getNotes(), customer.getNotes()) && Objects.equals(getPets(), customer.getPets()) && Objects.equals(getSchedule(), customer.getSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumber(), getNotes(), getPets(), getSchedule());
    }
}
