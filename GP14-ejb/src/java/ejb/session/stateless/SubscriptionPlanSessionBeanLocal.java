/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GymEntity;
import entity.SubscriptionPlanEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface SubscriptionPlanSessionBeanLocal {

    public Long createNewPlan(SubscriptionPlanEntity newSubscriptionPlanEntity);

    public SubscriptionPlanEntity retrievePlanByName(String name);

    public List<SubscriptionPlanEntity> retrieveAllPlans();

    public SubscriptionPlanEntity retrievePlanById(Long id);

    public void deletePlan(Long id);
    
}
