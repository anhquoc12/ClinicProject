/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.repository.impl;

import com.anhquoc0304.pojo.Invoice;
import com.anhquoc0304.pojo.MedicalRecord;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.Prescription;
import com.anhquoc0304.pojo.User;
import com.anhquoc0304.repository.StatRepository;
import com.anhquoc0304.service.InvoiceService;
import com.google.common.base.Predicates;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class StatRepositoryImpl implements StatRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private InvoiceService InvoiceService;

    @Override
    public int countPatients() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM User u WHERE u.userRole =: role");
        q.setParameter("role", User.PATIENT);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public int totalRevenue() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Invoice> iRoot = query.from(Invoice.class);
        Join<Invoice, MedicalRecord> rJoin = iRoot.join("medicalRecordId");
        Join<MedicalRecord, Prescription> pJoin = rJoin.join("prescriptionSet");
        Join<Prescription, Medicine> mJoin = pJoin.join("medicineId");
        query.where(builder.equal(iRoot.get("paymentStatus"), Invoice.ACCEPTED));
        query.multiselect(builder.sum(builder.sum(builder.prod(pJoin.get("totalUnit"),
                mJoin.get("unitPrice")), rJoin.get("examinationFee"))));
        Query q = s.createQuery(query);
        return ((BigDecimal) q.getSingleResult()).intValue();
    }

    @Override
    public int countMedical() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM MedicalRecord r ");
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public List<Object[]> statRevenue(LocalDate date, int typeStat) {
        Session s = this.factory.getObject().getCurrentSession();
        int month = date.getMonthValue();
        int year = date.getYear();
        int quater = date.get(IsoFields.QUARTER_OF_YEAR);
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Invoice> invoiceRoot = cq.from(Invoice.class);
        Join<Invoice, MedicalRecord> medicalRecordJoin = invoiceRoot.join("medicalRecordId");
        Join<MedicalRecord, Prescription> prescriptionJoin = medicalRecordJoin.join("prescriptionSet");
        Join<Prescription, Medicine> medicineJoin = prescriptionJoin.join("medicineId");
        predicates.add(cb.equal(invoiceRoot.get("paymentStatus"), Invoice.ACCEPTED));
        Predicate condition = cb.and(predicates.toArray(Predicate[]::new));
//        cq.where(predicates.toArray(Predicate[]::new));
        if (typeStat == 1) {
            cq.where(predicates.toArray(Predicate[]::new));
            cq.multiselect(
                    cb.sum(
                            cb.sum(
                                    cb.prod(prescriptionJoin.get("totalUnit"), medicineJoin.get("unitPrice")),
                                    medicalRecordJoin.get("examinationFee")
                            )
                    ).alias("total"),
                    cb.function("YEAR", Integer.class, invoiceRoot.get("createDate")).alias("year")
            );
            cq.groupBy(cb.function("YEAR", Integer.class, invoiceRoot.get("createDate")), invoiceRoot.get("id"));
            cq.groupBy(cb.function("YEAR", Integer.class, invoiceRoot.get("createDate")));
            Query query = s.createQuery(cq);
            return query.getResultList();
        }
        if (typeStat == 2) {
            predicates.add(cb.equal(cb.function("YEAR", Integer.class, invoiceRoot.get("createDate")), year));
            cq.where(predicates.toArray(Predicate[]::new));
            cq.multiselect(
                    cb.sum(
                            cb.sum(
                                    cb.prod(prescriptionJoin.get("totalUnit"), medicineJoin.get("unitPrice")),
                                    medicalRecordJoin.get("examinationFee")
                            )
                    ).alias("total"),
                    cb.function("MONTH", Integer.class, invoiceRoot.get("createDate")).alias("month")
            );
            cq.groupBy(cb.function("MONTH", Integer.class, invoiceRoot.get("createDate")), invoiceRoot.get("id"));
            cq.groupBy(cb.function("MONTH", Integer.class, invoiceRoot.get("createDate")));
            Query query = s.createQuery(cq);
            return query.getResultList();
        }
        predicates.add(cb.equal(cb.function("YEAR", Integer.class, invoiceRoot.get("createDate")), year));
        cq.where(predicates.toArray(Predicate[]::new));
        cq.multiselect(
                cb.sum(
                        cb.sum(
                                cb.prod(prescriptionJoin.get("totalUnit"), medicineJoin.get("unitPrice")),
                                medicalRecordJoin.get("examinationFee")
                        )
                ).alias("total"),
                cb.function("QUARTER", Integer.class, invoiceRoot.get("createDate")).alias("quarter")
        );
        cq.groupBy(cb.function("QUARTER", Integer.class, invoiceRoot.get("createDate")), invoiceRoot.get("id"));
        cq.groupBy(cb.function("QUARTER", Integer.class, invoiceRoot.get("createDate")));
        Query query = s.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Object[]> top5MedicineStat(boolean type) {
        Session s = this.factory.getObject().getCurrentSession();
        String query = "SELECT m.name, SUM(p.totalUnit) FROM Medicine m INNER JOIN m.prescriptionSet p GROUP BY m.name ";
        if (type) {
            query += "ORDER BY SUM(p.totalUnit) DESC";
        } else {
            query += "ORDER BY SUM(p.totalUnit) ASC";
        }
        Query q = s.createQuery(query);
        q.setMaxResults(5);
        return q.getResultList();
    }

    @Override
    public List<Object[]> statAmountPatient(LocalDate date, int typeStat) {
        Session s = this.factory.getObject().getCurrentSession();
        int month = date.getMonthValue();
        int year = date.getYear();
        int quater = date.get(IsoFields.QUARTER_OF_YEAR);
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<MedicalRecord> rRoot = query.from(MedicalRecord.class);
        List<Predicate> predicates = new ArrayList<>();
        if (typeStat == 1) {
            query.multiselect(builder.count(
                    rRoot.get("id")),
                    builder.function("YEAR", Integer.class,
                            rRoot.get("createdDate")).alias("year"));
            query.groupBy(builder.function("YEAR", Integer.class,
                    rRoot.get("createdDate")));
            Query q = s.createQuery(query);
            return q.getResultList();
        }
        if (typeStat == 2) {
            query.where(builder.equal(builder.function("YEAR", Integer.class,
                    rRoot.get("createdDate")), year));

            query.multiselect(builder.count(
                    rRoot.get("id")),
                    builder.function("MONTH", Integer.class,
                            rRoot.get("createdDate")).alias("month"));
            query.groupBy(builder.function("MONTH", Integer.class,
                    rRoot.get("createdDate")));
            Query q = s.createQuery(query);
            return q.getResultList();
        }
        query.where(builder.equal(builder.function("YEAR", Integer.class,
                rRoot.get("createdDate")), year));

        query.multiselect(builder.count(
                rRoot.get("id")),
                builder.function("QUARTER", Integer.class,
                        rRoot.get("createdDate")).alias("quarter"));
        query.groupBy(builder.function("QUARTER", Integer.class,
                rRoot.get("createdDate")));
        Query q = s.createQuery(query);
        return q.getResultList();
    }
}
