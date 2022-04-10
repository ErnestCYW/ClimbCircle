/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.GymEntitySessionBeanLocal;
import ejb.session.stateless.GymSlotSessionBeanLocal;
import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.Admin;
import entity.Customer;
import entity.GymEntity;
import entity.SubscriptionPlanEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.FacilitiesEnum;

/**
 *
 * @author ernestcyw
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "GymSlotSessionBeanLocal")
    private GymSlotSessionBeanLocal gymSlotSessionBeanLocal;

    @EJB(name = "SubscriptionPlanSessionBeanLocal")
    private SubscriptionPlanSessionBeanLocal subscriptionPlanSessionBeanLocal;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    @EJB(name = "GymEntitySessionBeanLocal")
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;

    @PersistenceContext(unitName = "GP14-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @PostConstruct
    public void postConstruct() {

        if (em.find(GymEntity.class, 1L) == null) {
            List<Enum> allFacilities = new ArrayList<Enum>(EnumSet.allOf(FacilitiesEnum.class));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("CCFUNAN","Climb Central Funan","Climb Central", "password", "107 North Bridge Rd, #B2-19/21 Funan, Singapore 179105",
                    "https://images.squarespace-cdn.com/content/v1/5f93a2d7758e71274f13910c/1605639905978-DNOA4NGK88AWKITLJF65/Asset+1.png", "Mon - Fri 10am to 11pm", "+65 6906 3918", allFacilities));
        }
        if (em.find(Admin.class, 1L) == null) {
            adminSessionBeanLocal.createNewAdmin(new Admin("admin", "password"));
        }

        if (em.find(SubscriptionPlanEntity.class, 1L) == null) {
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Premium", 3, 20.0, 90.0));
        }
        
        if (em.find(Customer.class, 1L) == null) {
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob","password","test@gmail.com",3,new Date()));
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
