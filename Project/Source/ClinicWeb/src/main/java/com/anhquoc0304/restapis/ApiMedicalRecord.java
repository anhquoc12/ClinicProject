/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.MedicalRecordService;
import com.anhquoc0304.service.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import javax.validation.Valid;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiMedicalRecord {

    @Autowired
    private UserService userService;
    @Autowired
    private MedicalRecordService medicalService;

    @PostMapping("/api/doctor/medical/add/")
    public ResponseEntity<Message> addMedical(Principal p,
            @RequestParam Map<String, String> medical) {
        User doctor = this.userService.getCurrentUser(p.getName());
        int id = 0;
        try {
            id = Integer.parseInt(medical.get("patientId"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        User patient = this.userService.getUserById(id);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAdvice(medical.get("advice"));
        medicalRecord.setConclusion(medical.get("conclusion"));
        medicalRecord.setPatientId(patient);
        medicalRecord.setSymptom(medical.get("symptom"));
        medicalRecord.setExaminationFee(new BigDecimal(medical.get("examinationFee")));
        medicalRecord.setNote(medical.get("note"));
        medicalRecord.setDoctorId(doctor);
        medicalRecord.setCreatedDate(new Date());
        Message message = new Message();
        if (this.medicalService.addMedicalRecord(medicalRecord)) {
            message.setMessage("Thêm toa thuốc");
            message.setData(medicalRecord);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        message.setMessage("Kiểm tra thông tin nhập");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
