/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers.api;

import com.anhquoc0304.service.StatService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
public class ApiStatController {
    @Autowired
    private StatService statService;
    @RequestMapping("/api/admin/stats/count-patients/")
    @CrossOrigin
    public ResponseEntity<Integer> countPatient() {
        return new ResponseEntity<>(this.statService.countPatients(),
                HttpStatus.OK);
    }
    
    @RequestMapping("/api/admin/stats/total-revenues/")
    @CrossOrigin
    public ResponseEntity<Integer> totalRevenues() {
        return new ResponseEntity<>(this.statService.totalRevenue(), HttpStatus.OK);
    }
    
    @RequestMapping("/api/admin/stats/count-medical/")
    @CrossOrigin
    public ResponseEntity<Integer> countMedical() {
        return new ResponseEntity<>(this.statService.countMedical(),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/stats/stat-revenue/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Object[]>> statRevenue(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "typeStat") int type) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate.getMonthValue());
        return new ResponseEntity<>(this.statService.statRevenue(localDate, type),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/stats/medicine-stats/{type}/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Object[]>> top5MedicineStat(@PathVariable(value = "type") int type) {
        if (type == 0) {
            return new ResponseEntity<>(this.statService.top5MedicineStat(true),
            HttpStatus.OK);
        }
        return new ResponseEntity<>(this.statService.top5MedicineStat(false),
            HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/stats/amount-patient/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Object[]>> statAmountPatient(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "typeStat") int type) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new ResponseEntity<>(this.statService.statAmountPatient(localDate, type),
        HttpStatus.OK);
    }
}
