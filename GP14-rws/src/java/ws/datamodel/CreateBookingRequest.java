/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author elgin
 */
public class CreateBookingRequest {
    
    private Long gymSlotId;
    private String username;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(Long gymSlotId, String username) {
        this.gymSlotId = gymSlotId;
        this.username = username;
    }
    /**
     * @return the gymSlotId
     */
    public Long getGymSlotId() {
        return gymSlotId;
    }

    /**
     * @param gymSlotId the gymSlotId to set
     */
    public void setGymSlotId(Long gymSlotId) {
        this.gymSlotId = gymSlotId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
