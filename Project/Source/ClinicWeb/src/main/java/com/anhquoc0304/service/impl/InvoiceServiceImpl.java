/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.repository.InvoiceRepository;
import com.anhquoc0304.service.InvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public boolean createInvoiceBeforePay(Invoice i) {
        return this.invoiceRepository.createInvoiceBeforePay(i);
    }

    @Override
    public List<Object[]> getInvoices() {
        return this.invoiceRepository.getInvoices();
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return this.invoiceRepository.getInvoiceById(id);
    }

    @Override
    public boolean payment(Invoice i) {
        return this.invoiceRepository.payment(i);
    }
    
}
