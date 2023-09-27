/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.DoctorService;
import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class UserController {

    @Autowired
    private UserService userDetailsService;
    @Autowired
    private Cloudinary cloud;
    @Autowired
    private SpecializationService specialService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    

    @ModelAttribute
    public void specialList(Model model) {
        model.addAttribute("specials", this.specialService.getSpecials());
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addPatient(Model model, @ModelAttribute(value = "user") @Valid User user,
            BindingResult br, HttpServletRequest servlet) {
        if (!br.hasErrors()) {
            List<String> msgErr = new ArrayList<>();
            if (user.getFile() != null) {
                try {
                    Map m = this.cloud.uploader().upload(user.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar((String) m.get("secure_url"));
                } catch (IOException ex) {
                    msgErr.add("Lỗi khi upload avatar! Vui lòng upload ảnh khác hoặc thử lại");
                    model.addAttribute("msgErr", msgErr);
                    return "register";
                }
            }

            user.setUserRole(User.PATIENT);
            if (this.userDetailsService.addOrUpdateUser(user)) {
                return "login";
            } else {
                msgErr.add("Đã có Lỗi khi đăng ký! Vui Lòng thử lại");
                model.addAttribute("msgErr", msgErr);
                return "register";
            }
        }
        return "register";
    }

    @RequestMapping("/admin/doctor")
    public String doctor(Model model) {
        model.addAttribute("specials", this.specialService.getSpecials());
        model.addAttribute("isDoctor", true);
        model.addAttribute("user", new User());
        return "employee";
    }

    @RequestMapping(value = "/admin/doctor", method = RequestMethod.POST)
    public String addOrUpdateDoctor(@RequestParam(value = "specialization") String specialId,
            Model model, @ModelAttribute(value = "user") @Valid User user,
            BindingResult br, HttpServletRequest servlet) {
        if (br.hasErrors()) {
            model.addAttribute("specials", this.specialService.getSpecials());
            model.addAttribute("isDoctor", true);
            model.addAttribute("userR", user);
            return "employee";
        }
        String msg = null;
        // Lấy avatar
        if (user.getFile() != null) {
            try {
                Map m = this.cloud.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                return doctor(model);
            }
        }
        user.setUserRole(User.DOCTOR);
        if (this.userDetailsService.addOrUpdateUser(user)) {
            // Nếu Thành công
            Doctor d = this.doctorService.getDoctorById(this.userDetailsService
                    .getCurrentUser(user.getUsername()).getId());
            d.setUserId(user);
            d.setSpecializationId(this.specialService
                    .getSpecializationById(Integer.parseInt(specialId)));
            if (this.doctorService.addOrUpdateDoctor(d)) {
                return "redirect:/admin/users/doctor";
            } else {
                msg = "Có Lỗi Xảy ra Chuyên Ngành";
            }
        } else {
            msg = "Có lỗi xảy ra";
        }
        return "redirect:/admin/users/doctor";
    }

    @RequestMapping("/admin/doctor/{id}")
    public String updateDoctor(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("user", this.userDetailsService.getUserById(id));
        model.addAttribute("doctor", this.doctorService.getDoctorById(id));
        return "employee";
    }

    @RequestMapping("/admin/nurse")
    public String nurse(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isDoctor", false);
        return "employee";
    }

    @RequestMapping(value = "/admin/nurse", method = RequestMethod.POST)
    public String addOrUpdateNurse(Model model, @ModelAttribute(value = "user") @Valid User user,
            BindingResult br, HttpServletRequest servlet) {
        if (br.hasErrors()) {
            model.addAttribute("isDoctor", false);
            return "employee";
        }
        String msg = null;
        if (user.getFile() != null) {
            try {
                Map m = this.cloud.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                msg = ex.getMessage();
                return "register";
            }
        }
        user.setUserRole(User.NURSE);
        if (this.userDetailsService.addOrUpdateUser(user)) {
            return "redirect:/admin/users/nurse";
        } else {
            msg = "Đã có Lỗi!Vui Lòng thử lại";
        }
        model.addAttribute("msg", msg);
        return "employee";
    }

    @RequestMapping("/admin/nurse/{id}")
    public String updateNurse(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("user", this.userDetailsService.getUserById(id));
        return "employee";
    }

    @RequestMapping("/admin/users/doctor")
    public String doctors(Model model, @RequestParam Map<String, String> params) {
        String name = params.get("name");
        List<Object[]> users = this.userDetailsService.getUserByUserRole(User.DOCTOR);
        List<Object[]> filterUser = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            filterUser = this.userDetailsService
                    .getUserByUserRoleAndName(User.DOCTOR, name);
            if (filterUser.size() > 0) {
                model.addAttribute("userList", filterUser);
            }
        } else {
            model.addAttribute("userList", users);
        }
        model.addAttribute("path", "Doctor");

        return "users";
    }

    @RequestMapping("/admin/users/patient")
    public String patients(Model model, @RequestParam Map<String, String> params) {
        String name = params.get("name");
        List<Object[]> users = this.userDetailsService.getUserByUserRole(User.PATIENT);
        List<Object[]> filterUsers = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            filterUsers = this.userDetailsService
                    .getUserByUserRoleAndName(User.PATIENT, name);
            if (filterUsers.size() > 0) {
                model.addAttribute("userList", filterUsers);
            }
        } else {
            model.addAttribute("userList", users);
        }
        model.addAttribute("path", "Patient");

        return "users";
    }

    @RequestMapping("/admin/users/nurse")
    public String nurses(Model model, @RequestParam Map<String, String> params) {
        String name = params.get("name");
        List<Object[]> users = this.userDetailsService.getUserByUserRole(User.NURSE);
        List<Object[]> filterUsers = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            filterUsers = this.userDetailsService
                    .getUserByUserRoleAndName(User.NURSE, name);
            if (filterUsers.size() > 0) {
                model.addAttribute("userList", filterUsers);
            }
        } else {
            model.addAttribute("userList", users);
        }
        model.addAttribute("path", "Nurse");

        return "users";
    }

    @RequestMapping("/infoUser")
    public String infoUser(Model model) {
        User u = this.userDetailsService.
                getCurrentUser(
                        SecurityContextHolder.getContext()
                                .getAuthentication().getName());
        model.addAttribute("user", u);
        return "infoUser";
    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute(value = "user") @Valid User user, Model model) {
        if (this.userDetailsService.addOrUpdateUser(user)) {
            return "redirect:/infoUser";
        }
        model.addAttribute("err", "Có Lỗi xảy ra");
        return "infoUser";
    }
    
    @GetMapping("/change-password")
    public String change() {
        return "changePassword";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam Map<String, String> params, Model model) {
        User u = this.userDetailsService.getCurrentUser(SecurityContextHolder.getContext()
                                .getAuthentication().getName());
        if (u == null)
            return "redirect:/login";
        if(passwordEncoder.matches(params.get("currentPW"), u.getPassword())) {
            u.setPassword(params.get("password"));
            this.userDetailsService.addOrUpdateUser(u);
            return "redirect:/";
        }
        else {
            System.out.println(params.get("currentPW"));
        }
            
        model.addAttribute("err", "Mật khẩu hiện tại không hợp lệ");
        return "changePassword";
    }
}
