/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Shift;
import com.anhquoc0304.repository.ShiftRepository;
import com.anhquoc0304.service.ShiftService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ShiftServiceImpl implements ShiftService{
    @Autowired
     private ShiftRepository shiftRepo;

    @Override
    public List<Shift> getShifts() {
        return this.shiftRepo.getShifts();
    }

    @Override
    public Shift getShiftById(int id) {
        return this.shiftRepo.getShiftById(id);
    }

    @Override
    public boolean addShift(Shift s) {
        return this.shiftRepo.addShift(s);
    }

    @Override
    public boolean deleteShift(Shift s) {
        return this.shiftRepo.deleteShift(s);
    }
    
}
