/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Medicine;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface MedicineRepository {
    List<Medicine> getMedicineByName(String name);
    List<Medicine> getMedicineByCategoryName(int id);
    Medicine getMedicineById(int id);
    boolean addOrUpdateMedicine(Medicine medicine);
    boolean deleteMedicine(Medicine medicine);
    boolean updateUnitInStock(Medicine m, int count);
    boolean existName(String name);
}
