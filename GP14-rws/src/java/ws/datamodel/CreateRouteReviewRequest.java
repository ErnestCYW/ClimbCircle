/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.RouteReview;

/**
 *
 * @author elgin
 */
public class CreateRouteReviewRequest {
    private RouteReview routeReview;
    private String username;
    private Long routeId;

    public CreateRouteReviewRequest() {
    }

    public CreateRouteReviewRequest(RouteReview routeReview, String username, Long routeId) {
        this.routeReview = routeReview;
        this.username = username;
        this.routeId = routeId;
    }

    /**
     * @return the routeReview
     */
    public RouteReview getRouteReview() {
        return routeReview;
    }

    /**
     * @param routeReview the routeReview to set
     */
    public void setRouteReview(RouteReview routeReview) {
        this.routeReview = routeReview;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the routeId
     */
    public Long getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the routeId to set
     */
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    
    
}
