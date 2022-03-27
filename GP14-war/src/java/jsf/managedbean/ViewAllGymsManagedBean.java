/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import entity.GymEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author ernestcyw
 */
@Named(value = "viewAllGymsManagedBean")
@RequestScoped
public class ViewAllGymsManagedBean {

    @EJB
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;

    private List<GymEntity> gymEntities;

    /**
     * Creates a new instance of ViewAllGymsManagedBean
     */
    public ViewAllGymsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setGymEntities(gymEntitySessionBeanLocal.retrieveAllGyms());
    }

    /**
     * @return the gymEntities
     */
    public List<GymEntity> getGymEntities() {
        return gymEntities;
    }

    /**
     * @param gymEntities the gymEntities to set
     */
    public void setGymEntities(List<GymEntity> gymEntities) {
        this.gymEntities = gymEntities;
    }

}
