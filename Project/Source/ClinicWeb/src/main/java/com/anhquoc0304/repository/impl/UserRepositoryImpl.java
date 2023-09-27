/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.UserRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Override
    public List<User> getUsers(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        String query = "FROM User u ";
        if (!username.isEmpty()) {
            query += "WHERE u.username =: user";
            Query q = s.createQuery(query);
            q.setParameter("user", username);
            return q.getResultList();
        }
        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (user.getId() == null)
                s.save(user);
            else
                s.update(user);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getEmployee() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User u WHERE u.userRole = 'DOCTOR' OR u.userRole = 'NURSE'");
        return q.getResultList();
    }

    @Override
    public User getCurrentUser(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User u WHERE u.username = :user");
        q.setParameter("user", username);
        if (q.getResultList().size() <= 0)
            return null;
        return (User) q.getResultList().get(0);
    }

    @Override
    public List<Object[]> getUserByUserRole(String userRole) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q;
        if (userRole == User.DOCTOR) {
            q = s.createQuery("SELECT u.id, u.avatar, u.fullName, u.address, u.email, u.phone, s.name, s.id FROM Doctor d LEFT JOIN d.userId u LEFT JOIN d.specializationId s WHERE u.userRole = :role");
        } else {
            q = s.createQuery("SELECT u.id, u.avatar, u.fullName, u.address, u.email, u.phone FROM User u WHERE u.userRole = :role");
        }
        q.setParameter("role", userRole);
        return q.getResultList();
    }

    @Override
    public boolean existUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User u");
        List<User> users = q.getResultList();
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User u WHERE u.id=: key");
        q.setParameter("key", id);
        return (User) q.getSingleResult();
    }

    @Override
    public boolean deleteUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(user);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getPatientByAppointmentToday() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT a.patientId FROM Appointment a WHERE a.appointmentDate = CURRENT_DATE AND a.appointmentStatus =: status");
        q.setParameter("status", Appointment.PRESENT);
        return q.getResultList();
    }

    @Override
    public boolean authUser(String username, String password) {
        User user = this.getCurrentUser(username);
        return this.passwordEncode.matches(password, user.getPassword());
    }

    @Override
    public List<Object[]> getUserByUserRoleAndName(String userRole, String name) {
        Session s = this.factory.getObject().getCurrentSession();
        String sql;
        if (userRole == User.DOCTOR) {
            sql ="SELECT u.id, u.avatar, u.fullName, u.address, u.email, u.phone, s.name, s.id FROM Doctor d LEFT JOIN d.userId u LEFT JOIN d.specializationId s WHERE u.userRole = :role AND u.fullName LIKE :key";
        } else {
            sql = "SELECT u.id, u.avatar, u.fullName, u.address, u.email, u.phone FROM User u WHERE u.userRole = :role AND u.fullName LIKE :key";
        }
        Query q = s.createQuery(sql);
        q.setParameter("key", '%' + name + '%');
        q.setParameter("role", userRole);
        return q.getResultList();
    }

}
