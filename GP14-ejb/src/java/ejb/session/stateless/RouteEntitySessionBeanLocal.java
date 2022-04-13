/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.RouteEntity;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.RouteRatingEnum;
import util.exception.RouteNotFoundException;

/**
 *
 * @author jamesyak
 */
@Local
public interface RouteEntitySessionBeanLocal {
    
    public Long createNewRoute(RouteEntity newRouteEntity);
    
    public RouteEntity retrieveRouteByRouteId(Long routeId) throws RouteNotFoundException;
    
    public RouteEntity retrieveRouteByRouteName(String routeName);
    
    public List<RouteEntity> retrieveAllRoutes();
    
    public void deleteRoute(Long routeId) throws RouteNotFoundException;
    
    public List<RouteRatingEnum> retrieveAllRouteRatings();
    
    public void updateRoute(RouteEntity routeEntity) throws RouteNotFoundException;

    public List<RouteEntity> retrieveRoutesByGym(GymEntity gym);
    
}
