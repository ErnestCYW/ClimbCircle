/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.FacilitiesEnum;
import util.exception.DeleteGymException;
import util.exception.GymEntityNotFoundException;
import util.exception.InvalidLoginCredentialException;

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
        List<GymEntity> gymEntities = query.getResultList();
        
        for (GymEntity gymEntity : gymEntities) {
            gymEntity.getGymSlots().size();
        }

        return gymEntities;

    }

    @Override
    public GymEntity retrieveGymByUsername(String username) throws GymEntityNotFoundException {
        Query query = em.createQuery("SELECT g from GymEntity g WHERE g.username = :username");
        query.setParameter("username", username);

        try {
            GymEntity gymEntity = (GymEntity) query.getSingleResult();
            gymEntity.getGymSlots().size();
            return gymEntity;
        } catch (NoResultException ex) {
            throw new GymEntityNotFoundException("Gym does not exist");
        }
    }

    @Override
    public GymEntity retrieveGymByGymId(Long gymId) throws GymEntityNotFoundException {

        GymEntity gymEntity = em.find(GymEntity.class, gymId);

        if (gymEntity != null) {

            gymEntity.getGymSlots().size();
//            gymEntity.getRouteReviews();
            return gymEntity;

        } else {

            throw new GymEntityNotFoundException("Gym ID " + gymId + " does not exist!");

        }

    }

    @Override
    public Long createNewGym(GymEntity newGymEntity) {
        em.persist(newGymEntity);
        em.flush();

        return newGymEntity.getGymId();
    }

    @Override
    public GymEntity login(String username, String password) throws InvalidLoginCredentialException {
        try {
            GymEntity gym = retrieveGymByUsername(username);

            if (gym.getPassword().equals(password)) {
                return gym;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password");
            }
        } catch (GymEntityNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password");
        }
    }

    @Override
    public List<Enum> retrieveAllFacilities() {

        List<Enum> allFacilities = new ArrayList<Enum>(EnumSet.allOf(FacilitiesEnum.class));
        return allFacilities;

    }

    @Override
    public void deleteGym(Long gymId) throws GymEntityNotFoundException, DeleteGymException {

        GymEntity gymEntityToRemove = retrieveGymByGymId(gymId);

//        if (gymEntityToRemove.getGymSlots().isEmpty() && gymEntityToRemove.getRouteReview().isEmpty()) {
        em.remove(gymEntityToRemove);

//        } else {
//            
//            throw new DeleteGymException("Gym ID " + gymId + " is associated with gymSlot(s) and or routeReview(s) and cannot be deleted!");
//            
//        }
    }

    @Override
    public void updateGym(GymEntity gymEntity) throws GymEntityNotFoundException {

        if (gymEntity != null && gymEntity.getGymId() != null) {

            GymEntity gymEntityToUpdate = retrieveGymByGymId(gymEntity.getGymId());

            gymEntityToUpdate.setUsername(gymEntity.getUsername());
            gymEntityToUpdate.setGymName(gymEntity.getGymName());
            gymEntityToUpdate.setFranchise(gymEntity.getFranchise());
            gymEntityToUpdate.setAddress(gymEntity.getAddress());
            gymEntityToUpdate.setOperatingHours(gymEntity.getOperatingHours());
            gymEntityToUpdate.setContactNumber(gymEntity.getContactNumber());
            gymEntityToUpdate.setFacilities(gymEntity.getFacilities());

        } else {
            
            throw new GymEntityNotFoundException("Gym ID not provided for gym to be updated");
        }
        

    }

}
