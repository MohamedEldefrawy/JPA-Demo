package com.udacity.jdnd.course3.critter.entity.skill;

import com.udacity.jdnd.course3.critter.entity.user.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Employee_Skill",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private List<Employee> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id) && name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
