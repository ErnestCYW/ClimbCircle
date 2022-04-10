/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.GymSlot;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.GymEntityNotFoundException;

/**
 *
 * @author elgin
 */
@Stateless
public class GymSlotSessionBean implements GymSlotSessionBeanLocal {

    @EJB(name = "GymEntitySessionBeanLocal")
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewGymSlot(String username, GymSlot newGymSlot) {
        
        try {
            GymEntity gym = gymEntitySessionBeanLocal.retrieveGymByUsername(username);
            
            em.persist(newGymSlot);
            gym.getGymSlots().add(newGymSlot);
            newGymSlot.setGymEntity(gym);
            
            em.flush();
            
            return newGymSlot.getGymSlotId();
        } catch (GymEntityNotFoundException ex) {
            return null;
        }
    }
    
    @Override
    public List<GymSlot> retrieveAllGymSlots() {
        Query query = em.createQuery("SELECT g FROM GymSlot g");
        
        return query.getResultList();
    }
    
    @Override
    public GymSlot retrieveGymSlotById(Long gymSlotId) {
        return em.find(GymSlot.class, gymSlotId);
    }
    
    @Override
    public List<GymSlot> retrieveGymSlotsByDate(Date date, GymEntity gym) {
        Query query = em.createQuery("SELECT g FROM GymSlot g WHERE g.gymEntity = :gym AND g.date = :inDate");
        query.setParameter("inDate", date).setParameter("gym", gym);
        List<GymSlot> gymSlots = query.getResultList();
        
        for (GymSlot gymSlot : gymSlots) {
            gymSlot.getGymEntity();
        }
        
        return gymSlots;
    }
    
    @Override
    public void updateGymSlot(GymSlot gymSlot) {
        
        GymSlot gymSlotToUpdate = retrieveGymSlotById(gymSlot.getGymSlotId());
        
        gymSlotToUpdate.setStartTime(gymSlot.getStartTime());
        gymSlotToUpdate.setEndTime(gymSlot.getEndTime());
        gymSlotToUpdate.setVacancies(gymSlot.getVacancies());
    }

    
}
