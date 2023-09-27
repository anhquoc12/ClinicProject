/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Prescription;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface PrescriptionRepository {
    boolean addPrescription(Prescription p);
    List<Prescription> getPrescriptionByMedicalRecord(int medicalRecordId);
    boolean saveToDatabasePrescription(List<Prescription> listPrescriptions);
    List<Object[]> getPrescirptionForDetailInvoice(MedicalRecord r);
    BigDecimal totalMedicine(MedicalRecord r);
}
