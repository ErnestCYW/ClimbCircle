/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author ernestcyw
 */
@Local
public interface GymEntitySessionBeanLocal {

    public List<GymEntity> retrieveAllGyms();

    public Long createNewGym(GymEntity newGymEntity);

    public GymEntity retrieveGymByUsername(String username);

    public GymEntity login(String username, String password) throws InvalidLoginCredentialException;
    
}
