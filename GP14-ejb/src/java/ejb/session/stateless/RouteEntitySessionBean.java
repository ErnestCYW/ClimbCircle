/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RouteEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RouteRatingEnum;

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
    public RouteEntity retrieveRouteByRouteId(Long routeId) {
        Query query = em.createQuery("SELECT r FROM RouteEntity r WHERE r.routeID =: routeID");
        query.setParameter("routeId", routeId);

        return (RouteEntity) query.getSingleResult();
    }

    @Override
    public RouteEntity retrieveRouteByRouteName(String routeName) {
        Query query = em.createQuery("SELECT r FROM RouteEntity r WHERE r.routeName =: routeName");
        query.setParameter("routeName", routeName);
        return (RouteEntity) query.getSingleResult();
    }

    @Override
    public List<RouteEntity> retrieveAllRoutes() {
        Query query = em.createQuery("SELECT r FROM RouteEntity r");
        return query.getResultList();
    }

    @Override
    public void deleteRoute(Long routeId) {
        RouteEntity routeEntityToRemove = retrieveRouteByRouteId(routeId);
        em.remove(routeEntityToRemove);
    }
    
    @Override
    public List<Enum> retrieveAllRouteRatings() {
        List<Enum> allRouteRatings = new ArrayList<>(EnumSet.allOf(RouteRatingEnum.class));
        return allRouteRatings;
    }
}
