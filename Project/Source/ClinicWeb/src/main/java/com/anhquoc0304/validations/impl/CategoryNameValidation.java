/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.service.CategoryService;
import com.anhquoc0304.validations.CategoryName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class CategoryNameValidation implements ConstraintValidator<CategoryName, String>{
    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(CategoryName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if (this.categoryService == null)
            return true;
        return !this.categoryService.existName(t);
    }
    
}
