/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface UserRepository {
    List<User> getUsers(String username);
    User getCurrentUser(String username);
    boolean addOrUpdateUser(User user);
    List<User> getEmployee();
    List<Object[]> getUserByUserRole(String userRole);
    boolean existUsername(String username);
    User getUserById(int id);
    boolean deleteUser(User user);
    List<User> getPatientByAppointmentToday();
    boolean authUser(String username, String password);
    List<Object[]> getUserByUserRoleAndName(String userRole, String name);
}
