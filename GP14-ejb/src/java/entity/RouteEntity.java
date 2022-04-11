/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.RouteRatingEnum;

/**
 *
 * @author jamesyak
 */
@Entity
public class RouteEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private String routeName;
    private String description;
    private RouteRatingEnum routeRating;
    private String routeImageURL;
    private String location;
    private String color;

    public RouteEntity() {
    }

    public RouteEntity(String routeName, String description, RouteRatingEnum routeRating, String routeImageURL, String location, String color) {
        this();
        this.routeName = routeName;
        this.description = description;
        this.routeRating = routeRating;
        this.routeImageURL = routeImageURL;
        this.location = location;
        this.color = color;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeId != null ? routeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the routeId fields are not set
        if (!(object instanceof RouteEntity)) {
            return false;
        }
        RouteEntity other = (RouteEntity) object;
        if ((this.routeId == null && other.routeId != null) || (this.routeId != null && !this.routeId.equals(other.routeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Route[ id=" + routeId + " ]";
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RouteRatingEnum getRouteRating() {
        return routeRating;
    }

    public void setRouteRating(RouteRatingEnum routeRating) {
        this.routeRating = routeRating;
    }

    public String getRouteImageURL() {
        return routeImageURL;
    }

    public void setRouteImageURL(String routeImageURL) {
        this.routeImageURL = routeImageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
    