/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.RouteReviewSessionBeanLocal;
import entity.RouteReview;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.RouteReviewNotFoundException;

/**
 *
 * @author ernestcyw
 */
@Named(value = "routeReviewManagementManagedBean")
@RequestScoped
public class RouteReviewManagementManagedBean {

    @EJB(name = "RouteReviewSessionBeanLocal")
    private RouteReviewSessionBeanLocal routeReviewSessionBeanLocal;

    private List<RouteReview> routeReviews;

    /**
     * Creates a new instance of RouteReviewManagementManagedBean
     */
    public RouteReviewManagementManagedBean() {
        routeReviews = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setRouteReviews(routeReviewSessionBeanLocal.retrieveAllRouteReviews());
    }

    public void deleteRouteReview(ActionEvent event) {
        try {
            RouteReview routeReviewToDelete = (RouteReview) event.getComponent().getAttributes().get("routeReviewToDelete");
            routeReviewSessionBeanLocal.deleteRouteReview(routeReviewToDelete.getRouteReviewId());

            routeReviews.remove(routeReviewToDelete);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Route review deleted successfully", null));
        } catch (RouteReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting route review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the routeReviews
     */
    public List<RouteReview> getRouteReviews() {
        return routeReviews;
    }

    /**
     * @param routeReviews the routeReviews to set
     */
    public void setRouteReviews(List<RouteReview> routeReviews) {
        this.routeReviews = routeReviews;
    }

}
