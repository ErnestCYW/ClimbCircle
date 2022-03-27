/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ernestcyw
 */
@Stateless
public class GymEntitySessionBean implements GymEntitySessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<GymEntity> retrieveAllGyms() {
        
        Query query = em.createQuery("SELECT g FROM GymEntity g");
        
        return query.getResultList();
        
    }
    
    @Override
    public Long createNewGym(GymEntity newGymEntity) {
        em.persist(newGymEntity);
        em.flush();
        
        return newGymEntity.getGymId();
    }
    
}
