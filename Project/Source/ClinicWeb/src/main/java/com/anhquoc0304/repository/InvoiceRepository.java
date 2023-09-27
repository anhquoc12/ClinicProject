/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.repository;

import com.anhquoc0304.pojo.Invoice;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface InvoiceRepository {
    List<Object[]> getInvoices();
    Invoice getInvoiceById(int id);
    boolean createInvoiceBeforePay(Invoice i);
    boolean payment(Invoice i);
}
