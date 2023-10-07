/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Shift;
import com.anhquoc0304.repository.ShiftRepository;
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
public class ShiftRepositoryImpl implements ShiftRepository{
    @Autowired
    LocalSessionFactoryBean factory;

    @Override
    public List<Shift> getShifts() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Shift s");
        return q.getResultList();
    }

    @Override
    public Shift getShiftById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Shift s WHERE s.id =:id");
        q.setParameter("id", id);
        return (Shift) q.getResultList().get(0);
    }

    @Override
    public boolean addShift(Shift s) {
        Session ss = this.factory.getObject().getCurrentSession();
        try {
            ss.save(s);
            return true;
        } catch (HibernateException ex) {ex.printStackTrace();}
        return true;
    }

    @Override
    public boolean deleteShift(Shift s) {
        Session ss = this.factory.getObject().getCurrentSession();
        try {
            ss.delete(s);
            return true;
        } catch (HibernateException ex) {ex.printStackTrace();}
        return true;
    }
    
}
