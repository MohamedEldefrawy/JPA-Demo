package com.udacity.jdnd.course3.critter.entity.user;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private String phoneNumber;
    private String notes;

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
        customerDTO.setPets(customerDTO.getPets());

        return customerDTO;
    }
}
