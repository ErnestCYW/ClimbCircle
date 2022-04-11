/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AdminNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author elgin
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCustomer(Customer newCustomer) {
        em.persist(newCustomer);
        em.flush();
        
        return newCustomer.getCustomerId();
    }
    
    
    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c from Customer c WHERE c.username = :username");
        query.setParameter("username", username);
        
        try {
            Customer customer = (Customer)query.getSingleResult();
            customer.getGymSlots().size();
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
