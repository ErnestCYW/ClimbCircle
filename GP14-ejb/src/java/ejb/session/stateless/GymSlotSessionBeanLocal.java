/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymSlot;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author elgin
 */
@Local
public interface GymSlotSessionBeanLocal {

    public Long createNewGymSlot(GymSlot newGymSlot);

    public List<GymSlot> retrieveAllGymSlots();

    public GymSlot retrieveGymSlotById(Long gymSlotId);
    
}
