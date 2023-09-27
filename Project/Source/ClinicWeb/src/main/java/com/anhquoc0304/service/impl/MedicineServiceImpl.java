/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.repository.MedicineRepository;
import com.anhquoc0304.service.MedicineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class MedicineServiceImpl implements MedicineService{
    @Autowired
    private MedicineRepository medicineRepo;

    @Override
    public List<Medicine> getMedicineByName(String name) {
        return this.medicineRepo.getMedicineByName(name);
    }

    @Override
    public boolean addOrUpdateMedicine(Medicine medicine) {
        return this.medicineRepo.addOrUpdateMedicine(medicine);
    }

    @Override
    public boolean deleteMedicine(Medicine medicine) {
         return this.medicineRepo.deleteMedicine(medicine);
    }

    @Override
    public List<Medicine> getMedicineByCategoryName(int id) {
        return this.medicineRepo.getMedicineByCategoryName(id);
    }

    @Override
    public Medicine getMedicineById(int id) {
        return this.medicineRepo.getMedicineById(id);
    }

    @Override
    public boolean updateUnitInStock(Medicine m, int count) {
        return this.medicineRepo.updateUnitInStock(m, count);
    }

    @Override
    public boolean existName(String name) {
        return this.medicineRepo.existName(name);
    }
    
}
