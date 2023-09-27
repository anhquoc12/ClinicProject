/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.repository.DoctorRepository;
import com.anhquoc0304.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Override
    public boolean addOrUpdateDoctor(Doctor d) {
        return this.doctorRepo.addOrUpdateDoctor(d);
    }

    @Override
    public Doctor getDoctorById(int id) {
        return this.doctorRepo.getDoctorById(id);
    }
    
}
