/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymSlotSessionBeanLocal;
import entity.GymSlot;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author elgin
 */
@Named(value = "gymSlotsManagementManagedBean")
@ViewScoped
public class GymSlotsManagementManagedBean implements Serializable {

    @EJB(name = "GymSlotSessionBeanLocal")
    private GymSlotSessionBeanLocal gymSlotSessionBeanLocal;

    private List<GymSlot> gymSlots;
    private List<GymSlot> existingGymSlots;
    private List<Date> dates;
    private Date currDate;

    public GymSlotsManagementManagedBean() {
    }

    @PostConstruct
    public void init() {
        this.setGymSlots(new ArrayList<>());
        getGymSlots().add(new GymSlot());
        this.setDates(new ArrayList<>());
        this.setExistingGymSlots(new ArrayList<>());

        //get gym slots for today on render
        Date date = new Date();
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        currDate = Date.from(dayInst);
        existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
    }

    public void add() {
        getGymSlots().add(new GymSlot());
    }

    public void delete(ActionEvent event) {
        GymSlot gymSlotToDelete = (GymSlot) event.getComponent().getAttributes().get("gymSlotToDelete");
        gymSlots.remove(gymSlotToDelete);
    }

    public void dateChanged(SelectEvent event) {
        currDate = (Date) event.getObject();
        existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
    }

    public void createNewGymSlot(ActionEvent event) {

        for (GymSlot gymSlot : gymSlots) {
            for (Date date : dates) {
                GymSlot newGymSlot = new GymSlot(gymSlot.getVacancies(), gymSlot.getStartTime(), gymSlot.getEndTime());
                newGymSlot.setDate(date);
                Long gymSlotId = gymSlotSessionBeanLocal.createNewGymSlot(newGymSlot);
                existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
            }
        }

    }

    /**
     * @return the gymSlots
     */
    public List<GymSlot> getGymSlots() {
        return gymSlots;
    }

    /**
     * @param gymSlots the gymSlots to set
     */
    public void setGymSlots(List<GymSlot> gymSlots) {
        this.gymSlots = gymSlots;
    }

    /**
     * @return the dates
     */
    public List<Date> getDates() {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    /**
     * @return the currDate
     */
    public Date getCurrDate() {
        return currDate;
    }

    /**
     * @param currDate the currDate to set
     */
    public void setCurrDate(Date currDate) {
        this.currDate = currDate;

    }

    /**
     * @return the existingGymSlots
     */
    public List<GymSlot> getExistingGymSlots() {
        return existingGymSlots;
    }

    /**
     * @param existingGymSlots the existingGymSlots to set
     */
    public void setExistingGymSlots(List<GymSlot> existingGymSlots) {
        this.existingGymSlots = existingGymSlots;
    }

}
