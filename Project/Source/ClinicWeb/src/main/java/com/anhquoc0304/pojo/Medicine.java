/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import com.anhquoc0304.validations.MedicineName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m"),
    @NamedQuery(name = "Medicine.findById", query = "SELECT m FROM Medicine m WHERE m.id = :id"),
    @NamedQuery(name = "Medicine.findByName", query = "SELECT m FROM Medicine m WHERE m.name = :name"),
    @NamedQuery(name = "Medicine.findByUnitPrice", query = "SELECT m FROM Medicine m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "Medicine.findByUnitInStock", query = "SELECT m FROM Medicine m WHERE m.unitInStock = :unitInStock")})
@MedicineName(message = "{medicine.name.uniqueMsg}")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255, message = "{medicine.name.sizeMsg}")
    @Column(name = "name")
    @NotEmpty(message = "{medicine.name.notEmptyMsg}")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unit_price")
    @NotNull(message = "{medicine.unitPrice.notNullMsg}")
    @Min(value = 1, message = "{medicine.unitPrice.minMsg}")
    private BigDecimal unitPrice;
    @Column(name = "unit_in_stock")
    @NotNull(message = "{medicine.unitInStock.notNullMsg}")
    @Min(value = 1, message = "{medicine.unitInStock.minMsg}")
    private Integer unitInStock;
    @OneToMany(mappedBy = "medicineId")
    @JsonIgnore
    private Set<Prescription> prescriptionSet;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
//    @JsonIgnore
    private Category categoryId;
    @JoinColumn(name = "unit_medicine_id", referencedColumnName = "id")
    @ManyToOne
//    @JsonIgnore
    private UnitMedicine unitMedicineId;

    public Medicine() {
    }

    public Medicine(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(Integer unitInStock) {
        this.unitInStock = unitInStock;
    }

    @XmlTransient
    public Set<Prescription> getPrescriptionSet() {
        return prescriptionSet;
    }

    public void setPrescriptionSet(Set<Prescription> prescriptionSet) {
        this.prescriptionSet = prescriptionSet;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public UnitMedicine getUnitMedicineId() {
        return unitMedicineId;
    }

    public void setUnitMedicineId(UnitMedicine unitMedicineId) {
        this.unitMedicineId = unitMedicineId;
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
        if (!(object instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.Medicine[ id=" + id + " ]";
    }
    
}
