/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import entity.GymEntity;
import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

}
