/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import util.enumeration.RouteRatingEnum;

/**
 *
 * @author ernestcyw
 */
@Entity
public class RouteReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeReviewId;
    private String content;
    private RouteRatingEnum rating;
    private Date datePosted;

    @ManyToOne(optional = false)
    private RouteEntity route;

    @ManyToOne(optional = false)
    private Customer customer;

    public RouteReview() {
    }

    public RouteReview(String content, RouteRatingEnum rating, Date datePosted) {
        this();
        this.content = content;
        this.rating = rating;
        this.datePosted = datePosted;
    }

    public Long getRouteReviewId() {
        return routeReviewId;
    }

    public void setRouteReviewId(Long routeReviewId) {
        this.routeReviewId = routeReviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeReviewId != null ? routeReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the routeReviewId fields are not set
        if (!(object instanceof RouteReview)) {
            return false;
        }
        RouteReview other = (RouteReview) object;
        if ((this.routeReviewId == null && other.routeReviewId != null) || (this.routeReviewId != null && !this.routeReviewId.equals(other.routeReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GymReview[ id=" + routeReviewId + " ]";
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the rating
     */
    public RouteRatingEnum getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(RouteRatingEnum rating) {
        this.rating = rating;
    }

    /**
     * @return the route
     */
    public RouteEntity getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the datePosted
     */
    public Date getDatePosted() {
        return datePosted;
    }

    /**
     * @param datePosted the datePosted to set
     */
    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

}
