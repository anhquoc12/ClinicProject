/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import com.anhquoc0304.service.StatService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class StatController {
    @Autowired
    private StatService statService;
    
    @RequestMapping("/admin/stat")
    public String stat(Model model) {
        model.addAttribute("dateIn", LocalDate.now());
        model.addAttribute("patient", statService.countPatients());
        model.addAttribute("subTotal", this.statService.totalRevenue());
        model.addAttribute("medical", statService.countMedical());
        model.addAttribute("top5", this.statService.top5MedicineStat(true));
        model.addAttribute("bottom5", this.statService.top5MedicineStat(false));
        return "stat";
    }
    
    @RequestMapping("/admin/stat/filter")
    public String statFilter(Model model, 
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "typeStat") String type) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Object[]> revenues = this.statService.statRevenue(localDate, Integer.parseInt(type));
        List<Object[]> amountPatient = this.statService.statAmountPatient(localDate, Integer.parseInt(type));
        model.addAttribute("amount", amountPatient);
        model.addAttribute("revenues", revenues);
        model.addAttribute("dateIn", localDate);
        model.addAttribute("patient", statService.countPatients());
        model.addAttribute("subTotal", this.statService.totalRevenue());
        model.addAttribute("medical", statService.countMedical());
        model.addAttribute("top5", this.statService.top5MedicineStat(true));
        model.addAttribute("bottom5", this.statService.top5MedicineStat(false));
        return "stat";
    }
}
