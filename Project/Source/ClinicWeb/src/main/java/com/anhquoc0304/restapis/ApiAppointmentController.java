/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.restapis;

import com.anhquoc0304.dto.Message;
import com.anhquoc0304.pojo.Appointment;
import com.anhquoc0304.pojo.Doctor;
import com.anhquoc0304.pojo.Specialization;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.service.AppointmentService;
import com.anhquoc0304.service.DoctorService;
import com.anhquoc0304.service.EmailService;
import com.anhquoc0304.service.SpecializationService;
import com.anhquoc0304.service.UserService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SpecializationService specService;

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
        List<Appointment> listAppointment
                = this.appointmentService.getAppointmentByCurrentUser(currentUser);
        return new ResponseEntity<>(listAppointment, HttpStatus.OK);
    }

    @PostMapping("/api/appointment/cancle/")
    public ResponseEntity<Message> cancleAppointment(@RequestBody Appointment a) {
        Message message = new Message();
        if (this.appointmentService.setAppointmentStatus(a, Appointment.CANCLED)) {
            message.setMessage("Huỷ lịch thành công.");
            message.setData(a);
        } else {
            message.setMessage("Có Lỗi xảy ra. Vui Lòng thử lại");
            message.setData(null);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(path = "/api/nurse/list-appoitment/confirm/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Appointment>> listAppointmentToConfirm() {
        return new ResponseEntity<>(
                this.appointmentService
                        .getAppointmentByStatus(Appointment.WAITTING),
                HttpStatus.OK);
    }

    @PostMapping(path = "/api/nurse/appointment/confirm/")
    public ResponseEntity<Message> confirmedAppointment(@RequestBody Appointment a, Principal p) {
        a.setNurseId(this.userService.getCurrentUser(p.getName()));
        Message message = new Message();
        if (this.appointmentService.setAppointmentStatus(a, Appointment.CONFIRMED)) {
            // Gửi mail
            User patient = a.getPatientId();
            User nurse = a.getNurseId();
            String emailTo = patient.getEmail();
            String subject = "Xác nhận lịch hẹn khám bệnh";
            List<String> paras = new ArrayList<>();
            paras.add(String.format("Chào Ông/Bà %s\n\n", patient.getFullName()));
            paras.add("Chúng tôi xin thông báo rằng lịch hẹn của bạn đã được xác nhận thành công. Dưới đây là thông tin chi tiết về lịch hẹn:\n\n");
            paras.add("+ Thời gian: " + new SimpleDateFormat("dd/MM/yyyy").format(a.getAppointmentDate()) + "\n\n");
            paras.add(String.format("+ Y tá xác nhận: %s\n\n", nurse.getFullName()));
            paras.add(String.format("+ Số điện thoại liên hệ: %s\n\n", nurse.getPhone()));
            paras.add(String.format("+ Email liên hệ: %s\n\n", nurse.getEmail()));
            paras.add("Nếu bạn có bất kỳ câu hỏi hoặc cần điều chỉnh lịch hẹn, vui lòng liên hệ với chúng tôi qua số điện thoại hoặc địa chỉ email trên để được hỗ trợ.\n\n");
            paras.add("Chúng tôi trân trọng mong đợi sự gặp gỡ của bạn và sẽ làm hết sức để đảm bảo mọi thứ diễn ra suôn sẻ.\n\n");
            paras.add("Trân trọng");
            String content = "";
            for (String para : paras) {
                content += para;
            }
            Map<String, String> params = new HashMap<>();
            params.put("emailTo", emailTo);
            params.put("subject", subject);
            params.put("content", content);
            this.emailService.sendEmail(params);
            message.setMessage("Xác nhận thành công.");
            message.setData(a);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        message.setMessage("Có lỗi xảy ra.");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(path = "/api/nurse/list-appoitment/present/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Appointment>> listAppointmentToday() {
        return new ResponseEntity<>(
                this.appointmentService
                        .getAppointmentToday(),
                HttpStatus.OK);
    }
    
    @GetMapping(path = "/api/doctor/list-patients/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Appointment>> listPatientAppointment(Principal p) {
        return new ResponseEntity<>(
                this.appointmentService
                        .getAppointmentByStatus(Appointment.PRESENT),
        HttpStatus.OK);
    }
    
    @PostMapping(path = "/api/nurse/appointment/present/")
    public ResponseEntity<Message> presentAppointment(@RequestBody Appointment a, Principal p) {
//        a.setNurseId(this.userService.getCurrentUser(p.getName()));
        Message message = new Message();
        if (this.appointmentService.setAppointmentStatus(a, Appointment.PRESENT)) {
            
            message.setMessage("Xác nhận có mặt.");
            message.setData(a);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        message.setMessage("Có lỗi xảy ra.");
        message.setData(null);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
