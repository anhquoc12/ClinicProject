/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.UnitMedicine;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UnitMedicineRepository {
    List<UnitMedicine> getUnits();
    UnitMedicine getUnitById(int id);
    boolean addUnitMedicine(UnitMedicine unit);
    boolean deleteUnitMedicine(UnitMedicine unit);
    boolean existName(String name);
}
