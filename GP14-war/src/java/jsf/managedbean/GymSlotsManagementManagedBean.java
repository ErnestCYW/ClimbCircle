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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
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
    private Boolean validated;
    private LocalTime prevEndTime;

    public GymSlotsManagementManagedBean() {
        prevEndTime = LocalTime.parse("09:00");
        validated = true;
    }

    @PostConstruct
    public void init() {
//        this.setGymSlots(new ArrayList<>());
//        getGymSlots().add(new GymSlot());
//        this.setDates(new ArrayList<>());
        //this.setExistingGymSlots(new ArrayList<>());

        //get gym slots for today on render
        Date date = new Date();
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        currDate = Date.from(dayInst);
        existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
    }

    public void add(ActionEvent event) {
        if (existingGymSlots.size() > 0) {
            GymSlot prevGymSlot = existingGymSlots.get(existingGymSlots.size() - 1);
            prevEndTime = prevGymSlot.getEndTime();
        }

        GymSlot newGymSlot = new GymSlot();
        newGymSlot.setStartTime(prevEndTime);
        newGymSlot.setEndTime(prevEndTime.plusHours(1));
        existingGymSlots.add(newGymSlot);
    }

    public void delete(ActionEvent event) {
        GymSlot gymSlotToDelete = (GymSlot) event.getComponent().getAttributes().get("gymSlotToDelete");
        gymSlots.remove(gymSlotToDelete);
    }

    public void dateChanged(SelectEvent event) {
        currDate = (Date) event.getObject();
        existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
    }

    public void validateStartAndEndTime(RowEditEvent event) {
        validated = true;
        GymSlot currGymSlot = (GymSlot) event.getObject();
        int index = existingGymSlots.indexOf(currGymSlot);
        if (currGymSlot.getVacancies() == null) {
            validated = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vacancies must be filled in", null));
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        if (index > 0) {
            GymSlot prevGymSlot = existingGymSlots.get(index - 1);
            if (currGymSlot.getStartTime().compareTo(prevGymSlot.getEndTime()) < 0) {
                validated = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Start Time must be later than previous End Time", null));
                FacesContext.getCurrentInstance().validationFailed();
            }
        }
        
        if (index < existingGymSlots.size() - 1) {
            GymSlot nextGymSlot = existingGymSlots.get(index + 1);
            if (currGymSlot.getEndTime().compareTo(nextGymSlot.getStartTime()) > 0) {
                validated = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "End Time must not be later than next Start Time", null));
                FacesContext.getCurrentInstance().validationFailed();
            }
        }
        
        if (currGymSlot.getEndTime().compareTo(currGymSlot.getStartTime()) <= 0) {
            validated = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "End Time must be later than Start Time", null));
            FacesContext.getCurrentInstance().validationFailed();
        }

    }

    public void createNewGymSlot(ActionEvent event) {

        for (GymSlot gymSlot : existingGymSlots) {
            
            if (gymSlot.getGymSlotId() == null) {
                gymSlot.setDate(currDate);
                Long gymSlotId = gymSlotSessionBeanLocal.createNewGymSlot(gymSlot);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Gym Slot successfully created: " + gymSlotId, null));
            } else {
                gymSlotSessionBeanLocal.updateGymSlot(gymSlot);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gym Slot successfully updated: " + gymSlot.getGymSlotId(), null));
            }

        }

//        for (GymSlot gymSlot : gymSlots) {
//            for (Date date : dates) {
//                GymSlot newGymSlot = new GymSlot(gymSlot.getVacancies(), gymSlot.getStartTime(), gymSlot.getEndTime());
//                newGymSlot.setDate(date);
//                Long gymSlotId = gymSlotSessionBeanLocal.createNewGymSlot(newGymSlot);
//                existingGymSlots = gymSlotSessionBeanLocal.retrieveGymSlotsByDate(currDate);
//            }
//        }
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

    /**
     * @return the prevEndTime
     */
    public LocalTime getPrevEndTime() {
        return prevEndTime;
    }

    /**
     * @param prevEndTime the prevEndTime to set
     */
    public void setPrevEndTime(LocalTime prevEndTime) {
        this.prevEndTime = prevEndTime;
    }

    /**
     * @return the validated
     */
    public Boolean getValidated() {
        return validated;
    }

    /**
     * @param validated the validated to set
     */
    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

}
