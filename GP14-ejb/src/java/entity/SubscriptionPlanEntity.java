/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author user
 */
@Entity
public class SubscriptionPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String name;
    private int numOfMonthlyAccess;
    private double fee;
    private double costPerGymAccess;

    public SubscriptionPlanEntity() {
    }

    public SubscriptionPlanEntity(String subscriptionPlanName, int numOfMonthlyAccess, double fee, double costPerGymAccess) {
        this.name = subscriptionPlanName;
        this.numOfMonthlyAccess = numOfMonthlyAccess;
        this.fee = fee;
        this.costPerGymAccess = costPerGymAccess;
    }
   
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof SubscriptionPlanEntity)) {
            return false;
        }
        SubscriptionPlanEntity other = (SubscriptionPlanEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SubscriptionPlanEntity[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfMonthlyAccess() {
        return numOfMonthlyAccess;
    }

    public void setNumOfMonthlyAccess(int numOfMonthlyAccess) {
        this.numOfMonthlyAccess = numOfMonthlyAccess;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getCostPerGymAccess() {
        return costPerGymAccess;
    }

    public void setCostPerGymAccess(double costPerGymAccess) {
        this.costPerGymAccess = costPerGymAccess;
    }
    
}
