/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface AppointmentRepository {
    boolean addAppointment(Appointment a);
    boolean countAppointment(Date d);
    public List<Appointment> getAppointmentByStatus(String status);
    public List<Appointment> getAppointmentByCurrentUser(User user);
    public Appointment getAppointmentById(int id);
    public int countAppointmentByStatus(String status);
    public boolean setAppointmentStatus(Appointment a, String status);
    public List<Appointment> getAppointmentToday();
    public Appointment getAppointmentByPatientId(User patient);
}
