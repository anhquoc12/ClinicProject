/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Schedule;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ScheduleRepository {
    boolean addSchedule(Schedule schedule);
    List<Object[]> getScheduleByDate(Date d);
    int countScheduleByDate(Date d);
}
