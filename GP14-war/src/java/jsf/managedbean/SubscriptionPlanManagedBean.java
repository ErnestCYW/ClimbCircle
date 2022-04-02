/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.SubscriptionPlanEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Named(value = "subscriptionPlanManagedBean")
@ViewScoped
public class SubscriptionPlanManagedBean implements Serializable {

    @EJB(name = "SubscriptionPlanSessionBeanLocal")
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    @Inject
    private ViewSubscriptionPlanManagedBean viewSubscriptionPlanManagedBean;

    private SubscriptionPlanEntity newSubscriptionPlanEntity;
    private List<SubscriptionPlanEntity> subscriptionPlans;

    /**
     * Creates a new instance of SubscriptionPlanManagedBean
     */
    public SubscriptionPlanManagedBean() {
        newSubscriptionPlanEntity = new SubscriptionPlanEntity();

    }

    public SubscriptionPlanManagedBean(SubscriptionPlanEntity newSubscriptionPlanEntity, List<SubscriptionPlanEntity> subscriptionPlans) {
        this();
        this.newSubscriptionPlanEntity = newSubscriptionPlanEntity;
        this.subscriptionPlans = subscriptionPlans;
    }

    @PostConstruct
    public void postConstruct() {
        subscriptionPlans = subscriptionPlanSessionBeanLocal.retrieveAllPlans();
        System.out.println("POST CONSTRUCT RUNNING");
    }

    public void doCreateNewPlan(ActionEvent event) {
        Long newSubscriptionPlanId = subscriptionPlanSessionBeanLocal.createNewPlan(newSubscriptionPlanEntity);

    }

    public void deletePlan(ActionEvent event) {
        SubscriptionPlanEntity subscriptionPlanEntityToDelete = (SubscriptionPlanEntity) event.getComponent().getAttributes().get("subscriptionPlanEntityToDelete");
        subscriptionPlanSessionBeanLocal.deletePlan(subscriptionPlanEntityToDelete.getId());

        subscriptionPlans.remove(subscriptionPlanEntityToDelete);
    }

    public SubscriptionPlanEntity getNewSubscriptionPlanEntity() {
        return newSubscriptionPlanEntity;
    }

    public void setNewSubscriptionPlanEntity(SubscriptionPlanEntity newSubscriptionPlanEntity) {
        this.newSubscriptionPlanEntity = newSubscriptionPlanEntity;
    }

    public List<SubscriptionPlanEntity> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<SubscriptionPlanEntity> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public ViewSubscriptionPlanManagedBean getViewSubscriptionPlanManagedBean() {
        return viewSubscriptionPlanManagedBean;
    }

    public void setViewSubscriptionPlanManagedBean(ViewSubscriptionPlanManagedBean viewSubscriptionPlanManagedBean) {
        this.viewSubscriptionPlanManagedBean = viewSubscriptionPlanManagedBean;
    }

}
