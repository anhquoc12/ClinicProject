/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.repository.InvoiceRepository;
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
public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getInvoices() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT i.id, r.patientId.fullName, i.createDate, i.paymentStatus, SUM(m.unitPrice * p.totalUnit) + r.examinationFee FROM Invoice i INNER JOIN i.medicalRecordId r INNER JOIN r.prescriptionSet p INNER JOIN p.medicineId m GROUP BY i.id, r.patientId.fullName, i.createDate, i.paymentStatus ORDER BY i.createDate DESC");
        return q.getResultList();
    }

    @Override
    public boolean createInvoiceBeforePay(Invoice i) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(i);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
        
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Invoice i WHERE i.id =: key");
        q.setParameter("key", id);
        return (Invoice) q.getResultList().get(0);
    }

    @Override
    public boolean payment(Invoice i) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(i);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
