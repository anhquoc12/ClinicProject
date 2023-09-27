/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Prescription;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.AppointmentService;
import com.anhquoc0304.service.InvoiceService;
import com.anhquoc0304.service.MedicalRecordService;
import com.anhquoc0304.service.MedicineService;
import com.anhquoc0304.service.PrescriptionService;
import com.anhquoc0304.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class MedicalRecordController {

    @Autowired
    private UserService UserService;
    @Autowired
    private MedicalRecordService medicalService;
    @Autowired
    private AppointmentService appointService;
    @Autowired
    private InvoiceService InvoiceService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private PrescriptionService prescriptionService;
    private int medicalId;

    @RequestMapping("/doctor/medical")
    public String medicalRecord(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        model.addAttribute("patients", this.UserService.getPatientByAppointmentToday());
        return "medical";
    }

    @RequestMapping(value = "/doctor/medical", method = RequestMethod.POST)
    public String addMedicalRecord(@RequestParam(value = "patient") int patient, Model model, @ModelAttribute(value = "medicalRecord")
            @Valid MedicalRecord m, BindingResult br, HttpServletRequest servlet) {
        if (!br.hasErrors()) {
            User u = this.UserService.getUserById(patient);
            m.setPatientId(u);
            m.setCreatedDate(new Date());
            m.setDoctorId(this.UserService
                    .getCurrentUser(SecurityContextHolder.getContext()
                            .getAuthentication().getName()));
            if (this.medicalService.addMedicalRecord(m)) {
                Appointment a = this.appointService.getAppointmentByPatientId(u);
                if (this.appointService.setAppointmentStatus(a, Appointment.FINISHED)) {
                    Invoice i = new Invoice();
                    i.setCreateDate(new Date());
                    i.setMedicalRecordId(m);
                    i.setPaymentStatus(Invoice.PENDING);
                    if (this.InvoiceService.createInvoiceBeforePay(i)) {
                        return String.format("redirect:/doctor/prescription/%d", m.getId());
                    } else {
                        model.addAttribute("msg", "Có lỗi xảy ra");
                    }
                } else {
                    model.addAttribute("msg", "Có lỗi xảy ra");
                }
            } else {
                model.addAttribute("msg", "Có lỗi xảy ra");
            }
        }
        model.addAttribute("patients", this.UserService.getPatientByAppointmentToday());
        return "medical";
    }

    @RequestMapping("/doctor/prescription/{id}")
    public String prescription(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("medicines", this.medicineService.getMedicineByName(null));
        MedicalRecord m = this.medicalService.getMedicalRecordById(id);
        model.addAttribute("medical", m);
        medicalId = m.getId();
        User doctor = this.UserService.getCurrentUser(SecurityContextHolder.getContext()
        .getAuthentication().getName());
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("doctorName", doctor.getFullName());
        jsonData.put("doctorAddress", doctor.getAddress());
        jsonData.put("doctorPhone", doctor.getPhone());
        User patient = m.getPatientId();
        jsonData.put("patientName", patient.getFullName());
        jsonData.put("patientAddress", patient.getAddress());
        jsonData.put("patientPhone", patient.getPhone());
        jsonData.put("advice", m.getAdvice() == null ? "none" : m.getAdvice());
        jsonData.put("file", String.format("prescription-%d", patient.getId()));
        model.addAttribute("dataServer", jsonData);
        return "prescription";
    }

    @RequestMapping(value = "/doctor/prescription/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> success(@RequestBody String json) {
        List<Prescription> prescriptions = new ArrayList<>();
        try {
            JsonNode node = new ObjectMapper().readTree(json);
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
                return ResponseEntity.ok("success");
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MedicalRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xảy ra lỗi xui lòng thử lại");
    }
    
    @RequestMapping("/doctor/history")
    public String history(Model model) {
        List<MedicalRecord> medicals = this.medicalService.getMedicals(null);
        Set<User> patientList = new HashSet<>();
        for (MedicalRecord medical : medicals) {
            patientList.add(medical.getPatientId());
        }
        model.addAttribute("medicals", medicals);
        model.addAttribute("patients", patientList);
        model.addAttribute("date", LocalDate.now());
        return "history";
    }
    
    @RequestMapping("/doctor/history/search")
    public String filterHistoryByDate(Model model, @RequestParam(name = "date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        LocalDate local = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<MedicalRecord> medicals = this.medicalService.getMedicals(date);
        Set<User> patientList = new HashSet<>();
        for (MedicalRecord medical : medicals) {
            patientList.add(medical.getPatientId());
        }
        model.addAttribute("medicals", medicals);
        model.addAttribute("patients", patientList);
        model.addAttribute("date", local);
        return "history";
    }
    
    @RequestMapping("/doctor/history/{id}")
    public String detailHistory(Model model, @PathVariable(value = "id") int id) {
        MedicalRecord medical = this.medicalService.getMedicalRecordById(id);
        List<Prescription> prescriptions = this.prescriptionService.getPrescriptionByMedicalRecord(id);
        model.addAttribute("medical", medical);
        model.addAttribute("prescriptions", prescriptions);
        return "detailHistory";
    }
}
