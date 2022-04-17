/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.RouteEntitySessionBeanLocal;
import entity.GymEntity;
import entity.RouteEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import util.enumeration.RouteRatingEnum;
import util.exception.RouteNotFoundException;

/**
 *
 * @author ernestcyw
 */
@Named(value = "myRouteManagementManagedBean")
@ViewScoped
public class MyRouteManagementManagedBean implements Serializable {

    @EJB
    private RouteEntitySessionBeanLocal routeEntitySessionBeanLocal;

    private List<RouteEntity> routeEntities;
    private List<RouteRatingEnum> availableRouteRatings;

    private RouteEntity newRouteEntity;

    private RouteEntity routeEntityToView;

    private RouteEntity routeEntityToUpdate;

    private GymEntity currentUser;

    /**
     * Creates a new instance of RouteManagementManagedBean
     */
    public MyRouteManagementManagedBean() {
        routeEntities = new ArrayList<>();
        availableRouteRatings = new ArrayList<>();

        newRouteEntity = new RouteEntity();
        routeEntityToView = new RouteEntity();
        routeEntityToUpdate = new RouteEntity();
    }

    @PostConstruct
    public void postConstruct() {
        currentUser = (GymEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        setRouteEntities(routeEntitySessionBeanLocal.retrieveRoutesByGym(currentUser));
        setAvailableRouteRatings(routeEntitySessionBeanLocal.retrieveAllRouteRatings());
    }

    public void createNewRoute(ActionEvent event) {
        try {

            newRouteEntity.setGymEntity(currentUser);
            Long newRouteId = routeEntitySessionBeanLocal.createNewRoute(newRouteEntity);

            routeEntities.add(newRouteEntity);

            newRouteEntity = new RouteEntity();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New route created successfully (Route ID: " + newRouteId + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new route: " + ex.getMessage(), null));
        }
    }

    public void doUpdateRoute(ActionEvent event) {
        routeEntityToUpdate = (RouteEntity) event.getComponent().getAttributes().get("routeEntityToUpdate");
    }

    public void updateRoute(ActionEvent event) {
        try {
            routeEntitySessionBeanLocal.updateRoute(getRouteEntityToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Route updated successfully (Route ID: " + routeEntityToUpdate.getRouteId() + ")", null));
        } catch (RouteNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating route: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteRoute(ActionEvent event) {
        try {
            RouteEntity routeEntityToDelete = (RouteEntity) event.getComponent().getAttributes().get("routeEntityToDelete");
            routeEntitySessionBeanLocal.deleteRoute(routeEntityToDelete.getRouteId());
            routeEntities.remove(routeEntityToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Route deleted successfully (Route ID: " + routeEntityToDelete.getRouteId() + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the route: " + ex.getMessage(), null));
        }
    }

    public void handleFileUploadCreate(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "route_pictures" + System.getProperty("file.separator") + event.getFile().getFileName();

//            System.out.println("********** handleFileUpload(): File name: " + event.getFile().getFileName());
//            System.out.println("********** handleFileUpload(): newFilePath: " + newFilePath);
            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            newRouteEntity.setRouteImageURL(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void handleFileUploadUpdate(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "route_pictures" + System.getProperty("file.separator") + event.getFile().getFileName();

//            System.err.println("********** handleFileUpload(): File name: " + event.getFile().getFileName());
//            System.err.println("********** handleFileUpload(): newFilePath: " + newFilePath);
            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            routeEntityToUpdate.setRouteImageURL(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public List<RouteEntity> getRouteEntities() {
        return routeEntities;
    }

    public void setRouteEntities(List<RouteEntity> routeEntities) {
        this.routeEntities = routeEntities;
    }

    public RouteEntity getNewRouteEntity() {
        return newRouteEntity;
    }

    public void setNewRouteEntity(RouteEntity newRouteEntity) {
        this.newRouteEntity = newRouteEntity;
    }

    public List<RouteRatingEnum> getAvailableRouteRatings() {
        return availableRouteRatings;
    }

    public void setAvailableRouteRatings(List<RouteRatingEnum> availableRouteRatings) {
        this.availableRouteRatings = availableRouteRatings;
    }

    public RouteEntity getRouteEntityToView() {
        return routeEntityToView;
    }

    public void setRouteEntityToView(RouteEntity routeEntityToView) {
        this.routeEntityToView = routeEntityToView;
    }

    public RouteEntity getRouteEntityToUpdate() {
        return routeEntityToUpdate;
    }

    public void setRouteEntityToUpdate(RouteEntity routeEntityToUpdate) {
        this.routeEntityToUpdate = routeEntityToUpdate;
    }

}
