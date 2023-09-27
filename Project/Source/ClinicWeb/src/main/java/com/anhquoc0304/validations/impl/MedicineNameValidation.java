/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.service.MedicineService;
import com.anhquoc0304.validations.MedicineName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class MedicineNameValidation implements ConstraintValidator<MedicineName, Medicine>{
    @Autowired
    private MedicineService medicineService;

    @Override
    public void initialize(MedicineName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(Medicine t, ConstraintValidatorContext cvc) {
        if (t.getId() == null) {
            if (this.medicineService == null)
                return true;
            return !this.medicineService.existName(t.getName());
        }
        return true;
        
    }
    
}
