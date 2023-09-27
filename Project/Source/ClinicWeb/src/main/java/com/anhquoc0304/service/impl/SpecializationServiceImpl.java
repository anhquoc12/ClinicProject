/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.repository.SpecializationRepository;
import com.anhquoc0304.service.SpecializationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class SpecializationServiceImpl implements SpecializationService {
    @Autowired
    private SpecializationRepository specRepo;
    @Override
    public List<Specialization> getSpecials() {
        return this.specRepo.getSpecials();
    }

    @Override
    public Specialization getSpecializationById(int id) {
        return this.specRepo.getSpecializationById(id);
    }

    @Override
    public boolean addSpecialization(Specialization spec) {
        return this.specRepo.addSpecialization(spec);
    }

    @Override
    public boolean deleteSpecialization(Specialization spec) {
        return this.specRepo.deleteSpecialization(spec);
    }

    @Override
    public boolean existName(String name) {
        return this.specRepo.existName(name);
    }
    
}
