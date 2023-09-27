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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByCreateDate", query = "SELECT i FROM Invoice i WHERE i.createDate = :createDate"),
    @NamedQuery(name = "Invoice.findByPaymentStatus", query = "SELECT i FROM Invoice i WHERE i.paymentStatus = :paymentStatus")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String PENDING = "PENDING";
    public static final String ACCEPTED = "ACCEPTED";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @JoinColumn(name = "medical_record_id", referencedColumnName = "id")
    @ManyToOne
    private MedicalRecord medicalRecordId;
    @JoinColumn(name = "nurse_id", referencedColumnName = "id")
    @ManyToOne
    private User nurseId;

    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public MedicalRecord getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(MedicalRecord medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public User getNurseId() {
        return nurseId;
    }

    public void setNurseId(User nurseId) {
        this.nurseId = nurseId;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.Invoice[ id=" + id + " ]";
    }
    
}
