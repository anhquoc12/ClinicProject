/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.Prescription;
import com.anhquoc0304.repository.MedicineRepository;
import com.anhquoc0304.repository.PrescriptionRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private MedicineRepository medicineRepo;

    @Override
    public boolean addPrescription(Prescription p) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(p);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Prescription> getPrescriptionByMedicalRecord(int medicalRecordId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Prescription p WHERE p.medicalRecordId.id =: id");
        q.setParameter("id", medicalRecordId);
        return q.getResultList();
    }

    @Override
    public boolean saveToDatabasePrescription(List<Prescription> listPrescriptions) {
        Session s = this.factory.getObject().getCurrentSession();
        boolean flag = true;
        for (Prescription pre : listPrescriptions) {
            System.out.println(String.format("%d - %d", pre.getMedicineId().getUnitInStock(), pre.getTotalUnit()));
            if (pre.getMedicineId().getUnitInStock() - pre.getTotalUnit() < 0) {
                return false;
            }
        }
        try {
            for (Prescription pre : listPrescriptions) {
                if (this.medicineRepo.updateUnitInStock(pre.getMedicineId(), pre.getTotalUnit()) && this.addPrescription(pre)) {
                    flag = true;
                }
                else {
                    flag = false;
                }
            }
        } catch (HibernateException ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Object[]> getPrescirptionForDetailInvoice(MedicalRecord r) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT p.medicineId.name, p.dosage, p.frequency, p.duration, CONCAT(p.totalUnit, ' ', p.medicineId.unitMedicineId.name), SUM(p.totalUnit * p.medicineId.unitPrice) FROM Prescription p WHERE p.medicalRecordId.id =:key GROUP BY p.medicineId.name, p.dosage, p.frequency, p.duration, CONCAT(p.totalUnit, ' ', p.medicineId.unitMedicineId.name)");
        q.setParameter("key", r.getId());
        return q.getResultList();
    }

    @Override
    public BigDecimal totalMedicine(MedicalRecord r) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT SUM(p.totalUnit * p.medicineId.unitPrice) FROM Prescription p WHERE p.medicalRecordId.id =:key");
        q.setParameter("key", r.getId());
        return  (BigDecimal) q.getSingleResult();
    }

}
