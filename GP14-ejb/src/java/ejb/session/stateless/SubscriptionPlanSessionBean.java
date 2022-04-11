/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SubscriptionPlanEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.SubscriptionPlanEntityNotFoundException;

/**
 *
 * @author user
 */
@Stateless
public class SubscriptionPlanSessionBean implements SubscriptionPlanSessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public Long createNewPlan(SubscriptionPlanEntity newSubscriptionPlanEntity) {
        em.persist(newSubscriptionPlanEntity);
        em.flush();

        return newSubscriptionPlanEntity.getSubscriptionPlanId();
    }

    @Override
    public List<SubscriptionPlanEntity> retrieveAllPlans() {

        Query query = em.createQuery("SELECT s FROM SubscriptionPlanEntity s");

        return query.getResultList();

    }

    @Override
    public SubscriptionPlanEntity retrievePlanByName(String name) throws SubscriptionPlanEntityNotFoundException {
        Query query = em.createQuery("SELECT s from SubscriptionPlanEntity s WHERE s.name = :name");
        query.setParameter("name", name);

        return (SubscriptionPlanEntity) query.getSingleResult();
    }

    @Override
    public SubscriptionPlanEntity retrievePlanById(Long id) throws SubscriptionPlanEntityNotFoundException {
        Query query = em.createQuery("SELECT s from SubscriptionPlanEntity s WHERE s.subscriptionPlanId = :id");
        query.setParameter("id", id);

        return (SubscriptionPlanEntity) query.getSingleResult();
    }

    @Override
    public void deletePlan(Long id) throws SubscriptionPlanEntityNotFoundException {
        SubscriptionPlanEntity subscriptionPlanEntityToRemove = retrievePlanById(id);
        em.remove(subscriptionPlanEntityToRemove);
    }

    @Override
    public void updatePlan(SubscriptionPlanEntity subscriptionPlanEntity) throws SubscriptionPlanEntityNotFoundException {

        if (subscriptionPlanEntity != null && subscriptionPlanEntity.getSubscriptionPlanId() != null) {

            SubscriptionPlanEntity subscriptionPlanEntityToUpdate = retrievePlanById(subscriptionPlanEntity.getSubscriptionPlanId());

            subscriptionPlanEntityToUpdate.setName(subscriptionPlanEntity.getName());
            subscriptionPlanEntityToUpdate.setNumOfMonthlyAccess(subscriptionPlanEntity.getNumOfMonthlyAccess());
            subscriptionPlanEntityToUpdate.setFee(subscriptionPlanEntity.getFee());
            subscriptionPlanEntityToUpdate.setCostPerGymAccess(subscriptionPlanEntity.getCostPerGymAccess());

        } else {
            throw new SubscriptionPlanEntityNotFoundException("Subscription Plan ID not provided for product to be updated");
        }

    }
    
}
