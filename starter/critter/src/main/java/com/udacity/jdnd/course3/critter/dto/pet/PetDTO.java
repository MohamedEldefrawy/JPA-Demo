package com.udacity.jdnd.course3.critter.dto.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.pet.PetType;
import com.udacity.jdnd.course3.critter.entity.user.Customer;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    private Customer customer;
    private LocalDate birthDate;
    private String notes;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pet toPet() {
        Pet pet = new Pet();
        pet.setId(this.getId());
        pet.setName(this.getName());
        pet.setCustomer(this.getCustomer());
        pet.setType(this.getType());
        pet.setBirthDate(this.getBirthDate());
        return pet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDTO petDTO = (PetDTO) o;
        return id == petDTO.id && type == petDTO.type && Objects.equals(name, petDTO.name) && Objects.equals(customer, petDTO.customer) && Objects.equals(birthDate, petDTO.birthDate) && Objects.equals(notes, petDTO.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, customer, birthDate, notes);
    }
}
