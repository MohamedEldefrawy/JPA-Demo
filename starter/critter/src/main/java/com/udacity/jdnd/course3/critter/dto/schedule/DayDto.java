package com.udacity.jdnd.course3.critter.dto.schedule;

import com.udacity.jdnd.course3.critter.entity.schedule.Day;

import java.util.Set;

public class DayDto {
    private Set<Day> days;

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }
}
