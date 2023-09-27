/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Room;
import com.anhquoc0304.repository.RoomRepository;
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
public class RoomRepositoryImpl implements RoomRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Room> getRooms() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Room r");
        return q.getResultList();
    }

    @Override
    public Room getRoomById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Room r WHERE r.id = :key");
        q.setParameter("key", id);
        return (Room) q.getResultList().get(0);
    }

    @Override
    public boolean addRoom(Room r) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(r);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRoom(Room r) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(r);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public int countRooms() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Room r");
        return Integer.parseInt(q.getSingleResult().toString());
    }

    @Override
    public boolean existName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Room r");
        List<Room> rooms = q.getResultList();
        for (Room r :rooms)
            if (r.getName().equals(name))
                return true;
        return false;
    }
    
}
