/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.RouteReview;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DeleteGymException;
import util.exception.GymEntityNotFoundException;
import util.exception.RouteReviewNotFoundException;

/**
 *
 * @author ernestcyw
 */
@Stateless
public class RouteReviewSessionBean implements RouteReviewSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public List<RouteReview> retrieveAllRouteReviews() {

        Query query = em.createQuery("SELECT rr FROM RouteReview rr");
        List<RouteReview> routeReviews = query.getResultList();

        for (RouteReview routeReview : routeReviews) {
            routeReview.getGymEntity();
            routeReview.getRoute();
            routeReview.getCustomer();
        }

        return routeReviews;

    }

    @Override
    public List<RouteReview> retrieveAllRouteReviewsByGymId(Long gymId) {

        Query query = em.createQuery("SELECT rr FROM RouteReview rr WHERE rr.gymEntity.gymId = :gymEntityId");
        query.setParameter("gymEntityId", gymId);
        List<RouteReview> routeReviews = query.getResultList();

        return routeReviews;

    }

    @Override
    public RouteReview retrieveRouteReviewByRouteReviewId(Long routeReviewId) throws RouteReviewNotFoundException {

        RouteReview routeReview = em.find(RouteReview.class, routeReviewId);

        if (routeReview != null) {
            return routeReview;
        } else {
            throw new RouteReviewNotFoundException("Route Review Id " + routeReviewId + " does not exists!");
        }
    }

    @Override
    public Long createNewRouteReview(RouteReview newRouteReview) {
        em.persist(newRouteReview);
        em.flush();

        return newRouteReview.getRouteReviewId();
    }

    @Override
    public void deleteRouteReview(Long routeReviewId) throws RouteReviewNotFoundException {

        RouteReview routeReviewToRemove = retrieveRouteReviewByRouteReviewId(routeReviewId);

        routeReviewToRemove.getGymEntity().getRouteReviews().remove(routeReviewToRemove);
        routeReviewToRemove.getRoute().getRouteReviews().remove(routeReviewToRemove);
        routeReviewToRemove.getCustomer().getRouteReviews().remove(routeReviewToRemove);

        em.remove(routeReviewToRemove);
    }

    @Override
    public void updateRouteReview(RouteReview routeReview) throws RouteReviewNotFoundException {

        if (routeReview != null && routeReview.getRouteReviewId() != null) {
            RouteReview routeReviewToUpdate = retrieveRouteReviewByRouteReviewId(routeReview.getRouteReviewId());

            routeReviewToUpdate.setContent(routeReview.getContent());
            routeReviewToUpdate.setRating(routeReview.getRating());
            
        } else {

            throw new RouteReviewNotFoundException("Route ID not provided for route to be updated");
        }

    }

}