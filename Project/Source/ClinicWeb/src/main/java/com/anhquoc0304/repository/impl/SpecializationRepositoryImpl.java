/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.repository.SpecializationRepository;
import java.util.List;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class SpecializationRepositoryImpl implements SpecializationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Specialization> getSpecials() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Specialization s ORDER BY s.id");
        return q.getResultList();
    }

    @Override
    public Specialization getSpecializationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Specialization s WHERE s.id = :key");
        q.setParameter("key", id);
        return (Specialization) q.getResultList().get(0);
    }

    @Override
    public boolean addSpecialization(Specialization spec) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(spec);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSpecialization(Specialization spec) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(spec);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Specialization s");
        List<Specialization> specs = q.getResultList();
        for (Specialization sp : specs)
            if (sp.getName().equals(name))
                return true;
        return false;
    }

}
