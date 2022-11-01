package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
}
