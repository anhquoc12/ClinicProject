/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import com.anhquoc0304.validations.Username;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.Fetch;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByUserRole", query = "SELECT u FROM User u WHERE u.userRole = :userRole"),
    @NamedQuery(name = "User.findByAvatar", query = "SELECT u FROM User u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "User.findByFullName", query = "SELECT u FROM User u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "User.findByAddress", query = "SELECT u FROM User u WHERE u.address = :address"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone")})

@Username(message = "{user.username.uniqueMsg}")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ADMIN = "ADMIN";
    public static final String DOCTOR = "DOCTOR";
    public static final String NURSE = "NURSE";
    public static final String PATIENT = "PATIENT";
    public static final String OWNER = "OWNER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100, message = "{user.name.MaxMsg}")
    @Column(name = "username")
    @NotEmpty(message = "{user.name.notEmptyMsg}")
    private String username;
    @Size(max = 100, min = 8, message = "{user.password.minMsg}")
    @Column(name = "password")
    @NotEmpty(message = "{user.password.notEmptyMsg}")
//    @Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\/\\\\-]).*$/",
//            message = "{user.password.patternMsg}")
    private String password;
    @Size(max = 7, message = "lỗi userrole")
    @Column(name = "user_role")
    private String userRole;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @Size(max = 200, message = "")
    @Column(name = "full_name")
    @NotEmpty(message = "{user.fullName.notEmptyMsg}")
    private String fullName;
    @Size(max = 255)
    @Column(name = "address")
    @NotEmpty(message = "{user.address.notEmptyMsg}")
    private String address;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    @NotEmpty(message = "{user.email.notEmptyMsg}")
    @Email(message = "{user.email.emailMsg}")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 15)
    @Column(name = "phone")
    @NotEmpty(message = "{user.phone.notEmptyMsg}")
    private String phone;
    @OneToMany(mappedBy = "userId")
    @JsonIgnore
    private Set<Doctor> doctorSet;
    @OneToMany(mappedBy = "userId")
    @JsonIgnore
    private Set<Schedule> scheduleSet;
    @OneToMany(mappedBy = "patientId")
    @JsonIgnore
    private Set<MedicalRecord> medicalRecordSet;
    @OneToMany(mappedBy = "doctorId")
    @JsonIgnore
    private Set<MedicalRecord> medicalRecordSet1;
    @OneToMany(mappedBy = "nurseId")
    @JsonIgnore
    private Set<Appointment> appointmentSet;
    @OneToMany(mappedBy = "patientId")
    @JsonIgnore
    private Set<Appointment> appointmentSet1;
    @OneToMany(mappedBy = "nurseId")
    @JsonIgnore
    private Set<Invoice> invoiceSet;

    @Transient
    @JsonIgnore
    private MultipartFile file;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Set<Doctor> getDoctorSet() {
        return doctorSet;
    }

    public void setDoctorSet(Set<Doctor> doctorSet) {
        this.doctorSet = doctorSet;
    }

    @XmlTransient
    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

    @XmlTransient
    public Set<MedicalRecord> getMedicalRecordSet() {
        return medicalRecordSet;
    }

    public void setMedicalRecordSet(Set<MedicalRecord> medicalRecordSet) {
        this.medicalRecordSet = medicalRecordSet;
    }

    @XmlTransient
    public Set<MedicalRecord> getMedicalRecordSet1() {
        return medicalRecordSet1;
    }

    public void setMedicalRecordSet1(Set<MedicalRecord> medicalRecordSet1) {
        this.medicalRecordSet1 = medicalRecordSet1;
    }

    @XmlTransient
    public Set<Appointment> getAppointmentSet() {
        return appointmentSet;
    }

    public void setAppointmentSet(Set<Appointment> appointmentSet) {
        this.appointmentSet = appointmentSet;
    }

    @XmlTransient
    public Set<Appointment> getAppointmentSet1() {
        return appointmentSet1;
    }

    public void setAppointmentSet1(Set<Appointment> appointmentSet1) {
        this.appointmentSet1 = appointmentSet1;
    }

    @XmlTransient
    public Set<Invoice> getInvoiceSet() {
        return invoiceSet;
    }

    public void setInvoiceSet(Set<Invoice> invoiceSet) {
        this.invoiceSet = invoiceSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.User[ id=" + id + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
