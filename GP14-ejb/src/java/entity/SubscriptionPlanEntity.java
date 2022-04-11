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
    private Long subscriptionPlanId; 
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
   
  
    public Long getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(Long subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subscriptionPlanId != null ? subscriptionPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the subscriptionPlanId fields are not set
        if (!(object instanceof SubscriptionPlanEntity)) {
            return false;
        }
        SubscriptionPlanEntity other = (SubscriptionPlanEntity) object;
        if ((this.subscriptionPlanId == null && other.subscriptionPlanId != null) || (this.subscriptionPlanId != null && !this.subscriptionPlanId.equals(other.subscriptionPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SubscriptionPlanEntity[ id=" + subscriptionPlanId + " ]";
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
