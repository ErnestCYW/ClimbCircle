/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import entity.GymEntity;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author ernestcyw
 */
@Named(value = "createNewGymManagedBean")
@RequestScoped
public class CreateNewGymManagedBean {

    @EJB(name = "GymEntitySessionBeanLocal")
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;

    private GymEntity newGymEntity;
    
    private List<Enum> selectedFacilities;
    private List<SelectItem> selectItems;

    /**
     * Creates a new instance of CreateNewGymManagedBean
     */
    public CreateNewGymManagedBean() {
        
        newGymEntity = new GymEntity();
        
    }
    
    public void doCreateNewGym(ActionEvent event) {
        Long newGymId = gymEntitySessionBeanLocal.createNewGym(newGymEntity);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New gym created: " + newGymId, "New gym created: " + newGymId));
    }

    /**
     * @return the newGymEntity
     */
    public GymEntity getNewGymEntity() {
        return newGymEntity;
    }

    /**
     * @param newGymEntity the newGymEntity to set
     */
    public void setNewGymEntity(GymEntity newGymEntity) {
        this.newGymEntity = newGymEntity;
    }
    
    @PostConstruct
    public void postConstruct() {
        
        List<Enum> allFacilities = gymEntitySessionBeanLocal.retrieveAllFacilities();
        setSelectItems(new ArrayList<>());
        
        for(Enum facility: allFacilities) {
            getSelectItems().add(new SelectItem(facility.toString()));
        }
        
    }

    /**
     * @return the selectedFacilities
     */
    public List<Enum> getSelectedFacilities() {
        return selectedFacilities;
    }

    /**
     * @param selectedFacilities the selectedFacilities to set
     */
    public void setSelectedFacilities(List<Enum> selectedFacilities) {
        this.selectedFacilities = selectedFacilities;
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

}
