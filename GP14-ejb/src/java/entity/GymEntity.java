/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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

    public GymEntity() {
    }
    
    public GymEntity(String gymName) {
        this();
        this.gymName = gymName;
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

}
