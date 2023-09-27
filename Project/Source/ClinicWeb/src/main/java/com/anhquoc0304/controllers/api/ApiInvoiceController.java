/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers.api;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.InvoiceService;
import com.anhquoc0304.service.MedicalRecordService;
import com.anhquoc0304.service.PrescriptionService;
import com.anhquoc0304.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
public class ApiInvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private MedicalRecordService medicalSevice;
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/api/nurse/invoices/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<List<Object[]>> invoices() {
        return new ResponseEntity<>(this.invoiceService.getInvoices(),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/nurse/invoices/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<Invoice> detailInvoice(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.invoiceService.getInvoiceById(id),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/nurse/invoices/prescription/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<List<Object[]>> prescriptionsByInvoiceId(@PathVariable(value = "id") int id) {
        Invoice i = this.invoiceService.getInvoiceById(id);
        return new ResponseEntity<>(this.prescriptionService.getPrescirptionForDetailInvoice(i.getMedicalRecordId()),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/nurse/invoices/fee/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<int[]> totalFeeByInvoiceId(@PathVariable(value = "id") int id) {
        Invoice i = this.invoiceService.getInvoiceById(id);
        int examinationFee = this.medicalSevice.getMedicalRecordById(i.getMedicalRecordId().getId()).getExaminationFee().intValue();
        int prescriptionFee =  this.prescriptionService.totalMedicine(this.medicalSevice.getMedicalRecordById(i.getMedicalRecordId().getId())).intValue();
        return new ResponseEntity<>(new int[]{examinationFee, prescriptionFee},
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/nurse/invoices/payment/{id}/", method = RequestMethod.POST)
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<String> payment(@PathVariable(value = "id") int invoiceId,
            Principal user) {
        Invoice i = this.invoiceService.getInvoiceById(invoiceId);
        i.setPaymentStatus(Invoice.ACCEPTED);
        User nurse = this.userService.getCurrentUser(user.getName());
        i.setNurseId(nurse);
        if (this.invoiceService.payment(i))
            return ResponseEntity.ok("SUCCESS");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra");
    }
}
