/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.components.JWTService;
import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.DoctorService;
import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jWTService;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SpecializationService specialService;
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/api/login/")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword())) {
            String token = this.jWTService.genarateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Lỗi username và password không trùng khớp.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/api/current-user/", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @CrossOrigin
    public ResponseEntity<User> currentUser(Principal user) {
        User currentU = this.userService
                .getCurrentUser(user.getName());
        return new ResponseEntity<>(currentU, HttpStatus.OK);
    }

    @PostMapping("/api/update/user/")
    public ResponseEntity<Message> updateUser(@RequestParam Map<String, String> params,
            @RequestPart(required = false) MultipartFile file, @Valid User user,
            BindingResult binding) {
        System.out.println(file);
        user = this.userService.getUserById(Integer.parseInt(params.get("id")));
        user.setUsername(params.get("username"));
        user.setFullName(params.get("fullName"));
        user.setEmail(params.get("email"));
        user.setPhone(params.get("phone"));
        user.setAddress(params.get("address"));
        Message message = new Message();
        if (binding.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : binding.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            message.setData(errors);
            message.setMessage("Kiểm tra lại thông tin vừa nhập!!!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (file != null) {
            try {
                Map m = this.cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));

            } catch (IOException ex) {
                message.setMessage("Không thể upload ảnh đại diện");
                message.setData(null);
            }
        }
        if (this.userService.addOrUpdateUser(user)) {
            message.setData(user);
            message.setMessage("Lưu thông tin thành công.");
        }
//        JsonNode result = new ObjectMapper().valueToTree(message);
        if (message.getData() != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/update/user/password/")
    public ResponseEntity<Message> changePassword(@RequestParam Map<String, String> passwordParams,
            Principal currentUser) {
        User user = this.userService.getCurrentUser(currentUser.getName());
        Message message = new Message();
        if (this.passwordEncoder.matches(passwordParams.get("current"),
                user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(
                    passwordParams.get("new")));
            if (this.userService.addOrUpdateUser(user)) {
                message.setMessage("Đổi mật khẩu thành công!!!");
                message.setData(user);
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                message.setMessage("Đổi mật khẩu không thành công!!!");
                message.setData(user);
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        message.setMessage("Mật khẩu hiện tại không đúng. Vui Lòng Nhập lại");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/add/user/patient/")
    public ResponseEntity<Message> addPatient(@RequestParam Map<String, String> patient,
            @RequestPart MultipartFile file, @Valid User user,
            BindingResult binding) {
        user = new User();
        user.setUsername(patient.get("username"));
        user.setPassword(patient.get("password"));
        user.setFullName(patient.get("fullName"));
        user.setEmail(patient.get("email"));
        user.setPhone(patient.get("phone"));
        user.setAddress(patient.get("address"));
        user.setUserRole(User.PATIENT);
        System.out.println(user);
        Message message = new Message();
        if (binding.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : binding.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            message.setData(errors);
            message.setMessage("Kiểm tra lại thông tin vừa nhập!!!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (file != null) {
            try {
                Map m = this.cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));
                if (this.userService.addOrUpdateUser(user)) {
                    message.setMessage("Đăng Ký Thành Công");
                    message.setData(user);
                }
            } catch (IOException ex) {
                message.setMessage("Không thể upload ảnh đại diện");
                message.setData(null);
            }
        }
        if (message.getData() != null) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.userService.getUserById(id),
                HttpStatus.OK);
    }

    @GetMapping(path = "/api/user/patients/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object[]>> getPatients() {
        return new ResponseEntity<>(this.userService.getUserByUserRole(User.PATIENT),
                HttpStatus.OK);
    }

    @GetMapping(path = "/api/user/list/{roles}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object[]>> getUsersByRoles(@PathVariable(value = "roles") String role, @RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return new ResponseEntity<>(this.userService.getUserByUserRoleAndName(role, name),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(this.userService.getUserByUserRole(role),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/admin/user/delete/{id}/")
    public ResponseEntity<Message> deleletUser(@PathVariable(value = "id") int id) {
        Message m = new Message();
        User user = this.userService.getUserById(id);
        if (this.userService.deleteUser(user)) {
            m.setMessage("Xoá bỏ thành công");
            m.setData(user);
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        m.setMessage("Xoá bỏ không thành công");
        return new ResponseEntity<>(m, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/api/admin/user/doctor/add/")
    public ResponseEntity<Message> addDoctor(@RequestParam Map<String, String> doctor,
            @RequestPart(required = false) MultipartFile file) {
        User user = new User();
//        String fullName = doctor.get("fullName");
//        String normalizedFullName = StringUtils.stripAccents(fullName.toLowerCase());
        

        if (doctor.get("id") != null) {
            try {
                user = this.userService.getUserById(Integer.parseInt(doctor.get("id")));

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } else {
            String fullName = doctor.get("fullName");
            String normalizedFullName = StringUtils.stripAccents(fullName.toLowerCase());
            String[] parts = normalizedFullName.split(" ");
            user.setUsername(parts[0] + parts[parts.length - 1] + (int) (Math.random() * 1000));
            user.setPassword("12345678");
        }
        user.setFullName(doctor.get("fullName"));
        user.setAddress(doctor.get("address"));
        user.setPhone(doctor.get("phone"));
        user.setEmail(doctor.get("email"));
        user.setUserRole(User.DOCTOR);
        Message message = new Message();
        if (file != null) {

            try {
                Map m = this.cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                message.setMessage("Không thể upload ảnh đại diện");
                message.setData(null);
            }

        }
        if (this.userService.addOrUpdateUser(user)) {
            Doctor d = this.doctorService.getDoctorById(this.userService
                    .getCurrentUser(user.getUsername()).getId());
            d.setUserId(user);
            d.setSpecializationId(this.specialService.getSpecializationById(Integer.parseInt(doctor.get("specialId"))));
            this.doctorService.addOrUpdateDoctor(d);
            message.setMessage("Thêm thành công");
            message.setData(d);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping(path = "/api/admin/user/nurse/add/")
    public ResponseEntity<Message> addNUrse(@RequestParam Map<String, String> nurse,
            @RequestPart(required = false) MultipartFile file) {
        User user = new User();
        if (nurse.get("id") != null) {
            try {
                user = this.userService.getUserById(Integer.parseInt(nurse.get("id")));

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } else {
            String fullName = nurse.get("fullName");
            String normalizedFullName = StringUtils.stripAccents(fullName.toLowerCase());
            String[] parts = normalizedFullName.split(" ");
            user.setUsername(parts[0] + parts[parts.length - 1] + (int) (Math.random() * 1000));
            user.setPassword("12345678");
        }
        user.setFullName(nurse.get("fullName"));
        user.setAddress(nurse.get("address"));
        user.setPhone(nurse.get("phone"));
        user.setEmail(nurse.get("email"));
        user.setUserRole(User.NURSE);
        Message message = new Message();
        if (file != null) {

            try {
                Map m = this.cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                message.setMessage("Không thể upload ảnh đại diện");
                message.setData(null);
            }

        }
        if (this.userService.addOrUpdateUser(user)) {
            message.setMessage("Thêm thành công");
            message.setData(user);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
