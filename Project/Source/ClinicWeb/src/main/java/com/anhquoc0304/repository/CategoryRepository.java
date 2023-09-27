/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Category;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CategoryRepository {
    List<Category> getCategories();
    Category getCategoryByid(int id);
    boolean addCategory(Category c);
    boolean deleteCategory(Category c);
    boolean existName(String name);
}
