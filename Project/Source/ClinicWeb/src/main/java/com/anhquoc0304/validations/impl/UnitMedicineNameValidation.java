/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.service.UnitMedicineService;
import com.anhquoc0304.validations.UnitMedicineName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class UnitMedicineNameValidation implements ConstraintValidator<UnitMedicineName, String>{
    @Autowired
    private UnitMedicineService unitService;

    @Override
    public void initialize(UnitMedicineName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if (this.unitService == null)
            return true;
        return !this.unitService.existName(t);
    }
    
}
