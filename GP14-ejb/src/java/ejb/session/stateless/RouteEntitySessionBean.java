/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.RouteEntity;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RouteRatingEnum;
import util.exception.RouteNotFoundException;

/**
 *
 * @author jamesyak
 */
@Stateless
public class RouteEntitySessionBean implements RouteEntitySessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public Long createNewRoute(RouteEntity newRouteEntity) {
        em.persist(newRouteEntity);
        em.flush();

        return newRouteEntity.getRouteId();
    }

    @Override
    public RouteEntity retrieveRouteByRouteId(Long routeId) throws RouteNotFoundException {

        RouteEntity routeEntity = em.find(RouteEntity.class, routeId);

        if (routeEntity != null) {
            return routeEntity;
        } else {
            throw new RouteNotFoundException("Route ID " + routeId + " does not exist!");
        }

    }

    @Override
    public RouteEntity retrieveRouteByRouteName(String routeName) {
        Query query = em.createQuery("SELECT r FROM RouteEntity r WHERE r.routeName = :routeName");
        query.setParameter("routeName", routeName);
        return (RouteEntity) query.getSingleResult();
    }

    @Override
    public List<RouteEntity> retrieveAllRoutes() {
        Query query = em.createQuery("SELECT r FROM RouteEntity r");
        return query.getResultList();
    }
    
    @Override
    public List<RouteEntity> retrieveRoutesByGym(GymEntity gym) {
        Query query = em.createQuery("SELECT r FROM RouteEntity r WHERE r.gymEntity = :gym");
        query.setParameter("gym", gym);
        return query.getResultList();
    }

    @Override
    public void deleteRoute(Long routeId) throws RouteNotFoundException {
        RouteEntity routeEntityToRemove = retrieveRouteByRouteId(routeId);
        em.remove(routeEntityToRemove);
    }

    @Override
    public List<RouteRatingEnum> retrieveAllRouteRatings() {
        RouteRatingEnum[] allRouteRatingsArray = RouteRatingEnum.values();
        List<RouteRatingEnum> allRouteRatings = Arrays.asList(allRouteRatingsArray);
        return allRouteRatings;
    }

    @Override
    public void updateRoute(RouteEntity routeEntity) throws RouteNotFoundException {

        if (routeEntity != null && routeEntity.getRouteId() != null) {

            RouteEntity routeEntityToUpdate = retrieveRouteByRouteId(routeEntity.getRouteId());
            
            routeEntityToUpdate.setRouteName(routeEntity.getRouteName());
            routeEntityToUpdate.setDescription(routeEntity.getDescription());
            routeEntityToUpdate.setRouteRating(routeEntity.getRouteRating());
            routeEntityToUpdate.setRouteImageURL(routeEntity.getRouteImageURL());
            routeEntityToUpdate.setColor(routeEntity.getColor());
            routeEntityToUpdate.setLocation(routeEntity.getLocation());
        
        } else {
            throw new RouteNotFoundException("Route ID not provided for route to be updated");
        }

    }
}
