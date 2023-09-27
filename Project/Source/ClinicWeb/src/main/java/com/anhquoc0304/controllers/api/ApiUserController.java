/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers.api;

import com.anhquoc0304.components.JWTService;
import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.DoctorService;
import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
//@RestController
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jWTService;
    @Autowired
    private Cloudinary cloud;
    @Autowired
    private SpecializationService specialService;
    @Autowired
    private DoctorService doctorService;
//    @Autowired
//    public ApiUserController(JWTService j) {
//        this.jWTService = j;
//    }

    @RequestMapping(value = "/admin/nurse/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public void deleteNurse(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(this.userService.getUserById(id));
    }
    
    @RequestMapping(value = "/api/admin/nurse/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<String> deleteNurseAPI(@PathVariable(value = "id") int id) {
        if(this.userService.deleteUser(this.userService.getUserById(id)))
            return new ResponseEntity<>("DELETE NURSE SUCCESS", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE NURSE FAILED", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/doctor/{id}/", method = RequestMethod.DELETE)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<String> deleteDoctorAPI(@PathVariable(value = "id") int id) {
        if(this.userService.deleteUser(this.userService.getUserById(id)))
            return new ResponseEntity<>("DELETE DOCTOR SUCCESS", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("DELETE DOCTOR FAILED", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/admin/doctor/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public void deleteDoctor(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(this.userService.getUserById(id));
    }

    
    @RequestMapping(value = "/api/login/", method = RequestMethod.POST)
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword())) {
            String token = this.jWTService.genarateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @RequestMapping(value = "/api/users/patient/", 
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addPatient(@RequestParam Map<String, String> patient,
            @RequestPart MultipartFile file) {
        User u = new User();
        u.setUserRole(User.PATIENT);
        u.setUsername(patient.get("username"));
        u.setPassword(patient.get("password"));
        u.setFullName(patient.get("fullName"));
        u.setEmail(patient.get("email"));
        u.setPhone(patient.get("phone"));
        u.setAddress(patient.get("address"));
        if (!file.isEmpty()) {
            try {
                Map m = this.cloud.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                
            }
        }
        if (this.userService.addOrUpdateUser(u))
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD USER FAILED!!!", HttpStatus.UNAUTHORIZED);
    }
    
    @RequestMapping(value = "/api/admin/users/nurse/", 
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<Object> addNurse(@RequestParam Map<String, String> nurse,
            @RequestPart MultipartFile file) {
        User u = new User();
        u.setUserRole(User.NURSE);
        u.setUsername(nurse.get("username"));
        u.setPassword(nurse.get("password"));
        u.setFullName(nurse.get("fullName"));
        u.setEmail(nurse.get("email"));
        u.setPhone(nurse.get("phone"));
        u.setAddress(nurse.get("address"));
        if (!file.isEmpty()) {
            try {
                Map m = this.cloud.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                
            }
        }
        if (this.userService.addOrUpdateUser(u))
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        return new ResponseEntity<>("ADD USER FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/users/nurse/{id}/", 
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<Object> updateNurse(@RequestParam Map<String, String> nurse,
            @PathVariable(value = "id") int id,
            @RequestPart(value = "file") MultipartFile file) {
        User u = this.userService.getUserById(id);
        u.setUsername(nurse.get("username"));
        u.setPassword(nurse.get("password"));
        u.setFullName(nurse.get("fullName"));
        u.setEmail(nurse.get("email"));
        u.setPhone(nurse.get("phone"));
        u.setAddress(nurse.get("address"));
        if (!file.isEmpty()) {
            try {
                Map m = this.cloud.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                
            }
        }
        if (this.userService.addOrUpdateUser(u))
            return new ResponseEntity<>(u, HttpStatus.ACCEPTED);
        return new ResponseEntity<>("UPDATE USER FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/users/doctor/", 
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<Object> addDoctor(@RequestParam Map<String, String> doctor,
            @RequestPart MultipartFile file) {
        User u = new User();
        u.setUserRole(User.DOCTOR);
        u.setUsername(doctor.get("username"));
        u.setPassword(doctor.get("password"));
        u.setFullName(doctor.get("fullName"));
        u.setEmail(doctor.get("email"));
        u.setPhone(doctor.get("phone"));
        u.setAddress(doctor.get("address"));
        Doctor d = new Doctor();
        int specialId = Integer.parseInt(doctor.get("specialId"));
        d.setSpecializationId(this.specialService.getSpecializationById(specialId));
        if (!file.isEmpty()) {
            try {
                Map m = this.cloud.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                
            }
        }
        if (this.userService.addOrUpdateUser(u)) {
            d.setUserId(this.userService.getUserById(u.getId()));
            this.doctorService.addOrUpdateDoctor(d);
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        }
            
        return new ResponseEntity<>("ADD USER FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/api/admin/users/doctor/{id}/", 
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<Object> updateDoctor(@RequestParam Map<String, String> doctor,
            @PathVariable(value = "id") int id,
            @RequestPart MultipartFile file) {
        User u = this.userService.getUserById(id);
        u.setUsername(doctor.get("username"));
        u.setPassword(doctor.get("password"));
        u.setFullName(doctor.get("fullName"));
        u.setEmail(doctor.get("email"));
        u.setPhone(doctor.get("phone"));
        u.setAddress(doctor.get("address"));
        Doctor d = this.doctorService.getDoctorById(id);
        int specialId = Integer.parseInt(doctor.get("specialId"));
        d.setSpecializationId(this.specialService.getSpecializationById(specialId));
        if (!file.isEmpty()) {
            try {
                Map m = this.cloud.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) m.get("secure_url"));
            } catch (IOException ex) {
                
            }
        }
        if (this.userService.addOrUpdateUser(u)) {
            d.setUserId(this.userService.getUserById(u.getId()));
            this.doctorService.addOrUpdateDoctor(d);
            return new ResponseEntity<>(u, HttpStatus.ACCEPTED);
        }
            
        return new ResponseEntity<>("UPDATE USER FAILED!!!", HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin(origins = {"http://localhost:3000/"})
    @RequestMapping(value = "/api/admin/users/patients/", produces = {MediaType.APPLICATION_JSON_VALUE}) 
    public ResponseEntity<List<Object[]>> getPatients(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(this.userService
                    .getUserByUserRoleAndName(User.PATIENT, name), HttpStatus.OK);
        } 
        return new ResponseEntity<>(this.userService.getUserByUserRole(User.PATIENT),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/nurses/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<List<Object[]>> getNurses(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(this.userService
                    .getUserByUserRoleAndName(User.NURSE, name), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.userService.getUserByUserRole(User.NURSE),
        HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/doctors/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000/"})
    public ResponseEntity<List<Object[]>> getDoctors(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(this.userService
                    .getUserByUserRoleAndName(User.DOCTOR, name), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.userService.getUserByUserRole(User.DOCTOR),
        HttpStatus.OK);
    }
    
    @CrossOrigin(origins = {"http://localhost:3000/"})
    @RequestMapping(value = "/api/current-user/", produces = {MediaType.APPLICATION_JSON_VALUE})
    
    public ResponseEntity<User> currentUser(Principal user) {
        User u = this.userService.getCurrentUser(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
    
    @CrossOrigin(origins = {"http://localhost:3000/"})
    @RequestMapping(value = "/api/user/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.userService.getUserById(id),
        HttpStatus.OK);
    }
    
    @CrossOrigin(origins = {"http://localhost:3000/"})
    @RequestMapping(value = "/api/user/doctor/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Doctor> getDoctorById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.doctorService.getDoctorById(id),
        HttpStatus.OK);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/api/users/employees/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getEmployees() {
        return new ResponseEntity<>(this.userService.getEmployee(),
        HttpStatus.OK);
    }
}
