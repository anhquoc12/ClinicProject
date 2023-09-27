/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.pojo;

import com.anhquoc0304.validations.ScheduleTime;
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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s"),
    @NamedQuery(name = "Schedule.findById", query = "SELECT s FROM Schedule s WHERE s.id = :id"),
    @NamedQuery(name = "Schedule.findByScheduleDate", query = "SELECT s FROM Schedule s WHERE s.scheduleDate = :scheduleDate"),
    @NamedQuery(name = "Schedule.findByShiftStart", query = "SELECT s FROM Schedule s WHERE s.shiftStart = :shiftStart"),
    @NamedQuery(name = "Schedule.findByShiftEnd", query = "SELECT s FROM Schedule s WHERE s.shiftEnd = :shiftEnd")})
@ScheduleTime(message = "{schedule.shiftStart.lessThanShiftEnd}")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "schedule_date")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "{schedule.scheduleDate.notNullMsg}")
    @Future(message = "{schedule.scheduleDate.FutureMsg}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date scheduleDate;
    @Column(name = "shift_start")
    @Temporal(TemporalType.TIME)
    @NotNull(message = "{schedule.shiftStart.notNullMsg}")
    @DateTimeFormat(pattern = "HH:mm")
    private Date shiftStart;
    @Column(name = "shift_end")
    @Temporal(TemporalType.TIME)
    @NotNull(message = "{schedule.shiftEnd.notNullMsg}")
    @DateTimeFormat(pattern = "HH:mm")
    private Date shiftEnd;
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne
    private Room roomId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Schedule() {
    }

    public Schedule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Date shiftStart) {
        this.shiftStart = shiftStart;
    }

    public Date getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Date shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.anhquoc0304.pojo.Schedule[ id=" + id + " ]";
    }

}
