/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.RouteEntitySessionBeanLocal;
import entity.RouteEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.enumeration.RouteRatingEnum;
import util.exception.RouteNotFoundException;

/**
 *
 * @author jamesyak
 */
@Named(value = "routeManagementManagedBean")
@ViewScoped
public class RouteManagementManagedBean implements Serializable {

    @EJB
    private RouteEntitySessionBeanLocal routeEntitySessionBeanLocal;

    private List<RouteEntity> routeEntities;
    private List<RouteRatingEnum> availableRouteRatings;

    private RouteEntity newRouteEntity;

    private RouteEntity routeEntityToView;
    
    private RouteEntity routeEntityToUpdate;

    /**
     * Creates a new instance of RouteManagementManagedBean
     */
    public RouteManagementManagedBean() {
        routeEntities = new ArrayList<>();
        newRouteEntity = new RouteEntity();
        routeEntityToView = new RouteEntity();
        routeEntityToUpdate = new RouteEntity();
    }

    @PostConstruct
    public void postConstruct() {
        setRouteEntities(routeEntitySessionBeanLocal.retrieveAllRoutes());
        setAvailableRouteRatings(routeEntitySessionBeanLocal.retrieveAllRouteRatings());
    }

    public void createNewRoute(ActionEvent event) {
        try {

            System.out.println("**********");

            Long newRouteId = routeEntitySessionBeanLocal.createNewRoute(newRouteEntity);

            routeEntities.add(newRouteEntity);
            
            newRouteEntity = new RouteEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New route created successfully (Route ID: " + newRouteId + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new route: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateRoute(ActionEvent event) {
        routeEntityToUpdate = (RouteEntity)event.getComponent().getAttributes().get("routeEntityToUpdate");
    }
    
    public void updateRoute(ActionEvent event) {
        try {
            routeEntitySessionBeanLocal.updateRoute(getRouteEntityToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Route updated successfully (Route ID: " + routeEntityToUpdate.getRouteId() + ")", null)); 
        } catch (RouteNotFoundException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating route: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    

    public void deleteRoute(ActionEvent event) {
        try {
            RouteEntity routeEntityToDelete = (RouteEntity) event.getComponent().getAttributes().get("routeEntityToDelete");
            routeEntitySessionBeanLocal.deleteRoute(routeEntityToDelete.getRouteId());
            routeEntities.remove(routeEntityToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Route deleted successfully (Route ID: " + routeEntityToDelete.getRouteId() + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the route: " + ex.getMessage(), null));
        }
    }

    public List<RouteEntity> getRouteEntities() {
        return routeEntities;
    }

    public void setRouteEntities(List<RouteEntity> routeEntities) {
        this.routeEntities = routeEntities;
    }

    public RouteEntity getNewRouteEntity() {
        return newRouteEntity;
    }

    public void setNewRouteEntity(RouteEntity newRouteEntity) {
        this.newRouteEntity = newRouteEntity;
    }

    public List<RouteRatingEnum> getAvailableRouteRatings() {
        return availableRouteRatings;
    }

    public void setAvailableRouteRatings(List<RouteRatingEnum> availableRouteRatings) {
        this.availableRouteRatings = availableRouteRatings;
    }

    public RouteEntity getRouteEntityToView() {
        return routeEntityToView;
    }

    public void setRouteEntityToView(RouteEntity routeEntityToView) {
        this.routeEntityToView = routeEntityToView;
    }

    public RouteEntity getRouteEntityToUpdate() {
        return routeEntityToUpdate;
    }

    public void setRouteEntityToUpdate(RouteEntity routeEntityToUpdate) {
        this.routeEntityToUpdate = routeEntityToUpdate;
    }

}
