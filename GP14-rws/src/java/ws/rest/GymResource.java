/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import ejb.session.stateless.GymSlotSessionBeanLocal;
import entity.GymEntity;
import entity.GymSlot;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("Gym")
public class GymResource {

    GymSlotSessionBeanLocal gymSlotSessionBean = lookupGymSlotSessionBeanLocal();

    GymEntitySessionBeanLocal gymEntitySessionBean = lookupGymEntitySessionBeanLocal();

    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GymResource
     */
    public GymResource() {
    }
    
    @Path("retrieveAllGyms")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllGyms() {
        try {
            List<GymEntity> gyms = gymEntitySessionBean.retrieveAllGyms();
            
            for (GymEntity gym : gyms) {
                List<GymSlot> gymSlots = gym.getGymSlots();
                
                for (GymSlot gymSlot : gymSlots) {
                    gymSlot.setGymEntity(null);
                }
            }

            GenericEntity<List<GymEntity>> genericEntity = new GenericEntity<List<GymEntity>>(gyms) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveGym/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGymById(@PathParam("id") Long gymId) {
        try {
            GymEntity gym = gymEntitySessionBean.retrieveGymByGymId(gymId);
            
            for (GymSlot gymSlot : gym.getGymSlots()) {
                gymSlot.setGymEntity(null);
            }

            GenericEntity<GymEntity> genericEntity = new GenericEntity<GymEntity>(gym) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveGymSlots/{id}/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGymSlots(@PathParam("id") Long gymId, @PathParam("date") String date) {
        try {
            GymEntity gym = gymEntitySessionBean.retrieveGymByGymId(gymId);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date dateObj = simpleDateFormat.parse(date);
            
            List<GymSlot> gymSlots = gymSlotSessionBean.retrieveGymSlotsByDate(dateObj, gym);
            
            for (GymSlot gymSlot : gymSlots) {
                gymSlot.getCustomers().clear();
                gymSlot.setGymEntity(null);
            }

            GenericEntity<List<GymSlot>> genericEntity = new GenericEntity<List<GymSlot>>(gymSlots) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    /**
     * Retrieves representation of an instance of ws.rest.GymResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GymResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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

    private GymSlotSessionBeanLocal lookupGymSlotSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GymSlotSessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/GymSlotSessionBean!ejb.session.stateless.GymSlotSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
