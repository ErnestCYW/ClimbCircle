/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.SubscriptionPlanEntity;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;

/**
 * REST Web Service
 *
 * @author user
 */
@Path("SubscriptionPlan")
public class SubscriptionPlanResource {

    SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal = lookupSubscriptionPlanSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of subscriptionPlanResource
     */
    public SubscriptionPlanResource() {
    }

    @Path("retrieveAllSubscriptionPlans")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSubscriptionPlans() {
        try {
      
            List<SubscriptionPlanEntity> subscriptionPlans = subscriptionPlanSessionBeanLocal.retrieveAllPlans();

            for (SubscriptionPlanEntity subscriptionPlan : subscriptionPlans) {
                subscriptionPlan.getCustomers().clear();
            }
            
            GenericEntity<List<SubscriptionPlanEntity>> genericEntity = new GenericEntity<List<SubscriptionPlanEntity>>(subscriptionPlans) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    private SubscriptionPlanSessionBeanLocal lookupSubscriptionPlanSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SubscriptionPlanSessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/SubscriptionPlanSessionBean!ejb.session.stateless.SubscriptionPlanSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
