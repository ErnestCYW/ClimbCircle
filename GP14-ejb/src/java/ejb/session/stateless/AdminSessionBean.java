/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author elgin
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewAdmin(Admin newAdmin) {
        em.persist(newAdmin);
        em.flush();
        
        return newAdmin.getAdminId();
    }
    
    @Override
    public Admin retrieveAdminByUsername(String username) {
        Query query = em.createQuery("SELECT a from Admin a WHERE a.username = :username");
        query.setParameter("username", username);
        
        return (Admin)query.getSingleResult();
    }
    
    @Override
    public Admin login(String username, String password) throws InvalidLoginCredentialException {
        Admin admin = retrieveAdminByUsername(username);
        
        if (admin.getPassword().equals(password)) {
            return admin;
        } else {
            throw new InvalidLoginCredentialException("Rip");
        }
        
        
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
