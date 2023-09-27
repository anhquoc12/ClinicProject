/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Specialization;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface SpecializationRepository {
    List<Specialization> getSpecials();
    Specialization getSpecializationById(int id);
    boolean addSpecialization(Specialization spec);
    boolean deleteSpecialization(Specialization spec);
    boolean existName(String name);
}
