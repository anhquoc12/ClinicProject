/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import com.anhquoc0304.pojo.UnitMedicine;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UnitMedicineService {
    List<UnitMedicine> getUnits();
    boolean addUnit(UnitMedicine unit);
    boolean deleteUnit(UnitMedicine unit);
    UnitMedicine getUnitById(int id);
    boolean existName(String name);
}
