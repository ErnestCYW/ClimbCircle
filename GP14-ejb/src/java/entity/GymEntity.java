/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
    private String gymName;
    private String franchise;
    private String password;
    private String address;
    private List<String> facilities;
    

    public GymEntity() {
    }
    
    public GymEntity(String gymName, String franchise, String password, String address, List<String> facilities) {
        this();
        this.gymName = gymName;
        this.franchise = franchise;
        this.password = password;
        this.address = address;
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
    public List<String> getFacilities() {
        return facilities;
    }

    /**
     * @param facilities the facilities to set
     */
    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

}
