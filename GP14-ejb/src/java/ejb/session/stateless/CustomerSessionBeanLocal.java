/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.SubscriptionPlanEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteCustomerException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author elgin
 */
@Local
public interface CustomerSessionBeanLocal {

    public Long createNewCustomer(Customer newCustomer, String subscriptionPlanName);

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Customer login(String username, String password) throws InvalidLoginCredentialException;

    public void updateCustomer(Customer customer);

    public void renewMembership(Customer customer, String subscriptionPlanName);
    
    public List<Customer> retrieveAllCustomers();

    public void deleteCustomer(String username) throws CustomerNotFoundException, DeleteCustomerException;

    
}
