/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Schedule;
import com.anhquoc0304.repository.ScheduleRepository;
import com.anhquoc0304.service.ScheduleService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepo;
    @Override
    public boolean addSchedule(Schedule schedule) {
        return this.scheduleRepo.addSchedule(schedule);
    }

    @Override
    public List<Object[]> getScheduleByDate(Date d){
        return this.scheduleRepo.getScheduleByDate(d);
    }

    @Override
    public int countScheduleByDate(Date d) {
        return this.scheduleRepo.countScheduleByDate(d);
    }
    
}
