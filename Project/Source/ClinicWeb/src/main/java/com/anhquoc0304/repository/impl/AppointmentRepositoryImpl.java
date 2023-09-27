/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.AppointmentRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class AppointmentRepositoryImpl implements AppointmentRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public boolean addAppointment(Appointment a) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(a);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean countAppointment(Date d) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Appointment a WHERE a.appointmentDate=: date");
        q.setParameter("date", d);
        int number = Integer.parseInt(q.getSingleResult().toString());
        return number < Integer.parseInt(env.getProperty("count.appointment"));
    }

    @Override
    public List<Appointment> getAppointmentByStatus(String status) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Appointment a WHERE a.appointmentStatus =: status AND a.appointmentDate >= CURRENT_DATE ORDER BY a.createdDate ASC");
        q.setParameter("status", status);
        return q.getResultList();
    }

    @Override
    public int countAppointmentByStatus(String status) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Appointment a WHERE a.appointmentStatus =: status AND a.appointmentDate > CURRENT_DATE");
        q.setParameter("status", status);
        return Integer.parseInt(q.getSingleResult().toString());
    }

    @Override
    public List<Appointment> getAppointmentByCurrentUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Appointment a WHERE a.patientId.id=: id ORDER BY a.createdDate ASC");
        q.setParameter("id", user.getId());
        return q.getResultList();
    }

    @Override
    public boolean setAppointmentStatus(Appointment a, String status) {
        Session s = this.factory.getObject().getCurrentSession();
        if (a.getId() != null) {
            a.setAppointmentStatus(status);
            try {
                s.update(a);
                return true;
            } catch(HibernateException ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("test error");
        }
        return false;
    }

    @Override
    public Appointment getAppointmentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Appointment a WHERE a.id=:key");
        q.setParameter("key", id);
        return (Appointment) (q.getResultList().size() > 0 ? q.getResultList().get(0): null);
    }

    @Override
    public List<Appointment> getAppointmentToday() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Appointment a WHERE a.appointmentDate = CURRENT_DATE AND a.appointmentStatus =: status");
        q.setParameter("status", Appointment.CONFIRMED);
        return q.getResultList();
    }

    @Override
    public Appointment getAppointmentByPatientId(User patient) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Appointment a WHERE a.patientId.id =: patientId AND a.appointmentDate = CURRENT_DATE AND a.appointmentStatus =: status");
        q.setParameter("patientId", patient.getId());
        q.setParameter("status", Appointment.PRESENT);
        return (Appointment) q.getResultList().get(0);
    }
    
}
