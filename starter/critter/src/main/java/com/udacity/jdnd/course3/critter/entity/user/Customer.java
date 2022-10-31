package com.udacity.jdnd.course3.critter.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private String phoneNumber;
    private String notes;
}
