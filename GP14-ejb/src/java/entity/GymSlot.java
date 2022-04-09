/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author elgin
 */
@Entity
public class GymSlot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymSlotId;
    private Integer vacancies;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;

    public GymSlot() {
        this.startTime = LocalTime.parse("09:00");
        this.endTime = LocalTime.parse("10:00");
        this.vacancies = 10;
    }

    public GymSlot(Integer vacancies, LocalTime startTime, LocalTime endTime) {
        this.vacancies = vacancies;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public GymSlot(Integer vacancies, LocalTime startTime, LocalTime endTime, Date date) {
        this.vacancies = vacancies;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public Long getGymSlotId() {
        return gymSlotId;
    }

    public void setGymSlotId(Long gymSlotId) {
        this.gymSlotId = gymSlotId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gymSlotId != null ? gymSlotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GymSlot)) {
            return false;
        }
        GymSlot other = (GymSlot) object;
        if ((this.gymSlotId == null && other.gymSlotId != null) || (this.gymSlotId != null && !this.gymSlotId.equals(other.gymSlotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GymSlot[ id=" + gymSlotId + " ]";
    }

    /**
     * @return the vacancies
     */
    public Integer getVacancies() {
        return vacancies;
    }

    /**
     * @param vacancies the vacancies to set
     */
    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    /**
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
