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
import ejb.session.stateless.RouteEntitySessionBeanLocal;
import ejb.session.stateless.RouteReviewSessionBeanLocal;
import ejb.session.stateless.SubscriptionPlanSessionBeanLocal;
import entity.Admin;
import entity.Customer;
import entity.GymEntity;
import entity.RouteEntity;
import entity.RouteReview;
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
import util.enumeration.RouteRatingEnum;

/**
 *
 * @author ernestcyw
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "RouteReviewSessionBeanLocal")
    private RouteReviewSessionBeanLocal routeReviewSessionBeanLocal;

    @EJB
    private RouteEntitySessionBeanLocal routeEntitySessionBeanLocal;

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
        
        System.out.println("POSTCONTRUCT RUNNIN");

        //Creating Gyms
        if (em.find(GymEntity.class, 1L) == null) {
            List<Enum> allFacilities = new ArrayList<Enum>(EnumSet.allOf(FacilitiesEnum.class));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("CCFUNAN", "Climb Central Funan", "Climb Central", "password", "107 North Bridge Rd, #B2-19/21 Funan, Singapore 179105", "CCFunan.jpeg", "Mon - Fri 10am to 11pm", "+65 6906 3918", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("CCKALLANG", "Climb Central Kallang", "Climb Central", "password", "#B1-01 Kallang Wave Mall, 1 Stadium Place, Singapore 397628", "CCKallang.jpeg", "Mon - Fri 10am to 11pm", "+65 6702 7972", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("CCNOVENA", "Climb Central Novena", "Climb Central", "password", "238 Thomson Rd, #03-23/25, Singapore 307683", "CCNovena.jpeg", "Mon - Fri 10am to 11pm", "+65 6353 6885", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("CLIMBERSLAB", "Climbers Lab", "Climbers Lab", "password", "48 Woodleigh Park Singapore 608586", "ClimbersLab.png", "Mon - Fri 10am to 9pm", "+65 6515 9363", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("KINETICSCLIMBING", "Kinetics Climbing", "Kinetics Climbing", "password", "511 Serangoon Road Singapore 218153", "KineticsClimbing.png", "Mon - Thu 8am to 11pm", "+65 6291 5045", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("BOULDERWORLD", "Boulder World", "Boulder World", "password", "SingPost Centre, Enrichment Zone, #01-205", "BoulderWorld.png", "Mon - Fri 10am to 11pm", "+65 6917 7783", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("GROUNDUPCLIMBASIA", "Ground Up Climb Asia", "Ground Up Climb", "password", "60 Tessensohn Road, c/o CLUB CSC @ TESSENSOHN Singapore 217664", "GroundUpClimbAsia.png", "Mon - Fri 10am to 11pm", "+65 6292 7701", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("BMDOWNTOWN", "Boulder Movement Downtown", "Boulder Movement", "password", "6A Shenton Way #B1-03 OUE Downtown Gallery Singapore 068815", "BMDowntown.jpeg", "Mon - Fri 10am to 11pm", "+65 6926 3918", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("BMTAISENG", "Boulder Movement Tai Seng", "Boulder Movement", "password", "18 Tai Seng St, #01-09, S(539775)", "BMTaiSeng.jpeg", "Mon - Fri 10am to 11pm", "+65 6732 7972", allFacilities));
            gymEntitySessionBeanLocal.createNewGym(new GymEntity("BMROCHOR", "Boulder Movement Rochor", "Boulder Movement", "password", "2 Serangoon Road #02-12, Tekka Place Singapore 218227", "BMRochor.jpeg", "Mon - Fri 10am to 8pm", "+65 6343 6885", allFacilities));
        }

        //Creating Admin
        //NOTE: Email here is where the request to create a gym account is sent to
        if (em.find(Admin.class, 1L) == null) {
            Admin mainAdmin = new Admin("admin", "password");
            mainAdmin.setEmail("ernestc@u.nus.edu");
            adminSessionBeanLocal.createNewAdmin(mainAdmin);
        }

        //Creating Subscription Plans
        if (em.find(SubscriptionPlanEntity.class, 1L) == null) {
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Premium", 100, 20, 60));
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Deluxe", 80, 15, 40));
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Basic", 60, 10, 30));
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Economy", 40, 5, 15));
            subscriptionPlanSessionBeanLocal.createNewPlan(new SubscriptionPlanEntity("Single", 12, 1, 1));
        }

        //Creating Customers
        if (em.find(Customer.class, 1L) == null) {
            customerSessionBeanLocal.createNewCustomer(new Customer("Alice", "password", "alice@gmail.com", 5, new Date()));
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob", "password", "test@gmail.com", 3, new Date()));
            customerSessionBeanLocal.createNewCustomer(new Customer("Charlie", "password", "charlie@gmail.com", 20, new Date()));
            customerSessionBeanLocal.createNewCustomer(new Customer("Daniel", "password", "daniel@gmail.com", 10, new Date()));
            customerSessionBeanLocal.createNewCustomer(new Customer("Edward", "password", "edward@gmail.com", 15, new Date()));
            customerSessionBeanLocal.createNewCustomer(new Customer("Francis", "password", "francis@gmail.com", 9, new Date()));
        }

        //Creating Routes
        if (em.find(RouteEntity.class, 1L) == null) {

            try {

                RouteEntity newRouteEntity1 = new RouteEntity("CCFunanRoute1", "CCFunanRoute1 Description", RouteRatingEnum.V6to8, "CCFunanRoute1", "White Wall", "Black");
                newRouteEntity1.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity1);

                RouteEntity newRouteEntity2 = new RouteEntity("CCFunanRoute2", "CCFunanRoute2 Description", RouteRatingEnum.V1to2, "CCFunanRoute2", "White Wall", "Orange");
                newRouteEntity2.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity2);

                RouteEntity newRouteEntity3 = new RouteEntity("CCFunanRoute3", "CCFunanRoute3 Description", RouteRatingEnum.V6to8, "CCFunanRoute3", "Black Wall", "Pink");
                newRouteEntity3.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity3);

                RouteEntity newRouteEntity4 = new RouteEntity("BMDowntownRoute1", "BMDowntownRoute1 Description", RouteRatingEnum.VB, "BMDowntownRoute1", "Pink Wall", "#f542f5");
                newRouteEntity4.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("BMDOWNTOWN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity4);

                RouteEntity newRouteEntity5 = new RouteEntity("BMDowntownRoute2", "BMDowntownRoute2 Description", RouteRatingEnum.VB, "BMDowntownRoute2", "Pink Wall", "#4287f5");
                newRouteEntity5.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("BMDOWNTOWN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity5);

                RouteEntity newRouteEntity6 = new RouteEntity("BMDowntownRoute3", "BMDowntownRoute3 Description", RouteRatingEnum.VB, "BMDowntownRoute3", "Pink Wall", "#4287f5");
                newRouteEntity6.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("BMDOWNTOWN"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity6);

                RouteEntity newRouteEntity7 = new RouteEntity("BMRochorRoute1", "BMDowntownRochor1 Description", RouteRatingEnum.V0to1, "BMRochorRoute1", "Wall 1", "Yellow");
                newRouteEntity7.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("BMROCHOR"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity7);

                RouteEntity newRouteEntity8 = new RouteEntity("BMRochorRoute2", "BMDowntownRochor2 Description", RouteRatingEnum.V0to1, "BMRochorRoute2", "Wall 2", "Red");
                newRouteEntity8.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("BMROCHOR"));
                routeEntitySessionBeanLocal.createNewRoute(newRouteEntity8);

            } catch (Exception e) {
                System.err.println("An error occured while creating new routes in data initialisation");
            }
        }

        //Creating Route Reviews 
        if (em.find(RouteReview.class, 1L) == null) {

            try {

                RouteReview newRouteReview1 = new RouteReview("I think this route is great", RouteRatingEnum.V0to1);
                newRouteReview1.setCustomer(customerSessionBeanLocal.retrieveCustomerByUsername("Alice"));
                newRouteReview1.setRoute(routeEntitySessionBeanLocal.retrieveRouteByRouteName("CCFunanRoute1"));
                newRouteReview1.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeReviewSessionBeanLocal.createNewRouteReview(newRouteReview1);

                RouteReview newRouteReview2 = new RouteReview("I think this route is bad", RouteRatingEnum.VB);
                newRouteReview2.setCustomer(customerSessionBeanLocal.retrieveCustomerByUsername("Alice"));
                newRouteReview2.setRoute(routeEntitySessionBeanLocal.retrieveRouteByRouteName("CCFunanRoute2"));
                newRouteReview2.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeReviewSessionBeanLocal.createNewRouteReview(newRouteReview2);

                RouteReview newRouteReview3 = new RouteReview("I think this route is very good", RouteRatingEnum.VB);
                newRouteReview3.setCustomer(customerSessionBeanLocal.retrieveCustomerByUsername("Bob"));
                newRouteReview3.setRoute(routeEntitySessionBeanLocal.retrieveRouteByRouteName("CCFunanRoute1"));
                newRouteReview3.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeReviewSessionBeanLocal.createNewRouteReview(newRouteReview3);

                RouteReview newRouteReview4 = new RouteReview("I think this route is very very good", RouteRatingEnum.V0to1);
                newRouteReview4.setCustomer(customerSessionBeanLocal.retrieveCustomerByUsername("Charlie"));
                newRouteReview4.setRoute(routeEntitySessionBeanLocal.retrieveRouteByRouteName("CCFunanRoute1"));
                newRouteReview4.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeReviewSessionBeanLocal.createNewRouteReview(newRouteReview4);

                RouteReview newRouteReview5 = new RouteReview("I think this route is super duper good", RouteRatingEnum.V0to1);
                newRouteReview5.setCustomer(customerSessionBeanLocal.retrieveCustomerByUsername("Daniel"));
                newRouteReview5.setRoute(routeEntitySessionBeanLocal.retrieveRouteByRouteName("CCFunanRoute1"));
                newRouteReview5.setGymEntity(gymEntitySessionBeanLocal.retrieveGymByUsername("CCFUNAN"));
                routeReviewSessionBeanLocal.createNewRouteReview(newRouteReview5);

            } catch (Exception e) {

                System.err.println("An error occured while creating new route reviews in data initialisation");
            }

        }
        
        //Gym Slot to be created using gyms own logic...
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
