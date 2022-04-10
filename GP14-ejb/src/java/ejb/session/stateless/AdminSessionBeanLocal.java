/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import javax.ejb.Local;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author elgin
 */
@Local
public interface AdminSessionBeanLocal {

    public Long createNewAdmin(Admin newAdmin);

    public Admin retrieveAdminByUsername(String username) throws AdminNotFoundException;

    public Admin login(String username, String password) throws InvalidLoginCredentialException;
    
}
