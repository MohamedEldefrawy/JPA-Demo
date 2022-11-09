package com.udacity.jdnd.course3.critter.dto.user;

import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;

import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Pet> pets;

    private List<Schedule> schedule;

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

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setNotes(this.getNotes());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setSchedule(this.getSchedule());
        customer.setPets(this.getPets());

        return customer;
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
}
