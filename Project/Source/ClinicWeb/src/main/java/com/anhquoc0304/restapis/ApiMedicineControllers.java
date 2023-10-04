/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.UnitMedicine;
import com.anhquoc0304.service.CategoryService;
import com.anhquoc0304.service.MedicineService;
import com.anhquoc0304.service.UnitMedicineService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private UnitMedicineService unitService;

    @GetMapping(path = "/api/employee/medicine/list/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Medicine>> getMedicines(
            @RequestParam Map<String, String> params) {
        String name = params.get("name");
        String category = params.get("category");
        if (name != null) {
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

    @DeleteMapping("/api/admin/medicine/delete/{id}/")
    public ResponseEntity<Message> deleteMedicine(@PathVariable(value = "id") int id) {
        Medicine m = this.medicineService.getMedicineById(id);
        Message message = new Message();
        if (this.medicineService.deleteMedicine(m)) {
            message.setMessage("Xoá thuốc thành công.");
            message.setData(m);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Xoá không thành công. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/api/admin/medicine/unit-medicines/")
    public ResponseEntity<List<UnitMedicine>> getUnits() {
        return new ResponseEntity<>(this.unitService.getUnits(),
                HttpStatus.OK);
    }
    
    @PostMapping("/api/admin/medicine/add/")
    public ResponseEntity<Message> addMedicines(@RequestParam Map<String, String> medicine) {
        Medicine m = new Medicine();
        System.out.println(medicine.get("id"));
        try {
            if(medicine.get("id") != null)
            m.setId(Integer.parseInt(medicine.get("id")));
        } catch(NumberFormatException ex) {ex.printStackTrace();}
        m.setName(medicine.get("name"));
        m.setUnitInStock(Integer.parseInt(medicine.get("unitInStock")));
        m.setUnitPrice(BigDecimal.valueOf(Long.parseLong(medicine.get("unitPrice"))));
        m.setCategoryId(this.categoryService.getCategoryByid(Integer.parseInt(medicine.get("category"))));
        m.setUnitMedicineId(this.unitService.getUnitById(Integer.parseInt(medicine.get("unit"))));
        Message message = new Message();
        if (this.medicineService.addOrUpdateMedicine(m)) {
            message.setMessage("Thêm thành công.");
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        
            message.setMessage("Có lỗi xảy ra. Vui lòng thử lại");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/api/admin/medicine/{id}/")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable(value = "id")
    int id) {
        return new ResponseEntity<>(this.medicineService.getMedicineById(id),
        HttpStatus.OK);
    }
    
    @DeleteMapping("/api/admin/medicine/categories/delete/{id}/")
    public ResponseEntity<Message> deleteCategory(@PathVariable(value = "id") int id) {
        Category c = this.categoryService.getCategoryByid(id);
        Message message = new Message();
        if (this.categoryService.deleteCategory(c)) {
            message.setMessage("Xoá loại thuốc thành công.");
            message.setData(c);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Xoá không thành công. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/api/admin/medicine/categories/add/")
    public ResponseEntity<Message> addCategory(@RequestBody Category c) {
        Message message = new Message();
        if (this.categoryService.addCategory(c)) {
            message.setMessage("thêm thành công.");
            message.setData(c);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Có lỗi xảy ra. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/api/admin/medicine/unit/delete/{id}/")
    public ResponseEntity<Message> deleteUnitmedicine(@PathVariable(value = "id") int id) {
        UnitMedicine u = this.unitService.getUnitById(id);
        Message message = new Message();
        if (this.unitService.deleteUnit(u)) {
            message.setMessage("Xoá đơn vị thuốc thành công.");
            message.setData(u);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Xoá không thành công. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/api/admin/medicine/unit/add/")
    public ResponseEntity<Message> addUnit(@RequestBody UnitMedicine u) {
        Message message = new Message();
        if (this.unitService.addUnit(u)) {
            message.setMessage("thêm thành công.");
            message.setData(u);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setMessage("Có lỗi xảy ra. Vui lòng thử lại.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
