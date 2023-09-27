/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

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
import java.time.ZoneId;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class ScheduleController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SpecializationService specializationService;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("rooms", this.roomService.getRooms());
        model.addAttribute("users", this.userService.getEmployee());
        model.addAttribute("specials", this.specializationService.getSpecials());
        model.addAttribute("rooms", this.roomService.getRooms());
    }
    
    @RequestMapping("/admin/schedule")
    public String schedule(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("rooms", this.roomService.getRooms());
        model.addAttribute("users", this.userService.getEmployee());
        return "schedule";
    }

    @RequestMapping(value = "/admin/schedule", method = RequestMethod.POST)
    public String addSchedule(
            Model model, @ModelAttribute(value = "schedule") @Valid Schedule schedule, BindingResult br, HttpServletRequest servlet) {
        if (!br.hasErrors()) {
            if (this.scheduleService.addSchedule(schedule)) {
                model.addAttribute("msgSuccess", "Thành Công");
                return "schedule";
            }
        }
        return "schedule";
    }

    @RequestMapping("/admin/room")
    public String room(Model model
    ) {
        model.addAttribute("rooms", this.roomService.getRooms());
        model.addAttribute("room", new Room());
        return "room";
    }

    @RequestMapping(value = "/admin/room", method = RequestMethod.POST)
    public String addRoom(Model model, @ModelAttribute(value = "room") @Valid Room room,
            BindingResult br, HttpServletRequest sevlet) {
        model.addAttribute("msgErr", null);
        if (!br.hasErrors()) {
            if (this.roomService.addRoom(room)) {
                return "redirect:/admin/room";
            } else {
                model.addAttribute("msgErr", "Có lỗi xảy ra");
                return "room";
            }
        }
        return "room";
    }

    @RequestMapping("/admin/specialization")
    public String specialization(Model model
    ) {
        model.addAttribute("special", new Specialization());
        model.addAttribute("specials", this.specializationService.getSpecials());
        return "specialization";
    }

    @RequestMapping(value = "/admin/specialization", method = RequestMethod.POST)
    public String addSpecialization(Model model,
            @ModelAttribute(value = "special") @Valid Specialization s,
            BindingResult br, HttpServletRequest servlet
    ) {
        model.addAttribute("msgErr", null);
        if (!br.hasErrors()) {
            if (this.specializationService.addSpecialization(s)) {
                return "redirect:/admin/specialization";
            } else {
                model.addAttribute("msgErr", "Có lỗi xảy ra");
                return "forward:/admin/specialization";
            }
        }
        return "specialization";
    }

    @RequestMapping("/schedule/viewSchedule")
    public String viewSchedule(Model model
    ) {
        Date date = Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        int count = this.scheduleService.countScheduleByDate(date);
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("count", count);
        model.addAttribute("listSchedule", this.scheduleService.getScheduleByDate(date));
        return "viewSchedule";
    }

    @RequestMapping("/schedule/viewSchedule/search")
    public String searchSchedule(Model model, @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateS) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        LocalDate local = dateS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int count = this.scheduleService.countScheduleByDate(dateS);
        model.addAttribute("date", local);
        model.addAttribute("count", count);
        model.addAttribute("listSchedule", this.scheduleService.getScheduleByDate(dateS));
        return "viewSchedule";
    }
}
