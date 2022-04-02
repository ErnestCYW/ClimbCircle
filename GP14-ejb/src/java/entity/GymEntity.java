/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ernestcyw
 */
@Entity
public class GymEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymId;
    private String username;
    private String gymName;
    private String franchise;
    private String password;
    private String address;
    private String profilePictureURL;
    private String operatingHours;
    private String contactNumber;
    private List<Enum> facilities;
    

    public GymEntity() {
        facilities = new ArrayList<>();
    }
    
    public GymEntity(String username, String gymName, String franchise, String password, String address,
            String profilePictureURL, String operatingHours, String contactNumber, List<Enum> facilities) {
        this();
        this.username = username;
        this.gymName = gymName;
        this.franchise = franchise;
        this.password = password;
        this.address = address;
        this.profilePictureURL = profilePictureURL;
        this.operatingHours = operatingHours;
        this.contactNumber = contactNumber;
        this.facilities = facilities;
    }
    

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gymId != null ? gymId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the gymId fields are not set
        if (!(object instanceof GymEntity)) {
            return false;
        }
        GymEntity other = (GymEntity) object;
        if ((this.gymId == null && other.gymId != null) || (this.gymId != null && !this.gymId.equals(other.gymId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GymEntity[ id=" + gymId + " ]";
    }

    /**
     * @return the gymName
     */
    public String getGymName() {
        return gymName;
    }

    /**
     * @param gymName the gymName to set
     */
    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    /**
     * @return the franchise
     */
    public String getFranchise() {
        return franchise;
    }

    /**
     * @param franchise the franchise to set
     */
    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the facilities
     */
    public List<Enum> getFacilities() {
        return facilities;
    }

    /**
     * @param facilities the facilities to set
     */
    public void setFacilities(List<Enum> facilities) {
        this.facilities = facilities;
    }

    /**
     * @return the userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the userName to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
        /**
     * @return the profilePictureURL
     */
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    /**
     * @param profilePictureURL the profilePictureURL to set
     */
    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    /**
     * @return the operatingHours
     */
    public String getOperatingHours() {
        return operatingHours;
    }

    /**
     * @param operatingHours the operatingHours to set
     */
    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


}
