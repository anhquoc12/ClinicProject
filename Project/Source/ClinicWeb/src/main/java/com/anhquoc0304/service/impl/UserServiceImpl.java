/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;


import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.UserRepository;
import com.anhquoc0304.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service("UserDetailsService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public List<User> getUsers(String username) {
        return this.userRepo.getUsers(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.getUsers(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Không tồn tại tại username");
        }

        User u = users.get(0);
        Set<GrantedAuthority> authoritys = new HashSet<>();
        authoritys.add(new SimpleGrantedAuthority(u.getUserRole()));
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authoritys);
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        String pw = user.getPassword();
        pw = this.passwordEncoder.encode(pw);
        if (user.getId() == null)
            user.setPassword(pw);
        return this.userRepo.addOrUpdateUser(user);
    }

    @Override
    public List<User> getEmployee() {
        return this.userRepo.getEmployee();
    }

    @Override
    public User getCurrentUser(String username) {
        return this.userRepo.getCurrentUser(username);
    }

    @Override
    public List<Object[]> getUserByUserRole(String userRole) {
        return this.userRepo.getUserByUserRole(userRole);
    }

    @Override
    public boolean existUsername(String username) {
       return this.userRepo.existUsername(username);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public boolean deleteUser(User user) {
        return this.userRepo.deleteUser(user);
    }

    @Override
    public List<User> getPatientByAppointmentToday() {
        return this.userRepo.getPatientByAppointmentToday();
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepo.authUser(username, password);
    }

    @Override
    public List<Object[]> getUserByUserRoleAndName(String userRole, String name) {
        return this.userRepo.getUserByUserRoleAndName(userRole, name);
//throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
