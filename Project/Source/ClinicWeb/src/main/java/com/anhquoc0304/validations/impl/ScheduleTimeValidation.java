/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.pojo.Schedule;
import com.anhquoc0304.validations.ScheduleTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Admin
 */
public class ScheduleTimeValidation implements ConstraintValidator<ScheduleTime, Schedule>{

    @Override
    public void initialize(ScheduleTime constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(Schedule t, ConstraintValidatorContext cvc) {
        return t.getShiftStart().compareTo(t.getShiftEnd()) < 0;
    }
    
}
