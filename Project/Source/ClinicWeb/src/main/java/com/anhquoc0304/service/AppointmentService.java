/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface AppointmentService {
    boolean addAppointment(Appointment a);
    boolean countAppointment(Date d);
    List<Appointment> getAppointmentByStatus(String status);
    int countAppointmentByStatus(String status);
    List<Appointment> getAppointmentByCurrentUser(User user);
    boolean setAppointmentStatus(Appointment a, String status);
    Appointment getAppointmentById(int id);
    List<Appointment> getAppointmentToday();
    Appointment getAppointmentByPatientId(User patient);
}
