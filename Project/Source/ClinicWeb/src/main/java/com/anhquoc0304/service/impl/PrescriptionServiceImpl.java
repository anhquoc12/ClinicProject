/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Prescription;
import com.anhquoc0304.repository.PrescriptionRepository;
import com.anhquoc0304.service.PrescriptionService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public boolean addPrescription(Prescription p) {
        return this.prescriptionRepository.addPrescription(p);
    }

    @Override
    public boolean saveToDatabasePrescription(List<Prescription> listPrescriptions) {
        return this.prescriptionRepository.saveToDatabasePrescription(listPrescriptions);
    }

    @Override
    public List<Prescription> getPrescriptionByMedicalRecord(int medicalRecordId) {
        return this.prescriptionRepository.getPrescriptionByMedicalRecord(medicalRecordId);
    }

    @Override
    public List<Object[]> getPrescirptionForDetailInvoice(MedicalRecord r) {
        return this.prescriptionRepository.getPrescirptionForDetailInvoice(r);
    }

    @Override
    public BigDecimal totalMedicine(MedicalRecord r) {
        return this.prescriptionRepository.totalMedicine(r);
    }
}
