/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.service.RoomService;
import com.anhquoc0304.validations.RoomName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class RoomNameValidation implements ConstraintValidator<RoomName, String>{
    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(RoomName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if (this.roomService == null)
            return true;
        return !this.roomService.existName(t);
    }
    
}
