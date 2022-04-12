/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.GymEntitySessionBeanLocal;
import ejb.session.stateless.GymSlotSessionBeanLocal;
import entity.Customer;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateBookingRequest;

/**
 * REST Web Service
 *
 * @author elgin
 */
@Path("GymSlot")
public class GymSlotResource {

    GymEntitySessionBeanLocal gymEntitySessionBean = lookupGymEntitySessionBeanLocal();

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();

    GymSlotSessionBeanLocal gymSlotSessionBean = lookupGymSlotSessionBeanLocal();
    
    
    
    

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GymSlotResource
     */
    public GymSlotResource() {
    }

    @Path("retrieveGymSlot/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGymSlot(@PathParam("id") Long gymSlotId) {
        try {
            GymSlot gymSlot = gymSlotSessionBean.retrieveGymSlotById(gymSlotId);

            gymSlot.getGymEntity().getGymSlots().clear();
            gymSlot.getCustomers().clear();

            GenericEntity<GymSlot> genericEntity = new GenericEntity<GymSlot>(gymSlot) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveGymSlots/{id}/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGymSlotsByGym(@PathParam("id") Long gymId, @PathParam("date") String date) {
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
    
    @Path("retrieveGymSlots/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGymSlotsByCustomer(@PathParam("username") String username) {
        try {
            Customer customer = customerSessionBean.retrieveCustomerByUsername(username);
            List<GymSlot> gymSlots = customer.getGymSlots();
            
            for (GymSlot gymSlot : gymSlots) {
                gymSlot.getCustomers().clear();
                gymSlot.getGymEntity().getGymSlots().clear();
            }
            
            GenericEntity<List<GymSlot>> genericEntity = new GenericEntity<List<GymSlot>>(gymSlots) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(CreateBookingRequest request) {

        try {
            gymSlotSessionBean.createBooking(request.getGymSlotId(), request.getUsername());

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    /**
     * Retrieves representation of an instance of ws.rest.GymSlotResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GymSlotResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/GP14/GP14-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
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
