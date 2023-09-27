/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Admin
 */
@Controller
public class IndexController {
    
    @GetMapping(value = "/")
    @ResponseBody    
    public String index(Model model) {
        return "Hello from Spring API! The server runs successfully!!!";
    }
}
