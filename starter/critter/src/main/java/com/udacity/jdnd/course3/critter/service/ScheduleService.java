package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        return this.scheduleRepository.save(scheduleDTO.toSchedule());
    }

    public List<ScheduleDTO> getSchedules() {
        Iterable<Schedule> schedulesList = this.scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedulesList
        ) {
            scheduleDTOS.add(schedule.toScheduleDto());
        }

        return scheduleDTOS;
    }
}
