/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.SubscriptionPlanEntity;
import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author user
 */
@Named(value = "createNewSubscriptionPlanManagedBean")
@RequestScoped
public class CreateNewSubscriptionPlanManagedBean {

    @EJB
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    private SubscriptionPlanEntity newSubscriptionPlanEntity;

    public CreateNewSubscriptionPlanManagedBean(SubscriptionPlanEntity newSubscriptionPlanEntity) {
        this.newSubscriptionPlanEntity = newSubscriptionPlanEntity;
    }

    public void doCreateNewPlan(ActionEvent event) {
        Long newSubscriptionPlanId = subscriptionPlanSessionBeanLocal.createNewPlan(newSubscriptionPlanEntity);

    }

    public SubscriptionPlanEntity getNewSubscriptionPlanEntity() {
        return newSubscriptionPlanEntity;
    }

    public void setNewSubscriptionPlanEntity(SubscriptionPlanEntity newSubscriptionPlanEntity) {
        this.newSubscriptionPlanEntity = newSubscriptionPlanEntity;
    }

}
