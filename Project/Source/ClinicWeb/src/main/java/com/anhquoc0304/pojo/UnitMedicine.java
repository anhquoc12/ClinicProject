/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import com.anhquoc0304.validations.UnitMedicineName;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "unit_medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnitMedicine.findAll", query = "SELECT u FROM UnitMedicine u"),
    @NamedQuery(name = "UnitMedicine.findById", query = "SELECT u FROM UnitMedicine u WHERE u.id = :id"),
    @NamedQuery(name = "UnitMedicine.findByName", query = "SELECT u FROM UnitMedicine u WHERE u.name = :name")})
public class UnitMedicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    @NotEmpty(message = "{unitMedicine.name.notEmptyMsg}")
    @UnitMedicineName(message = "{unitMedicine.name.uniqueMsg}")
    private String name;
    @OneToMany(mappedBy = "unitMedicineId")
    @JsonIgnore
    private Set<Medicine> medicineSet;

    public UnitMedicine() {
    }

    public UnitMedicine(Integer id) {
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

    @XmlTransient
    public Set<Medicine> getMedicineSet() {
        return medicineSet;
    }

    public void setMedicineSet(Set<Medicine> medicineSet) {
        this.medicineSet = medicineSet;
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
        if (!(object instanceof UnitMedicine)) {
            return false;
        }
        UnitMedicine other = (UnitMedicine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.UnitMedicine[ id=" + id + " ]";
    }
    
}
