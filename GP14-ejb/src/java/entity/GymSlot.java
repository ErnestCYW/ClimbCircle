/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @ManyToOne(optional = false)
    private GymEntity gymEntity;
    
    @ManyToMany(mappedBy="gymSlots")
    private List<Customer> customers;
    

    public GymSlot() {
        this.startTime = LocalTime.parse("09:00");
        this.endTime = LocalTime.parse("10:00");
        this.vacancies = 10;
        this.customers = new ArrayList<>();
    }

    public GymSlot(Integer vacancies, LocalTime startTime, LocalTime endTime) {
        this.vacancies = vacancies;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customers = new ArrayList<>();
    }

    public GymSlot(Integer vacancies, LocalTime startTime, LocalTime endTime, Date date) {
        this.vacancies = vacancies;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.customers = new ArrayList<>();
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

    /**
     * @return the gymEntity
     */
    public GymEntity getGymEntity() {
        return gymEntity;
    }

    /**
     * @param gymEntity the gymEntity to set
     */
    public void setGymEntity(GymEntity gymEntity) {
        this.gymEntity = gymEntity;
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }


}
