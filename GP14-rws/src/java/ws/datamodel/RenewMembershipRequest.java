/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Customer;

/**
 *
 * @author elgin
 */
public class RenewMembershipRequest {
    private Customer customer;
    private String subscriptionPlan;

    public RenewMembershipRequest() {
    }

    public RenewMembershipRequest(Customer customer, String subscriptionPlan) {
        this.customer = customer;
        this.subscriptionPlan = subscriptionPlan;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the subscriptionPlan
     */
    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    /**
     * @param subscriptionPlan the subscriptionPlan to set
     */
    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
    
    
}
