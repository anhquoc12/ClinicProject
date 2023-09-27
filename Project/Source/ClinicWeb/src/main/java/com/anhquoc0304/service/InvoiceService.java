/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.MedicalRecord;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface InvoiceService {
    List<Object[]> getInvoices();
    boolean createInvoiceBeforePay(Invoice i);
    Invoice getInvoiceById(int id);
    boolean payment(Invoice i);
}
