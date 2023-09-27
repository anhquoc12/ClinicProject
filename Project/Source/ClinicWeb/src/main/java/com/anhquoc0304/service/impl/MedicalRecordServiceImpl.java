/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.repository.MedicalRecordRepository;
import com.anhquoc0304.service.MedicalRecordService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{
    @Autowired
    private MedicalRecordRepository medicalRepository;

    @Override
    public boolean addMedicalRecord(MedicalRecord m) {
        return this.medicalRepository.addMedicalRecord(m);
    }

    @Override
    public MedicalRecord getMedicalRecordById(int id) {
        return this.medicalRepository.getMedicalRecordById(id);
    }

    @Override
    public List<MedicalRecord> getMedicals(Date date) {
        return this.medicalRepository.getMedicals(date);
    }
    
}
