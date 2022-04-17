/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Customer;
import entity.SubscriptionPlanEntity;

/**
 *
 * @author user
 */
public class UpdateCustomerRequest {
    private Customer customer;
    private SubscriptionPlanEntity subscriptionPlan;

    public UpdateCustomerRequest() {
    }

    public UpdateCustomerRequest(Customer customer, SubscriptionPlanEntity subscriptionPlanEntity) {
        this.customer = customer;
        this.subscriptionPlan = subscriptionPlanEntity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SubscriptionPlanEntity getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlanEntity subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
    
    
}
