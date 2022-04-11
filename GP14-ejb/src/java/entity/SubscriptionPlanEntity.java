/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
    private float price;
    private int numOfPasses;
    private int validity;
    
    @OneToMany
    private List<Customer> customers;
    

    public SubscriptionPlanEntity() {
        this.customers = new ArrayList<>();
    }

    public SubscriptionPlanEntity(String name, float price, int numOfPasses, int validity) {
        this();
        this.name = name;
        this.price = price;
        this.numOfPasses = numOfPasses;
        this.validity = validity;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumOfPasses() {
        return numOfPasses;
    }

    public void setNumOfPasses(int numOfPasses) {
        this.numOfPasses = numOfPasses;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
}
