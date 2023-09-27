/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.validations.impl;

import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.UserService;
import com.anhquoc0304.validations.Username;
import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
public class UsernameValidation implements ConstraintValidator<Username, User> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(Username constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isValid(User t, ConstraintValidatorContext cvc) {
        if (t.getId() == null) {
            if (this.userService == null)
                return true;
            return !this.userService.existUsername(t.getUsername());
        }
        return true;
    }

}
