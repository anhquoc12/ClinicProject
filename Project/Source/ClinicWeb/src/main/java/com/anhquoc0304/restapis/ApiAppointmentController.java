/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.AppointmentService;
import com.anhquoc0304.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
public class ApiAppointmentController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/api/appointment/register/")
    public ResponseEntity<Message> registerAppointment(@RequestBody Appointment appointment,
            Principal user) {
        User currentUser = this.userService.getCurrentUser(user.getName());
        appointment.setPatientId(currentUser);
        Message message = new Message();

        if (!this.appointmentService.countAppointment(appointment.getAppointmentDate())) {
            message.setMessage("Ngày ngày đã đủ lịch khám vui lòng chọn 1 ngày khác!!!");
            message.setData(null);
            return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
        }

        if (this.appointmentService.addAppointment(appointment)) {
            message.setMessage("Đăng Ký thành công. Vui Lòng chờ xác nhận.!!!");
            message.setData(appointment);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        message.setMessage("Có lỗi nào đó đã xảy ra. Vui lòng đăng ký lại.!!!");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/api/appointment/lists/")
    public ResponseEntity<List<Appointment>> getAppointmentsByCurrentUser(Principal user) {
        User currentUser = this.userService.getCurrentUser(user.getName());
        List<Appointment> listAppointment = 
                this.appointmentService.getAppointmentByCurrentUser(currentUser);
        return new ResponseEntity<>(listAppointment, HttpStatus.OK);
    }
    
    @PostMapping("/api/appointment/cancle/")
    public ResponseEntity<Message> cancleAppointment(@RequestBody Appointment a) {
        Message message = new Message();
        if(this.appointmentService.setAppointmentStatus(a, Appointment.CANCLED)) {
            message.setMessage("Huỷ lịch thành công.");
            message.setData(a);
        }
        else {
            message.setMessage("Có Lỗi xảy ra. Vui Lòng thử lại");
            message.setData(null);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
