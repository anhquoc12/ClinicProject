/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.Prescription;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.InvoiceService;
import com.anhquoc0304.service.MedicalRecordService;
import com.anhquoc0304.service.MedicineService;
import com.anhquoc0304.service.PrescriptionService;
import com.anhquoc0304.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private PrescriptionService prescriptionService;

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
            Invoice i = new Invoice();
            i.setCreateDate(new Date());
            i.setMedicalRecordId(medicalRecord);
            if (this.invoiceService.createInvoiceBeforePay(i)) {
                message.setMessage("Thêm toa thuốc");
                message.setData(medicalRecord);
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            }
        }
        message.setMessage("Kiểm tra thông tin nhập");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/api/doctor/medical/prescription/{id}/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> addPrescription(HttpServletRequest servlet,
            @PathVariable(value = "id") int medicalId) {
        try {
            BufferedReader reader = servlet.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonNode node = new ObjectMapper().readTree(sb.toString());
            List<Prescription> prescriptions = new ArrayList<>();
            for (int i = 0; i < node.size(); i++) {
                Prescription p = new Prescription();
                p.setMedicalRecordId(this.medicalService.getMedicalRecordById(medicalId));
                p.setDosage(node.get(i).get("dosage").asText());
                p.setFrequency(node.get(i).get("frequency").asText());
                p.setTotalUnit(node.get(i).get("totalUnit").asInt());
                p.setDuration(node.get(i).get("duration").asText());
                p.setMedicineId(this.medicineService.getMedicineById(node.get(i).get("medicineId").asInt()));
                prescriptions.add(p);
            }
            if (this.prescriptionService.saveToDatabasePrescription(prescriptions)) {
                Message message = new Message();
                message.setMessage("Hoàn thành khám bệnh.");
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Message message = new Message();
        message.setMessage("Có lỗi xảy ra.");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping(path = "/api/doctor/history/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalRecord>> getHistories(@RequestParam(value = "date",
            required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam(value = "patient", required = false) 
                    String patient) {
        int patientId = 0;
        if (patient != null) patientId = Integer.parseInt(patient);
        if(date != null)
            return new ResponseEntity<>(this.medicalService.getMedicals(date, patientId),
            HttpStatus.OK);
        else
            return new ResponseEntity<>(this.medicalService.getMedicals(null, patientId),
            HttpStatus.OK);
    }
    
    @GetMapping("/api/doctor/history/{id}/")
    public ResponseEntity<MedicalRecord> getDetailMedical(@PathVariable(value = "id")
    int id) {
        return new ResponseEntity<>(this.medicalService.getMedicalRecordById(id),
        HttpStatus.OK);
    }
    
    @GetMapping("/api/doctor/prescription/{id}/")
    public ResponseEntity<List<Prescription>> getPrescriptionByMedical(@PathVariable(value = "id")
    int id) {
        return new ResponseEntity<>(this.prescriptionService.getPrescriptionByMedicalRecord(id),
        HttpStatus.OK);
    }
}
