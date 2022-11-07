package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.DayRepository;
import org.springframework.stereotype.Service;

@Service
public class DayService {
    private DayRepository dayRepository;

    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }
}
