/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.repository.CategoryRepository;
import com.anhquoc0304.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public List<Category> getCategories() {
        return this.categoryRepo.getCategories();
    }

    @Override
    public Category getCategoryByid(int id) {
        return this.categoryRepo.getCategoryByid(id);
    }

    @Override
    public boolean addCategory(Category c) {
        return this.categoryRepo.addCategory(c);
    }

    @Override
    public boolean deleteCategory(Category c) {
        return this.categoryRepo.deleteCategory(c);
    }

    @Override
    public boolean existName(String name) {
        return this.categoryRepo.existName(name);
    }
    
}
