/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author elgin
 */
@Local
public interface CustomerSessionBeanLocal {

    public Long createNewCustomer(Customer newCustomer);

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Customer login(String username, String password) throws InvalidLoginCredentialException;
    
}
