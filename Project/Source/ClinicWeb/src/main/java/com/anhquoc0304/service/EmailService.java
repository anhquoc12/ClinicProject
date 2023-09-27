/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.anhquoc0304.service;

import java.util.Map;

/**
 *
 * @author Admin
 */
public interface EmailService {
    boolean sendEmail(Map<String, String> params);
}
