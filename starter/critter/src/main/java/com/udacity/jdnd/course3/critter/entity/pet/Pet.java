package com.udacity.jdnd.course3.critter.entity.pet;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;

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

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    @Fetch(FetchMode.JOIN)
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
}
