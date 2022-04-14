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
public class CreateCustomerRequest {
    private Customer newCustomer;
    private String subscriptionPlan;

    public CreateCustomerRequest() {
    }

    public CreateCustomerRequest(Customer newCustomer, String subscriptionPlan) {
        this.newCustomer = newCustomer;
        this.subscriptionPlan = subscriptionPlan;
    }

    /**
     * @return the newCustomer
     */
    public Customer getNewCustomer() {
        return newCustomer;
    }

    /**
     * @param newCustomer the newCustomer to set
     */
    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
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
