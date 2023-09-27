/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Schedule;
import com.anhquoc0304.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class ScheduleReopositoryImpl implements ScheduleRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public boolean addSchedule(Schedule schedule) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (schedule.getId() == null) {
                s.save(schedule);
                return true;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<Object[]> getScheduleByDate(Date d) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u.fullName, s.shiftStart, s.shiftEnd, r.name, u.userRole, sp.name FROM Schedule s LEFT JOIN s.userId u LEFT JOIN s.roomId r LEFT JOIN u.doctorSet d LEFT JOIN d.specializationId sp WHERE s.scheduleDate =: date ORDER BY s.shiftStart");
        q.setParameter("date", d);
        return q.getResultList();
    }

    @Override
    public int countScheduleByDate(Date d) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Schedule s WHERE s.scheduleDate =: date");
        q.setParameter("date", d);
        return Integer.parseInt(q.getSingleResult().toString());
    }
    
}
