/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.GymSlot;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteGymSlotException;

/**
 *
 * @author elgin
 */
@Local
public interface GymSlotSessionBeanLocal {

    public Long createNewGymSlot(String username, GymSlot newGymSlot);

    public List<GymSlot> retrieveGymSlotsByGym(GymEntity gym);

    public GymSlot retrieveGymSlotById(Long gymSlotId);

    public List<GymSlot> retrieveGymSlotsByDate(Date date, GymEntity gym);

    public void updateGymSlot(GymSlot gymSlot);

    public void createBooking(Long gymSlotId, String username);

    public void deleteGymSlot(Long gymSlotId) throws DeleteGymSlotException;

    public List<GymSlot> retrieveGymBookings(Long gymId);
    
}
