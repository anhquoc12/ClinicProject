/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.service.SpecializationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiSpecializationController {
    @Autowired
    SpecializationService specialService;
    
    @GetMapping("/api/specialization/lists/")
    public ResponseEntity<List<Specialization>> getSpecializations() {
        return new ResponseEntity<>(this.specialService.getSpecials(), HttpStatus.OK);
    }
}
