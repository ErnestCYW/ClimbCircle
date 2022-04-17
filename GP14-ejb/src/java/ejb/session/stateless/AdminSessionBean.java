/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AdminNotFoundException;
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
    public Admin retrieveAdminByUsername(String username) throws AdminNotFoundException {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username");
        query.setParameter("username", username);
        
        try {
            return (Admin)query.getSingleResult();
        } catch (NoResultException ex) {
            throw new AdminNotFoundException("Admin does not exist");
        }
        
    }
    
    @Override
    public Admin login(String username, String password) throws InvalidLoginCredentialException {
        
        
        try {
            Admin admin = retrieveAdminByUsername(username);
            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password");
            }
        
        } catch (AdminNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password");
        }
        
        
    }
    
    public List<Admin> retrieveAllAdmins() {
        Query query = em.createQuery("SELECT a FROM Admin a");
        List<Admin> admins = query.getResultList();
        
        return admins;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
