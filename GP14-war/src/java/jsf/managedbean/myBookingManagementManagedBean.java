/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymSlotSessionBeanLocal;
import entity.GymEntity;
import entity.GymSlot;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ernestcyw
 */
@Named(value = "myBookingManagementManagedBean")
@RequestScoped
public class myBookingManagementManagedBean {

    @EJB(name = "GymSlotSessionBeanLocal")
    private GymSlotSessionBeanLocal gymSlotSessionBeanLocal;

    private List<GymSlot> bookings;

    private GymEntity currentUser;

    /**
     * Creates a new instance of myBookingManagementManagedBean
     */
    public myBookingManagementManagedBean() {
        bookings = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setCurrentUser((GymEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser"));
        setBookings(gymSlotSessionBeanLocal.retrieveGymBookings(getCurrentUser().getGymId()));
    }

    /**
     * @return the bookings
     */
    public List<GymSlot> getBookings() {
        return bookings;
    }

    /**
     * @param bookings the bookings to set
     */
    public void setBookings(List<GymSlot> bookings) {
        this.bookings = bookings;
    }

    /**
     * @return the currentUser
     */
    public GymEntity getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(GymEntity currentUser) {
        this.currentUser = currentUser;
    }
    
    

}
