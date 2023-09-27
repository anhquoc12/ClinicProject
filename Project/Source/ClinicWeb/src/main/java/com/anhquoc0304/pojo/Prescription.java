/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "prescription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
    @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :id"),
    @NamedQuery(name = "Prescription.findByDosage", query = "SELECT p FROM Prescription p WHERE p.dosage = :dosage"),
    @NamedQuery(name = "Prescription.findByFrequency", query = "SELECT p FROM Prescription p WHERE p.frequency = :frequency"),
    @NamedQuery(name = "Prescription.findByDuration", query = "SELECT p FROM Prescription p WHERE p.duration = :duration"),
    @NamedQuery(name = "Prescription.findByTotalUnit", query = "SELECT p FROM Prescription p WHERE p.totalUnit = :totalUnit")})
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "dosage")
    private String dosage;
    @Size(max = 50)
    @Column(name = "frequency")
    private String frequency;
    @Size(max = 50)
    @Column(name = "duration")
    private String duration;
    @Column(name = "total_unit")
    private Integer totalUnit;
    @JoinColumn(name = "medical_record_id", referencedColumnName = "id")
    @ManyToOne
    private MedicalRecord medicalRecordId;
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    @ManyToOne
    private Medicine medicineId;

    public Prescription() {
    }

    public Prescription(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(Integer totalUnit) {
        this.totalUnit = totalUnit;
    }

    public MedicalRecord getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(MedicalRecord medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public Medicine getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Medicine medicineId) {
        this.medicineId = medicineId;
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
        if (!(object instanceof Prescription)) {
            return false;
        }
        Prescription other = (Prescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.Prescription[ id=" + id + " ]";
    }
    
}
