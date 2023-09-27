/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Doctor;

/**
 *
 * @author Admin
 */
public interface DoctorRepository {
    boolean addOrUpdateDoctor(Doctor d);
    Doctor getDoctorById(int id);
}
