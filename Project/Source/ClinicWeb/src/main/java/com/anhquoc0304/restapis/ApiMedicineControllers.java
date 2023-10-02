/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.service.CategoryService;
import com.anhquoc0304.service.MedicineService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiMedicineControllers {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping(path = "/api/employee/medicine/list/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Medicine>> getMedicines(
            @RequestParam Map<String, String> params) {
        String name = params.get("name");
        String category = params.get("category");
        if(name != null) {
            return new ResponseEntity<>(this.medicineService.getMedicineByName(name),
            HttpStatus.OK);
        } else if (category != null) {
            return new ResponseEntity<>(this.medicineService.getMedicineByCategoryName(
                    Integer.parseInt(category)
            ),
            HttpStatus.OK);
        }
        return new ResponseEntity<>(this.medicineService.getMedicineByName(null), 
        HttpStatus.OK);
    }
    
    @GetMapping(path = "/api/employee/medicine/categories/", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(this.categoryService.getCategories(),
        HttpStatus.OK);
    }
    
}
