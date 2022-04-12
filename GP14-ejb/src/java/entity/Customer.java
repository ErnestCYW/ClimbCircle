/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

/**
 *
 * @author elgin
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String username;
    private String password;
    private String email;
    private Integer numOfPassesLeft;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expiryDate;

    @ManyToMany
    private List<GymSlot> gymSlots;

    @ManyToOne
    private SubscriptionPlanEntity subscriptionPlan;

    public Customer() {
        gymSlots = new ArrayList<>();
    }

    public Customer(String username, String password, String email, Integer numOfPassesLeft, Date expiryDate) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.numOfPassesLeft = numOfPassesLeft;
        this.expiryDate = expiryDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the numOfPassesLeft
     */
    public Integer getNumOfPassesLeft() {
        return numOfPassesLeft;
    }

    /**
     * @param numOfPassesLeft the numOfPassesLeft to set
     */
    public void setNumOfPassesLeft(Integer numOfPassesLeft) {
        this.numOfPassesLeft = numOfPassesLeft;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the gymSlots
     */
    public List<GymSlot> getGymSlots() {
        return gymSlots;
    }

    /**
     * @param gymSlots the gymSlots to set
     */
    public void setGymSlots(List<GymSlot> gymSlots) {
        this.gymSlots = gymSlots;

    }

    public SubscriptionPlanEntity getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlanEntity subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

}
