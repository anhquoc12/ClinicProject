/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.repository.MedicineRepository;
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
public class MedicineRepositoryImpl implements MedicineRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Medicine> getMedicineByName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        String sql = "FROM Medicine m ";
        if (name != null && !name.isEmpty()) {
            sql += "WHERE m.name LIKE: n";
        }
        Query q = s.createQuery(sql);
        if (name != null && !name.isEmpty()) {
            q.setParameter("n", "%" + name + "%");
        }
        return q.getResultList();
    }

    @Override
    public boolean addOrUpdateMedicine(Medicine medicine) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (medicine.getId() != null)
                s.update(medicine);
            else
                s.save(medicine);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedicine(Medicine medicine) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(medicine);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Medicine> getMedicineByCategoryName(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        String sql = "SELECT m FROM Medicine m JOIN m.categoryId c WHERE c.id =: n";
        Query q = s.createQuery(sql);
        q.setParameter("n", id);
        return q.getResultList();
    }

    @Override
    public Medicine getMedicineById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Medicine m WHERE m.id =:id");
        q.setParameter("id", id);
        return (Medicine) q.getResultList().get(0);
    }

    @Override
    public boolean updateUnitInStock(Medicine m, int count) {
        Session s = this.factory.getObject().getCurrentSession();
        if (m.getUnitInStock() - count < 0)
            return false;
        try {
            m.setUnitInStock(m.getUnitInStock() - count);
            s.update(m);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Medicine m");
        List<Medicine> medicines = q.getResultList();
        for (Medicine m : medicines)
            if (m.getName().equals(name))
                return true;
        return false;
    }
    
}
