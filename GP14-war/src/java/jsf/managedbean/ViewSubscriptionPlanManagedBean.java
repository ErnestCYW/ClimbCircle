/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.SubscriptionPlanEntity;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author user
 */
@Named(value = "viewSubscriptionPlanManagedBean")
@ViewScoped
public class ViewSubscriptionPlanManagedBean implements Serializable{
    private SubscriptionPlanEntity subscriptionPlanToView;
    
    /**
     * Creates a new instance of ViewSubscriptionPlanManagedBean
     */
    public ViewSubscriptionPlanManagedBean() {
    }

    public SubscriptionPlanEntity getSubscriptionPlanToView() {
        return subscriptionPlanToView;
    }

    public void setSubscriptionPlanToView(SubscriptionPlanEntity subscriptionPlanToView) {
        this.subscriptionPlanToView = subscriptionPlanToView;
    }
    
}
