/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.MedicalRecordRepository;
import com.anhquoc0304.repository.UserRepository;
import java.util.Date;
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
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean addMedicalRecord(MedicalRecord m) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(m);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public MedicalRecord getMedicalRecordById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM MedicalRecord m WHERE m.id =: key");
        q.setParameter("key", id);
        return (MedicalRecord) q.getResultList().get(0);
    }

    @Override
    public List<MedicalRecord> getMedicals(Date date, int patient) {
        Session s = this.factory.getObject().getCurrentSession();
        String sql = "FROM MedicalRecord m ";
        if (date != null) {
            sql += "WHERE DATE(m.createdDate)=: date";
            Query q = s.createQuery(sql);
            q.setParameter("date", date);
            return q.getResultList();
        } else if (patient > 0) {
            User p = this.userRepo.getUserById(patient);
            sql += "WHERE m.patientId.id=: id";
            Query q = s.createQuery(sql);
            q.setParameter("id", patient);
            return q.getResultList();
        } else {
            Query q = s.createQuery(sql);
            return q.getResultList();
        }

    }

}
