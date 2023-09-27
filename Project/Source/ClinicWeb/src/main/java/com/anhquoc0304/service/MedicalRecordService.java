/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import com.anhquoc0304.pojo.MedicalRecord;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface MedicalRecordService {
    boolean addMedicalRecord(MedicalRecord m);
    MedicalRecord getMedicalRecordById(int id);
    List<MedicalRecord> getMedicals(Date date);
}
