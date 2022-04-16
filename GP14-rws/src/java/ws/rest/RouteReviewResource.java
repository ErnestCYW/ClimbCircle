/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.RouteEntitySessionBeanLocal;
import ejb.session.stateless.RouteReviewSessionBeanLocal;
import entity.Customer;
import entity.RouteEntity;
import entity.RouteReview;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateRouteReviewRequest;

/**
 * REST Web Service
 *
 * @author elgin
 */
@Path("RouteReview")
public class RouteReviewResource {

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();

    RouteEntitySessionBeanLocal routeEntitySessionBean = lookupRouteEntitySessionBeanLocal();

    RouteReviewSessionBeanLocal routeReviewSessionBean = lookupRouteReviewSessionBeanLocal();

    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RouteReviewResource
     */
    public RouteReviewResource() {
    }
    
    @Path("retrieveRouteReviews/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRouteReviewsByRoute(@PathParam("id") Long routeId) {
        try {
            RouteEntity route = routeEntitySessionBean.retrieveRouteByRouteId(routeId);

            List<RouteReview> routeReviews = routeReviewSessionBean.retrieveRouteReviewsByRoute(route);

            for (RouteReview routeReview : routeReviews) {
                routeReview.setRoute(null);
                
                Customer customer = routeReview.getCustomer();
                
                customer.getRouteReviews().clear();
                customer.getGymSlots().clear();
                customer.setSubscriptionPlan(null);
            }
            
            
            GenericEntity<List<RouteReview>> genericEntity = new GenericEntity<List<RouteReview>>(routeReviews) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveRouteRatings/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRouteRatingsByRoute(@PathParam("id") Long routeId) {
        try {
            RouteEntity route = routeEntitySessionBean.retrieveRouteByRouteId(routeId);
            
            List<Object[]> ratings = routeReviewSessionBean.retrieveRouteRatingResults(route);
            
            
            //GenericEntity<List<Object[]>> genericEntity = new GenericEntity<List<Object[]>>(ratings);

            return Response.status(Status.OK).entity(ratings).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRouteReview(CreateRouteReviewRequest createRouteReviewRequest) {
        try {
            RouteReview routeReview = createRouteReviewRequest.getRouteReview();
            
            Customer customer = customerSessionBean.retrieveCustomerByUsername(createRouteReviewRequest.getUsername());
            customer.getRouteReviews().add(routeReview);
            routeReview.setCustomer(customer);
            
            RouteEntity route = routeEntitySessionBean.retrieveRouteByRouteId(createRouteReviewRequest.getRouteId());
            route.getRouteReviews().add(routeReview);
            routeReview.setRoute(route);
            
            Long id = routeReviewSessionBean.createNewRouteReview(routeReview);
            
            return Response.status(Response.Status.OK).entity(id).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    /**
     * Retrieves representation of an instance of ws.rest.RouteReviewResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RouteReviewResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private RouteReviewSessionBeanLocal lookupRouteReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouteReviewSessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/RouteReviewSessionBean!ejb.session.stateless.RouteReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RouteEntitySessionBeanLocal lookupRouteEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouteEntitySessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/RouteEntitySessionBean!ejb.session.stateless.RouteEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
