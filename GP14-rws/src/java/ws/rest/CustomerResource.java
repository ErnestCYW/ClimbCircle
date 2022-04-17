/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import entity.GymSlot;
import entity.RouteReview;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateCustomerRequest;

/**
 * REST Web Service
 *
 * @author elgin
 */
@Path("Customer")
public class CustomerResource {

    CustomerSessionBeanLocal customerSessionBeanLocal = lookupCustomerSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    @Path("login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            Customer customer = customerSessionBeanLocal.login(username, password);
            
            customer.getSubscriptionPlan().getCustomers().clear();
            
            List<GymSlot> gymSlots = customer.getGymSlots();
            for (GymSlot gymSlot : gymSlots) {
                gymSlot.getCustomers().clear();
                gymSlot.setGymEntity(null);
            }
            
            List<RouteReview> routeReviews = customer.getRouteReviews();
            for (RouteReview routeReview : routeReviews) {
                routeReview.setCustomer(null);
                routeReview.setRoute(null);
            }

            GenericEntity<Customer> genericEntity = new GenericEntity<Customer>(customer) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveCustomer/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomer(@PathParam("username") String username) {
        try {
            Customer customer = customerSessionBeanLocal.retrieveCustomerByUsername(username);
            
            customer.getSubscriptionPlan().getCustomers().clear();
            
            List<GymSlot> gymSlots = customer.getGymSlots();
            for (GymSlot gymSlot : gymSlots) {
                gymSlot.getCustomers().clear();
                gymSlot.setGymEntity(null);
            }
            
            List<RouteReview> routeReviews = customer.getRouteReviews();
            for (RouteReview routeReview : routeReviews) {
                routeReview.setCustomer(null);
                routeReview.setRoute(null);
            }

            GenericEntity<Customer> genericEntity = new GenericEntity<Customer>(customer) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CreateCustomerRequest createCustomerRequest) {
        try {
            Long id = customerSessionBeanLocal.createNewCustomer(createCustomerRequest.getNewCustomer(), createCustomerRequest.getSubscriptionPlan());
            return Response.status(Response.Status.OK).entity(id).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
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
