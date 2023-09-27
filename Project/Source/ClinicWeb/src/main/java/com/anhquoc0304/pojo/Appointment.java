/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
    @NamedQuery(name = "Appointment.findByCreatedDate", query = "SELECT a FROM Appointment a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Appointment.findByAppointmentDate", query = "SELECT a FROM Appointment a WHERE a.appointmentDate = :appointmentDate"),
    @NamedQuery(name = "Appointment.findByAppointmentStatus", query = "SELECT a FROM Appointment a WHERE a.appointmentStatus = :appointmentStatus")})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String WAITTING = "WAITTING";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String CANCLED = "CANCLED";
    public static final String PRESENT = "PRESENT";
    public static final String FINISHED = "FINISHED";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "appointment_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{appointment.appointmentDate.notNullMsg}")
    private Date appointmentDate;
    @Lob
    @Column(name = "description")
    @Size(max = 65535)
    @NotEmpty(message = "{appointment.description.notEmptyMsg}")
    private String description;
    @Size(max = 9)
    @Column(name = "appointment_status")
    private String appointmentStatus;
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    @ManyToOne
    private Specialization specializationId;
    @JoinColumn(name = "nurse_id", referencedColumnName = "id")
    @ManyToOne
    private User nurseId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private User patientId;

    public Appointment() {
    }

    public Appointment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appoinmentDate) {
        this.appointmentDate = appoinmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Specialization getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Specialization specializationId) {
        this.specializationId = specializationId;
    }

    public User getNurseId() {
        return nurseId;
    }

    public void setNurseId(User nurseId) {
        this.nurseId = nurseId;
    }

    public User getPatientId() {
        return patientId;
    }

    public void setPatientId(User patientId) {
        this.patientId = patientId;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.Appointment[ id=" + id + " ]";
    }
    
}
