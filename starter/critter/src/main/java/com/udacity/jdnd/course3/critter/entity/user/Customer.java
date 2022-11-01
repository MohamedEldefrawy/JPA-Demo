package com.udacity.jdnd.course3.critter.entity.user;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Pet> pets = new java.util.ArrayList<>();


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

        return customerDTO;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
