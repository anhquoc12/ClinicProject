/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.UnitMedicine;
import com.anhquoc0304.repository.UnitMedicineRepository;
import com.anhquoc0304.service.UnitMedicineService;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class UnitMedicineServiceImpl implements UnitMedicineService{
    @Autowired
    private UnitMedicineRepository unitRepo;

    @Override
    public List<UnitMedicine> getUnits() {
         return this.unitRepo.getUnits();
    }

    @Override
    public boolean addUnit(UnitMedicine unit) {
        return this.unitRepo.addUnitMedicine(unit);
    }

    @Override
    public boolean deleteUnit(UnitMedicine unit) {
        return this.unitRepo.deleteUnitMedicine(unit);
    }

    @Override
    public UnitMedicine getUnitById(int id) {
        return this.unitRepo.getUnitById(id);
    }

    @Override
    public boolean existName(String name) {
        return this.unitRepo.existName(name);
    }
    
}
