/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.AppointmentRepository;
import com.anhquoc0304.service.AppointmentService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointRepo;

    @Override
    public boolean addAppointment(Appointment a) {
        a.setCreatedDate(new Date());
        a.setAppointmentStatus(Appointment.WAITTING);
        return this.appointRepo.addAppointment(a);
    }

    @Override
    public boolean countAppointment(Date d) {
        return this.appointRepo.countAppointment(d);
    }

    @Override
    public List<Appointment> getAppointmentByStatus(String status) {
        return this.appointRepo.getAppointmentByStatus(status);
    }

    @Override
    public int countAppointmentByStatus(String status) {
       return this.appointRepo.countAppointmentByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentByCurrentUser(User user) {
        return this.appointRepo.getAppointmentByCurrentUser(user);
    }

    @Override
    public boolean setAppointmentStatus(Appointment a, String status) {
        return this.appointRepo.setAppointmentStatus(a, status);
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return this.appointRepo.getAppointmentById(id);
    }

    @Override
    public List<Appointment> getAppointmentToday() {
        return this.appointRepo.getAppointmentToday();
    }

    @Override
    public Appointment getAppointmentByPatientId(User patient) {
         return this.appointRepo.getAppointmentByPatientId(patient);
    }
    
}
