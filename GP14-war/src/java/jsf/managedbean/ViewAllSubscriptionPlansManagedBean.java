/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.GymEntity;
import entity.SubscriptionPlanEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author user
 */
@Named(value = "viewAlSubscriptionPlansManagedBean")
@RequestScoped
public class ViewAllSubscriptionPlansManagedBean {

    @EJB(name = "SubscriptionPlanSessionBeanLocal")
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    private List<SubscriptionPlanEntity> subscriptionPlans;
    /**
     * Creates a new instance of ViewAlSubscriptionPlansManagedBean
     */
    public ViewAllSubscriptionPlansManagedBean() {
        
    }
    
    @PostConstruct
    public void postConstruct() {
        setSubscriptionPlans(subscriptionPlanSessionBeanLocal.retrieveAllPlans());
        System.out.println("POST CONSTRUCT RUNNING");
    }

    /**
     * @return the subscriptionPlans
     */
    public List<SubscriptionPlanEntity> getSubscriptionPlans() {
        System.out.println("GET SUB PLANS RUNNING");
        return subscriptionPlans;
    }

    /**
     * @param subscriptionPlans the subscriptionPlans to set
     */
    public void setSubscriptionPlans(List<SubscriptionPlanEntity> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }
    
}
