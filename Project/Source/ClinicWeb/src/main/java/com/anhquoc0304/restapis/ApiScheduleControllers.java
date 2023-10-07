/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Room;
import com.anhquoc0304.pojo.Shift;
import com.anhquoc0304.service.RoomService;
import com.anhquoc0304.service.ScheduleService;
import com.anhquoc0304.service.ShiftService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiScheduleControllers {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired ShiftService shiftService;
    
    @GetMapping("/api/admin/rooms/")
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(this.roomService.getRooms(),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/api/admin/room/delete/{id}/")
    public ResponseEntity<Message> deleteRoom(@PathVariable(value = "id") int id) {
        Room r = this.roomService.getRoomById(id);
        Message message = new Message();
        if (this.roomService.deleteRoom(r)) {
            message.setMessage("Xoá phòng thành công.");
            message.setData(r);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Xoá không thành công. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/api/admin/room/add/")
    public ResponseEntity<Message> addRoom(@RequestBody Room r) {
        Message message = new Message();
        if (this.roomService.addRoom(r)) {
            message.setMessage("thêm thành công.");
            message.setData(r);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Có lỗi xảy ra. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/api/admin/shift/")
    public ResponseEntity<List<Shift>> getShifts(){
        return new ResponseEntity<>(this.shiftService.getShifts(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
