/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.repository.DoctorRepository;
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
public class DoctorRepositoryImpl implements DoctorRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public boolean addOrUpdateDoctor(Doctor d) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (d.getId() == null)
                s.save(d);
            else
                s.update(d);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Doctor getDoctorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Doctor d WHERE d.userId.id=:key");
        q.setParameter("key", id);
        return !q.getResultList().isEmpty() ? (Doctor) q.getResultList().get(0) : new Doctor();
    }
    
}
