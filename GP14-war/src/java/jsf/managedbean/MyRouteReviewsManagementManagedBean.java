/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.RouteReviewSessionBeanLocal;
import entity.GymEntity;
import entity.RouteReview;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ernestcyw
 */
@Named(value = "myRouteReviewsManagementManagedBean")
@RequestScoped
public class MyRouteReviewsManagementManagedBean {

    @EJB(name = "RouteReviewSessionBeanLocal")
    private RouteReviewSessionBeanLocal routeReviewSessionBeanLocal;

    private List<RouteReview> routeReviews;
    
    private GymEntity currentUser;

    /**
     * Creates a new instance of MyRouteReviewManagedBean
     */
    public MyRouteReviewsManagementManagedBean() {
        routeReviews = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setCurrentUser((GymEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser"));
        setRouteReviews(routeReviewSessionBeanLocal.retrieveAllRouteReviewsByGymId(getCurrentUser().getGymId()));
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

    /**
     * @return the currentUser
     */
    public GymEntity getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(GymEntity currentUser) {
        this.currentUser = currentUser;
    }
    
    

}
