/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.repository.CategoryRepository;
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
public class CategoryRepositoryImpl implements CategoryRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Category> getCategories() {
         Session s = this.factory.getObject().getCurrentSession();
         Query q = s.createQuery("FROM Category c ORDER BY c.id");
         return q.getResultList();
    }

    @Override
    public Category getCategoryByid(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Category c WHERE c.id = :key");
        q.setParameter("key", id);
        return (Category) q.getResultList().get(0);
    }

    @Override
    public boolean addCategory(Category c) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Category c) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Category c");
        List<Category> categories = q.getResultList();
        for (Category c : categories) {
            if (c.getName().equals(name))
                return true;
        }
        return false;
    }
    
}
