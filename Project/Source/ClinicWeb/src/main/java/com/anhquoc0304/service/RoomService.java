/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import com.anhquoc0304.pojo.Room;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface RoomService {
    List<Room> getRooms();
    Room getRoomById(int id);
    boolean addRoom(Room r);
    boolean deleteRoom(Room r);
    int countRooms();
    boolean existName(String name);
}
