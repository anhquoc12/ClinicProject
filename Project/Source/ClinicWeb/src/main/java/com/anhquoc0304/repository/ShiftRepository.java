/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Shift;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ShiftRepository {
    List<Shift> getShifts();
    Shift getShiftById(int id);
    boolean addShift(Shift s);
    boolean deleteShift(Shift s);
}
