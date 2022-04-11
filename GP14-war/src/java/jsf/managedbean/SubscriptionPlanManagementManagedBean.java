/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.SubscriptionPlanEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.SubscriptionPlanEntityNotFoundException;

/**
 *
 * @author user
 */
@Named(value = "subscriptionPlanManagementManagedBean")
@ViewScoped
public class SubscriptionPlanManagementManagedBean implements Serializable {

    @EJB
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    private SubscriptionPlanEntity newSubscriptionPlanEntity;
    private List<SubscriptionPlanEntity> subscriptionPlans;

    private SubscriptionPlanEntity subscriptionPlanEntityToUpdate;

    /**
     * Creates a new instance of SubscriptionPlanManagedBean
     */
    public SubscriptionPlanManagementManagedBean() {
        newSubscriptionPlanEntity = new SubscriptionPlanEntity();
        subscriptionPlans = new ArrayList<>();

    }

    @PostConstruct
    public void postConstruct() {
        subscriptionPlans = subscriptionPlanSessionBeanLocal.retrieveAllPlans();
    }

    public void createNewPlan(ActionEvent event) {

        Long newSubscriptionPlanId = subscriptionPlanSessionBeanLocal.createNewPlan(newSubscriptionPlanEntity);
        subscriptionPlans.add(newSubscriptionPlanEntity);
        
        newSubscriptionPlanEntity = new SubscriptionPlanEntity();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product created successfully (Gym ID: " + newSubscriptionPlanId + ")", null));

    }

    public void doUpdatePlan(ActionEvent event) {

        subscriptionPlanEntityToUpdate = (SubscriptionPlanEntity) event.getComponent().getAttributes().get("subscriptionPlanEntityToUpdate");

    }

    public void updatePlan(ActionEvent event) {

        try {
            subscriptionPlanSessionBeanLocal.updatePlan(getSubscriptionPlanEntityToUpdate());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Subscription plan updated successfully", null));
        } catch (SubscriptionPlanEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating subsciption plan: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    public void deletePlan(ActionEvent event) {
        try {

            SubscriptionPlanEntity subscriptionPlanEntityToDelete = (SubscriptionPlanEntity) event.getComponent().getAttributes().get("subscriptionPlanEntityToDelete");
            
            subscriptionPlanSessionBeanLocal.deletePlan(subscriptionPlanEntityToDelete.getSubscriptionPlanId());

            subscriptionPlans.remove(subscriptionPlanEntityToDelete);

        } catch (SubscriptionPlanEntityNotFoundException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting subscription plan: " + ex.getMessage(), null));

        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));

        }
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

    /**
     * @return the subscriptionPlanEntityToUpdate
     */
    public SubscriptionPlanEntity getSubscriptionPlanEntityToUpdate() {
        return subscriptionPlanEntityToUpdate;
    }

    /**
     * @param subscriptionPlanEntityToUpdate the subscriptionPlanEntityToUpdate
     * to set
     */
    public void setSubscriptionPlanEntityToUpdate(SubscriptionPlanEntity subscriptionPlanEntityToUpdate) {
        this.subscriptionPlanEntityToUpdate = subscriptionPlanEntityToUpdate;
    }

}
