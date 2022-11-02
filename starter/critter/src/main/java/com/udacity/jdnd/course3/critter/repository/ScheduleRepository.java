package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Long, Schedule> {
}
