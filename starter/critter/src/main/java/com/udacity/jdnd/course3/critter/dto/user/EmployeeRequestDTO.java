package com.udacity.jdnd.course3.critter.dto.user;

import com.udacity.jdnd.course3.critter.entity.skill.Skill;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
public class EmployeeRequestDTO {
    private List<Skill> skills;
    private LocalDate date;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
