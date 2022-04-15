/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import ejb.session.stateless.RouteEntitySessionBeanLocal;
import entity.Customer;
import entity.GymEntity;
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

/**
 * REST Web Service
 *
 * @author elgin
 */
@Path("Route")
public class RouteResource {

    GymEntitySessionBeanLocal gymEntitySessionBean = lookupGymEntitySessionBeanLocal();

    RouteEntitySessionBeanLocal routeEntitySessionBean = lookupRouteEntitySessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RouteResource
     */
    public RouteResource() {
    }
    
    @Path("retrieveRoutes/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRoutesByGym(@PathParam("id") Long gymId) {
        try {
            GymEntity gym = gymEntitySessionBean.retrieveGymByGymId(gymId);
            
            List<RouteEntity> routes = routeEntitySessionBean.retrieveRoutesByGym(gym);
            
            for (RouteEntity route : routes) {
                route.setGymEntity(null);
                route.getRouteReviews().clear();
            }

            GenericEntity<List<RouteEntity>> genericEntity = new GenericEntity<List<RouteEntity>>(routes) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    

    /**
     * Retrieves representation of an instance of ws.rest.RouteResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RouteResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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

    private GymEntitySessionBeanLocal lookupGymEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GymEntitySessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/GymEntitySessionBean!ejb.session.stateless.GymEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
