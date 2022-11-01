package com.udacity.jdnd.course3.critter.entity.user;

import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Employee extends User {

    @ManyToMany(mappedBy = "employees")
    @Fetch(FetchMode.JOIN)
    private Set<Skill> skills;

    @ManyToMany(mappedBy = "employees")
    @Fetch(FetchMode.JOIN)
    private Set<Schedule> schedule;


    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }
}
