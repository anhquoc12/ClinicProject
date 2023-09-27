/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.UnitMedicine;
import com.anhquoc0304.repository.UnitMedicineRepository;
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
public class UnitMedicineRepositoryImpl implements UnitMedicineRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<UnitMedicine> getUnits() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM UnitMedicine u ORDER BY u.id");
        return q.getResultList();
    }

    @Override
    public boolean addUnitMedicine(UnitMedicine unit) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(unit);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUnitMedicine(UnitMedicine unit) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(unit);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public UnitMedicine getUnitById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM UnitMedicine u WHERE u.id=:key");
        q.setParameter("key", id);
        return (UnitMedicine) q.getResultList().get(0);
    }

    @Override
    public boolean existName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM UnitMedicine u");
        List<UnitMedicine> units = q.getResultList();
        for (UnitMedicine u : units)
            if (u.getName().equals(name))
                return true;
        return false;
    }
}
