/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers.api;

import com.anhquoc0304.pojo.Room;
import com.anhquoc0304.pojo.Schedule;
import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.service.RoomService;
import com.anhquoc0304.service.ScheduleService;
import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.service.UserService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
public class ApiScheduleController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private SpecializationService specializationService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/room/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable(value = "id") int id) {
        this.roomService.deleteRoom(this.roomService.getRoomById(id));
    }

    @RequestMapping(value = "/admin/specialization/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable(value = "id") int id) {
        this.specializationService.deleteSpecialization(this.specializationService.getSpecializationById(id));
    }
    
    @RequestMapping(value = "/api/admin/room/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Room>> rooms() {
        return new ResponseEntity<>(this.roomService.getRooms(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/room/add/", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addRoom(@RequestParam(value = "name") String name) {
        Room r = new Room();
        r.setName(name);
        if (this.roomService.addRoom(r))
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD ROOM FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/room/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<String> deleteRoomAPI(@PathVariable(value = "id") int id) {
        Room r = this.roomService.getRoomById(id);
        if (this.roomService.deleteRoom(r))
            return new ResponseEntity<>("DELETE ROOM SUCCESS!!!", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE ROOM FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/specialization/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Specialization>> specializations() {
        return new ResponseEntity<>(this.specializationService.getSpecials(),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/specialization/add/", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addSpecialization(@RequestParam(value = "name") String name) {
        Specialization s = new Specialization();
        s.setName(name);
        if (this.specializationService.addSpecialization(s))
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD ROOM FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/specialization/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<String> deleteSpecializationAPI(@PathVariable(value = "id") int id) {
        Specialization s = this.specializationService.getSpecializationById(id);
        if (this.specializationService.deleteSpecialization(s))
            return new ResponseEntity<>("DELETE SPECIALIZATION SUCCESS!!!", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE SPECIALIZATION FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/schedule/viewSchedule/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Object[]>> viewSchedule() {
        return new ResponseEntity<>(this.scheduleService.getScheduleByDate(new Date()), 
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/schedule/viewSchedule/search/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Object[]>> searchViewSchedule(@RequestParam(value = "date") 
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(this.scheduleService.getScheduleByDate(date), 
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/schedule/", method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addSchedule(@RequestParam Map<String, String> schedule,
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "shiftStart") @DateTimeFormat(pattern = "HH:mm") Date start,
            @RequestParam(value = "shiftEnd") @DateTimeFormat(pattern = "HH:mm") Date end) throws ParseException {
        Schedule s = new Schedule();
        s.setRoomId(this.roomService.getRoomById(Integer.parseInt(schedule.get("room"))));
        s.setScheduleDate(date);
        s.setShiftEnd(end);
        s.setShiftStart(start);
        s.setUserId(this.userService.getUserById(Integer.parseInt(schedule.get("user"))));
        
        if(this.scheduleService.addSchedule(s))
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD SCHEDULE FAILED!!!", HttpStatus.BAD_REQUEST);
    }
}
