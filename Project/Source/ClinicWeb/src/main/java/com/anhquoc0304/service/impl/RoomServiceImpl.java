/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Room;
import com.anhquoc0304.repository.RoomRepository;
import com.anhquoc0304.service.RoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomRepository roomRepo;

    @Override
    public List<Room> getRooms() {
        return this.roomRepo.getRooms();
    }

    @Override
    public Room getRoomById(int id) {
        return this.roomRepo.getRoomById(id);
    }

    @Override
    public boolean addRoom(Room r) {
        return this.roomRepo.addRoom(r);
    }

    @Override
    public boolean deleteRoom(Room r) {
        return this.roomRepo.deleteRoom(r);
    }

    @Override
    public int countRooms() {
        return this.roomRepo.countRooms();
    }

    @Override
    public boolean existName(String name) {
        return this.roomRepo.existName(name);
    }
    
}
