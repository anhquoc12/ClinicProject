/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.InvoiceService;
import com.anhquoc0304.service.MedicalRecordService;
import com.anhquoc0304.service.PrescriptionService;
import com.anhquoc0304.service.UserService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Admin
 */
@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private MedicalRecordService medicalSevice;

    @RequestMapping("/nurse/invoices")
    public String listInvoices(Model model) {
        model.addAttribute("invoices", this.invoiceService.getInvoices());
        return "invoices";
    }

    @RequestMapping("/nurse/invoices/{id}")
    public String detailInvoice(@PathVariable(value = "id") int invoiceId, Model model) {
        Invoice i = this.invoiceService.getInvoiceById(invoiceId);
        int examinationFee = this.medicalSevice.getMedicalRecordById(i.getMedicalRecordId().getId()).getExaminationFee().intValue();
        
        User nurse = this.userService.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
        int prescriptionFee =  this.prescriptionService.totalMedicine(this.medicalSevice.getMedicalRecordById(i.getMedicalRecordId().getId())).intValue();
        model.addAttribute("medicines", this.prescriptionService.getPrescirptionForDetailInvoice(i.getMedicalRecordId()));
        model.addAttribute("nurse", nurse);
        model.addAttribute("invoice", i);
        model.addAttribute("feeMedical", examinationFee);
        model.addAttribute("feePrescription", prescriptionFee);
        return "payment";
    }
    
    @RequestMapping(value = "/nurse/invoices/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> payment(@PathVariable(value = "id") int invoiceId) {
        Invoice i = this.invoiceService.getInvoiceById(invoiceId);
        i.setPaymentStatus(Invoice.ACCEPTED);
        User nurse = this.userService.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
        i.setNurseId(nurse);
        if (this.invoiceService.payment(i))
            return ResponseEntity.ok("SUCCESS");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra");
    }
}
