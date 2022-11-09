package com.udacity.jdnd.course3.critter.entity.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "customer"})
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer user) {
        this.customer = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PetDTO toPetDto() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName(this.getName());
        petDTO.setType(this.getType());
        petDTO.setId(this.getId());
        petDTO.setNotes(this.getNotes());
        petDTO.setBirthDate(this.getBirthDate());
        petDTO.setCustomer(this.getCustomer());
        return petDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return getId().equals(pet.getId()) && getType() == pet.getType() && getName().equals(pet.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getName());
    }
}
