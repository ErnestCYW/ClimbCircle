/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RouteReview;
import java.util.List;
import javax.ejb.Local;
import util.exception.RouteReviewNotFoundException;

/**
 *
 * @author ernestcyw
 */
@Local
public interface RouteReviewSessionBeanLocal {

    public List<RouteReview> retrieveAllRouteReviews();

    public List<RouteReview> retrieveAllRouteReviewsByGymId(Long gymId);

    public RouteReview retrieveRouteReviewByRouteReviewId(Long routeReviewId) throws RouteReviewNotFoundException;

    public Long createNewRouteReview(RouteReview newRouteReview);

    public void deleteRouteReview(Long routeReviewId) throws RouteReviewNotFoundException;

    public void updateRouteReview(RouteReview routeReview) throws RouteReviewNotFoundException;
    
}
