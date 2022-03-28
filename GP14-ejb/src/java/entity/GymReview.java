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
public class GymReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymReviewId;
    private String content;
    private Integer rating;

    public Long getGymReviewId() {
        return gymReviewId;
    }

    public void setGymReviewId(Long gymReviewId) {
        this.gymReviewId = gymReviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gymReviewId != null ? gymReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the gymReviewId fields are not set
        if (!(object instanceof GymReview)) {
            return false;
        }
        GymReview other = (GymReview) object;
        if ((this.gymReviewId == null && other.gymReviewId != null) || (this.gymReviewId != null && !this.gymReviewId.equals(other.gymReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GymReview[ id=" + gymReviewId + " ]";
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
}
