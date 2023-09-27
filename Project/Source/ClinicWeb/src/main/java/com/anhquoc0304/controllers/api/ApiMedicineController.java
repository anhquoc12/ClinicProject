/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers.api;

import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.UnitMedicine;
import com.anhquoc0304.service.AppointmentService;
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
public class ApiMedicineController {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private UnitMedicineService unitMedicineService;
    @Autowired
    private CategoryService CategoryService;
    
    @RequestMapping(value = "/admin/medicine/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        this.medicineService.deleteMedicine(this.medicineService.getMedicineById(id));
    }
    
    @RequestMapping(value = "/admin/medicine/unitMedicine/{id}/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnit(@PathVariable(value = "id") int id) {
        this.unitMedicineService.deleteUnit(this.unitMedicineService.getUnitById(id));
    }
    
    @RequestMapping(value = "/admin/medicine/category/{id}/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value = "id") int id) {
        this.CategoryService.deleteCategory(this.CategoryService.getCategoryByid(id));
    }
    
    @RequestMapping("/api/employees/medicines/")
    @CrossOrigin
    public ResponseEntity<List<Medicine>> listMedicines(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        String cateId = params.get("cate");
        if (cateId != null && !cateId.isEmpty() ) {
            return new ResponseEntity<>(this.medicineService.getMedicineByCategoryName(Integer.parseInt(cateId)),
                HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(this.medicineService.getMedicineByName(name),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/medicine/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addMedicines(@RequestParam Map<String, String> medicine) {
        Medicine m = new Medicine();
        m.setName(medicine.get("name"));
        m.setUnitInStock(Integer.parseInt(medicine.get("stock")));
        m.setUnitPrice(BigDecimal.valueOf(Long.parseLong(medicine.get("price"))));
        m.setCategoryId(this.CategoryService.getCategoryByid(Integer.parseInt(medicine.get("category"))));
        m.setUnitMedicineId(this.unitMedicineService.getUnitById(Integer.parseInt(medicine.get("unit"))));
        if (this.medicineService.addOrUpdateMedicine(m))
            return new ResponseEntity<>(m, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD MEDICINE FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/medicine/{id}/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> updateMedicines(@RequestParam Map<String, String> medicine,
            @PathVariable(value = "id") int id) {
        Medicine m = this.medicineService.getMedicineById(id);
        m.setName(medicine.get("name"));
        m.setUnitInStock(Integer.parseInt(medicine.get("unitInStock")));
        m.setUnitPrice(BigDecimal.valueOf(Long.parseLong(medicine.get("unitPrice"))));
        m.setCategoryId(this.CategoryService.getCategoryByid(Integer.parseInt(medicine.get("categoryId"))));
        m.setUnitMedicineId(this.unitMedicineService.getUnitById(Integer.parseInt(medicine.get("unitMedicineId"))));
        if (this.medicineService.addOrUpdateMedicine(m))
            return new ResponseEntity<>(m, HttpStatus.CREATED);
        return new ResponseEntity<>("UPDATE MEDICINE FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/medicines/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<String> deleteMedicine(@PathVariable(value = "id") int id) {
        if (this.medicineService.deleteMedicine(this.medicineService.getMedicineById(id)))
            return new ResponseEntity<>("DELETE MEDICINE SUCCESS", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE MEDICINE FAILED", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/medicine/category/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<String> deleteCategoryAPI(@PathVariable(value = "id") int id) {
        try {
            if(this.CategoryService.deleteCategory(this.CategoryService.getCategoryByid(id)))
        {
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("DELETE FAILED", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/medicine/category/", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<Object> addCategory(@RequestParam(value = "name") String name) {
        Category c = new Category();
        c.setName(name);
        if (this.CategoryService.addCategory(c))
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping("/api/admin/medicine/categories/")
    @CrossOrigin
    public ResponseEntity<List<Category>> categories() {
        return new ResponseEntity<>(this.CategoryService.getCategories(), HttpStatus.OK);
    }
    
    @RequestMapping("/api/admin/medicine/unit-medicines/")
    @CrossOrigin
    public ResponseEntity<List<UnitMedicine>> unitMedicines() {
        return new ResponseEntity<>(this.unitMedicineService.getUnits(),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/medicine/unit-medicine", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addUnit(@RequestParam(value = "name") String name) {
        UnitMedicine unit = new UnitMedicine();
        unit.setName(name);
        if (this.unitMedicineService.addUnit(unit))
            return new ResponseEntity<>(unit, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD UNIT MEDICINE FAILED!!!", 
        HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/medicine/unit-medicine/{id}", method = RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<String> deleteUnitMedicine(@PathVariable(value = "id") int id) {
        UnitMedicine unit = this.unitMedicineService.getUnitById(id);
        if (this.unitMedicineService.deleteUnit(unit))
            return new ResponseEntity<>("DELETE UNIT MEDICINE SUCCESS", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE UNIT MEDICINE FAILED!!!", 
        HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/api/admin/medicine/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Medicine> getMedicineById(@PathVariable(value = "id") int id) {
        Medicine m = this.medicineService.getMedicineById(id);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }
}
