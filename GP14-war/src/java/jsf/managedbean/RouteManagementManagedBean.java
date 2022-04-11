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
    private List<Enum> availableRouteRatings;
    
    private RouteEntity newRouteEntity;
    private Enum newRouteRatingCreate;
    
   
    /**
     * Creates a new instance of RouteManagementManagedBean
     */
    public RouteManagementManagedBean() {
        routeEntities = new ArrayList<>();
        newRouteEntity = new RouteEntity();
        newRouteRatingCreate = null;
    }
    
    @PostConstruct
    public void postConstruct() {
        setRouteEntities(routeEntitySessionBeanLocal.retrieveAllRoutes());
        setAvailableRouteRatings(routeEntitySessionBeanLocal.retrieveAllRouteRatings());
    }
    
    public void createNewRoute(ActionEvent event) {
        try {
            
            System.out.println("********** " + (RouteRatingEnum) newRouteRatingCreate);
            newRouteEntity.setRouteRating((RouteRatingEnum) newRouteRatingCreate);
            
            Long newRouteId = routeEntitySessionBeanLocal.createNewRoute(newRouteEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New route created successfully (Route ID: " + newRouteId + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new route: " + ex.getMessage(), null));
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

    public List<Enum> getAvailableRouteRatings() {
        return availableRouteRatings;
    }

    public void setAvailableRouteRatings(List<Enum> availableRouteRatings) {
        this.availableRouteRatings = availableRouteRatings;
    }
    
    

    public Enum getNewRouteRatingCreate() {
        return newRouteRatingCreate;
    }

    public void setNewRouteRatingCreate(RouteRatingEnum newRouteRatingCreate) {
        this.newRouteRatingCreate = newRouteRatingCreate;
    }

   
}
