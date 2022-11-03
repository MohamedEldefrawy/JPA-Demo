package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.schedule.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule createdSchedule = this.scheduleService.createSchedule(scheduleDTO);
        if (createdSchedule == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdSchedule.toScheduleDto(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = this.scheduleService.getSchedules();
        if (scheduleDTOS.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scheduleDTOS, HttpStatus.OK);

    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = this.scheduleService.findSchedulesByPetId(petId);
        if (scheduleDTOS.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scheduleDTOS, HttpStatus.OK);

    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = this.scheduleService.findSchedulesByEmployeeId(employeeId);
        if (scheduleDTOS.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scheduleDTOS, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = this.scheduleService.findSchedulesByCustomerId(customerId);
        if (scheduleDTOS.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scheduleDTOS, HttpStatus.OK);
    }
}
