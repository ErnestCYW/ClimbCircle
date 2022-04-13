/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
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
import util.exception.CustomerNotFoundException;
import util.exception.DeleteGymSlotException;
import util.exception.GymEntityNotFoundException;

/**
 *
 * @author elgin
 */
@Stateless
public class GymSlotSessionBean implements GymSlotSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

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
    public List<GymSlot> retrieveGymSlotsByGym(GymEntity gym) {
        Query query = em.createQuery("SELECT g FROM GymSlot g WHERE g.gymEntity = :gym");
        query.setParameter("gym", gym);
        List<GymSlot> gymSlots = query.getResultList();
        
        for (GymSlot gymSlot : gymSlots) {
            gymSlot.getCustomers().size();
        }
        
        return query.getResultList();
    }
    
    @Override
    public GymSlot retrieveGymSlotById(Long gymSlotId) {
        GymSlot gymSlot = em.find(GymSlot.class, gymSlotId);
        gymSlot.getCustomers().size();
        
        return gymSlot;
    }
    
    @Override
    public List<GymSlot> retrieveGymSlotsByDate(Date date, GymEntity gym) {
        Query query = em.createQuery("SELECT g FROM GymSlot g WHERE g.gymEntity = :gym AND g.date = :inDate");
        query.setParameter("inDate", date).setParameter("gym", gym);
        List<GymSlot> gymSlots = query.getResultList();
        
        for (GymSlot gymSlot : gymSlots) {
            gymSlot.getCustomers().size();
        }
        
        return gymSlots;
    }
    
    @Override
    public void updateGymSlot(GymSlot gymSlot) {
        
        GymSlot gymSlotToUpdate = retrieveGymSlotById(gymSlot.getGymSlotId());
        
        gymSlotToUpdate.setStartTime(gymSlot.getStartTime());
        gymSlotToUpdate.setEndTime(gymSlot.getEndTime());
        gymSlotToUpdate.setVacancies(gymSlot.getVacancies());
        //gymSlotToUpdate.setCustomers(gymSlot.getCustomers());
    }
    
    @Override
    public void createBooking(Long gymSlotId, String username) {
        try {
            GymSlot gymSlot = retrieveGymSlotById(gymSlotId);
            Customer customer = customerSessionBeanLocal.retrieveCustomerByUsername(username);
            gymSlot.setVacancies(gymSlot.getVacancies() - 1);
            gymSlot.getCustomers().add(customer);
            customer.getGymSlots().add(gymSlot);
        } catch (CustomerNotFoundException ex) {
            
        }
        
    }
    
    @Override
    public void deleteGymSlot(Long gymSlotId) throws DeleteGymSlotException {
        GymSlot gymSlotToDelete = retrieveGymSlotById(gymSlotId);
        
        if (gymSlotToDelete.getCustomers().isEmpty()) {
            em.remove(gymSlotToDelete);
        } else {
            throw new DeleteGymSlotException("Gym Slot ID " + gymSlotId + " is associated with customer(s) and cannot be deleted!");
        }
    }

    
}
