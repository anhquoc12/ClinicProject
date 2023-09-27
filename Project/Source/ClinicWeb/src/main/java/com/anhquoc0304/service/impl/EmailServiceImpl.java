/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.service.impl;

import com.anhquoc0304.service.EmailService;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@PropertySource("classpath:configs.properties")
public class EmailServiceImpl implements EmailService {
    @Autowired
    private Environment env;
    @Autowired
    private Session javaMail;

    @Override
    public boolean sendEmail(Map<String, String> params) {
        try {
            MimeMessage message = new MimeMessage(javaMail);
            message.setFrom(new InternetAddress(env.getProperty("mail.email")));
            message.setRecipient(Message.RecipientType.TO, 
                    new InternetAddress(params.get("emailTo")));
            message.setSubject(params.get("subject"), "UTF-8");
            message.setText(params.get("content"), "UTF-8");
            Transport.send(message);
            return true;
        } catch (SendFailedException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
