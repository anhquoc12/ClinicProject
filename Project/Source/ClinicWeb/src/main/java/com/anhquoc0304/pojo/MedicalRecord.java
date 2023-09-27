/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "medical_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicalRecord.findAll", query = "SELECT m FROM MedicalRecord m"),
    @NamedQuery(name = "MedicalRecord.findById", query = "SELECT m FROM MedicalRecord m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalRecord.findByExaminationFee", query = "SELECT m FROM MedicalRecord m WHERE m.examinationFee = :examinationFee")})
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "symptom")
    @NotEmpty(message = "{medicalRecord.symptom.notEmpty}")
    private String symptom;
    @Lob
    @Size(max = 65535)
    @Column(name = "conclusion")
    @NotEmpty(message = "{medicalRecord.conclusion.notEmpty}")
    private String conclusion;
    @Lob
    @Size(max = 65535)
    @Column(name = "advice")
    private String advice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "examination_fee")
    @Positive(message = "{medicalRecord.examinationFee.positive}")
    @NotNull(message = "{medicalRecord.examinationFee.notNull}")
    @Min(value = 20000, message = "{medicalRecord.examinationFee.minValue}")
    private BigDecimal examinationFee;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private User patientId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    private User doctorId;
    @OneToMany(mappedBy = "medicalRecordId")
    @JsonIgnore
    private Set<Prescription> prescriptionSet;
    @OneToMany(mappedBy = "medicalRecordId")
    @JsonIgnore
    private Set<Invoice> invoiceSet;

    public MedicalRecord() {
    }

    public MedicalRecord(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public BigDecimal getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(BigDecimal examinationFee) {
        this.examinationFee = examinationFee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getPatientId() {
        return patientId;
    }

    public void setPatientId(User patientId) {
        this.patientId = patientId;
    }

    public User getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(User doctorId) {
        this.doctorId = doctorId;
    }

    @XmlTransient
    public Set<Prescription> getPrescriptionSet() {
        return prescriptionSet;
    }

    public void setPrescriptionSet(Set<Prescription> prescriptionSet) {
        this.prescriptionSet = prescriptionSet;
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
        if (!(object instanceof MedicalRecord)) {
            return false;
        }
        MedicalRecord other = (MedicalRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.MedicalRecord[ id=" + id + " ]";
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
