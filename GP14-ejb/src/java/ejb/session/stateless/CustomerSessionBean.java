/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.SubscriptionPlanEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SubscriptionPlanEntityNotFoundException;

/**
 *
 * @author elgin
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @EJB(name = "SubscriptionPlanSessionBeanLocal")
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    
    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCustomer(Customer newCustomer, String subscriptionPlanName) {
        
        try {
            SubscriptionPlanEntity subscriptionPlan = subscriptionPlanSessionBeanLocal.retrievePlanByName(subscriptionPlanName);
            
            subscriptionPlan.getCustomers().add(newCustomer);
            newCustomer.setSubscriptionPlan(subscriptionPlan);
            
            em.persist(newCustomer);
            em.flush();
            
            return newCustomer.getCustomerId();
        } catch (SubscriptionPlanEntityNotFoundException ex) {
            return null;
        }
    }
    
    
    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c from Customer c WHERE c.username = :username");
        query.setParameter("username", username);
        
        try {
            Customer customer = (Customer)query.getSingleResult();
            customer.getGymSlots().size();
            customer.getRouteReviews().size();
            return customer;
        } catch (NoResultException ex) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        
    }
    
    @Override
    public Customer login(String username, String password) throws InvalidLoginCredentialException {

        try {
            Customer customer = retrieveCustomerByUsername(username);
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password");
            }
        
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password");
        }
        
        
    }

    
}
