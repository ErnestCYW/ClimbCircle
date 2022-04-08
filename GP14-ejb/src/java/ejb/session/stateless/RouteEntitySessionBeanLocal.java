/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RouteEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jamesyak
 */
@Local
public interface RouteEntitySessionBeanLocal {
    
    public Long createNewRoute(RouteEntity newRouteEntity);
    
    public RouteEntity retrieveRouteByRouteId(Long routeId);
    
    public RouteEntity retrieveRouteByRouteName(String routeName);
    
    public List<RouteEntity> retrieveAllRoutes();
    
    public void deleteRoute(Long routeId);
    
}
