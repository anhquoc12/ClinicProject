/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.validations.SpecializationName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class SpecializationNameValidation implements ConstraintValidator<SpecializationName, String>{
    @Autowired
    private SpecializationService specialService;

    @Override
    public void initialize(SpecializationName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if (this.specialService == null)
            return true;
        return !this.specialService.existName(t);
    }
    
}
