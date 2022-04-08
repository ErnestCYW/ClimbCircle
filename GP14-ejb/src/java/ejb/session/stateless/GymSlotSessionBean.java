/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymSlot;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author elgin
 */
@Stateless
public class GymSlotSessionBean implements GymSlotSessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewGymSlot(GymSlot newGymSlot) {
        
        em.persist(newGymSlot);
        em.flush();
        
        return newGymSlot.getGymSlotId();
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
    public List<GymSlot> retrieveGymSlotsByDate(Date date) {
        Query query = em.createQuery("SELECT g FROM GymSlot g WHERE g.date = :inDate");
        query.setParameter("inDate", date);
        
        return query.getResultList();
    }

    
}
