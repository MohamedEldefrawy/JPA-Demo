package com.udacity.jdnd.course3.critter.entity.pet;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne
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
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && type == pet.type && Objects.equals(name, pet.name) && Objects.equals(birthDate, pet.birthDate) && Objects.equals(notes, pet.notes) && Objects.equals(customer, pet.customer) && Objects.equals(schedule, pet.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, birthDate, notes, customer, schedule);
    }
}
