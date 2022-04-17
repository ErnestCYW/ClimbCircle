/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GymEntitySessionBeanLocal;
import entity.GymEntity;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import util.exception.DeleteGymException;
import util.exception.GymEntityNotFoundException;

/**
 *
 * @author ernestcyw
 */
@Named(value = "gymManagementManagedBean")
@ViewScoped
public class GymManagementManagedBean implements Serializable {

    @EJB
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;

    private List<GymEntity> gymEntities;
//    private List<GymEntity> filteredGymEntities;

    private GymEntity newGymEntity;
    private List<Enum> allFacilities;
    private List<Enum> selectedFacilitiesCreate;

    private GymEntity gymEntityToView;

    private GymEntity gymEntityToUpdate;
    private List<Enum> selectedFacilitiesUpdate;

    /**
     * Creates a new instance of GymManagementManagedBean
     */
    public GymManagementManagedBean() {
        gymEntities = new ArrayList<>();

        newGymEntity = new GymEntity();
        selectedFacilitiesCreate = new ArrayList<>();

        gymEntityToView = new GymEntity();

//        gymEntityToUpdate = new GymEntity();
        selectedFacilitiesUpdate = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setGymEntities(gymEntitySessionBeanLocal.retrieveAllGyms());
//        filteredGymEntities = gymEntitySessionBeanLocal.retrieveAllGyms(); //Temp
        setAllFacilities(gymEntitySessionBeanLocal.retrieveAllFacilities());
    }

//    public void viewGymDetails(ActionEvent event) throws IOException {
//        Long gymIdToView = (Long) event.getComponent().getAttributes().get("gymId");
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gymIdToView", gymIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("viewGymtDetails.xhtml");
//    }
//    
    public void createNewGym(ActionEvent event) {

        try {
            newGymEntity.setFacilities(selectedFacilitiesCreate);
            Long gymIdCreated = gymEntitySessionBeanLocal.createNewGym(newGymEntity);
            gymEntities.add(newGymEntity);

            newGymEntity = new GymEntity();
            selectedFacilitiesCreate = new ArrayList<>(); // can't use .clear() here

            PrimeFaces.current().executeScript("PF('dialogCreateNewGym').hide()");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New gym created successfully (Gym ID: " + gymIdCreated + ")", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product: " + ex.getMessage(), null));
        }

    }

    public void doUpdateGym(ActionEvent event) {

        gymEntityToUpdate = (GymEntity) event.getComponent().getAttributes().get("gymEntityToUpdate");

    }

    public void updateGym(ActionEvent event) {

        try {

            getGymEntityToUpdate().setFacilities(getSelectedFacilitiesUpdate());
            gymEntitySessionBeanLocal.updateGym(getGymEntityToUpdate());

            PrimeFaces.current().executeScript("PF('dialogUpdateGym').hide()");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gym updated successfully", null));
        } catch (GymEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating gym: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    public void deleteGym(ActionEvent event) {

        try {
            GymEntity gymEntityToDelete = (GymEntity) event.getComponent().getAttributes().get("gymEntityToDelete");
            gymEntitySessionBeanLocal.deleteGym(gymEntityToDelete.getGymId());

            gymEntities.remove(gymEntityToDelete);

//            if(filteredProductEntities != null)
//            {
//                filteredProductEntities.remove(productEntityToDelete);
//            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gym deleted successfully", null));
        } catch (GymEntityNotFoundException | DeleteGymException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting gym: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    public void handleFileUploadCreate(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "gym_profile_pictures" + System.getProperty("file.separator") + event.getFile().getFileName();

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

            newGymEntity.setProfilePictureURL(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void handleFileUploadUpdate(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "gym_profile_pictures" + System.getProperty("file.separator") + event.getFile().getFileName();

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

            gymEntityToUpdate.setProfilePictureURL(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void handleFileUploadGymPicture(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + "gym_pictures" + System.getProperty("file.separator") + event.getFile().getFileName();

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

            newGymEntity.getGymPicturesURL().add(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    /**
     * @return the gymEntities
     */
    public List<GymEntity> getGymEntities() {
        return gymEntities;
    }

    /**
     * @param gymEntities the gymEntities to set
     */
    public void setGymEntities(List<GymEntity> gymEntities) {
        this.gymEntities = gymEntities;
    }

    /**
     * @return the newGymEntity
     */
    public GymEntity getNewGymEntity() {
        return newGymEntity;
    }

    /**
     * @param newGymEntity the newGymEntity to set
     */
    public void setNewGymEntity(GymEntity newGymEntity) {
        this.newGymEntity = newGymEntity;
    }

    /**
     * @return the allFacilities
     */
    public List<Enum> getAllFacilities() {
        return allFacilities;
    }

    /**
     * @param allFacilities the allFacilities to set
     */
    public void setAllFacilities(List<Enum> allFacilities) {
        this.allFacilities = allFacilities;
    }

    /**
     * @return the selectedFacilitiesCreate
     */
    public List<Enum> getSelectedFacilitiesCreate() {
        return selectedFacilitiesCreate;
    }

    /**
     * @param selectedFacilitiesCreate the selectedFacilitiesCreate to set
     */
    public void setSelectedFacilitiesCreate(List<Enum> selectedFacilitiesCreate) {
        this.selectedFacilitiesCreate = selectedFacilitiesCreate;
    }

    /**
     * @return the gymEntityToView
     */
    public GymEntity getGymEntityToView() {
        return gymEntityToView;
    }

    /**
     * @param gymEntityToView the gymEntityToView to set
     */
    public void setGymEntityToView(GymEntity gymEntityToView) {
        this.gymEntityToView = gymEntityToView;
    }

    /**
     * @return the gymEntityToUpdate
     */
    public GymEntity getGymEntityToUpdate() {
        return gymEntityToUpdate;
    }

    /**
     * @param gymEntityToUpdate the gymEntityToUpdate to set
     */
    public void setGymEntityToUpdate(GymEntity gymEntityToUpdate) {
        this.gymEntityToUpdate = gymEntityToUpdate;
    }

    /**
     * @return the selectedFacilitiesUpdate
     */
    public List<Enum> getSelectedFacilitiesUpdate() {
        return selectedFacilitiesUpdate;
    }

    /**
     * @param selectedFacilitiesUpdate the selectedFacilitiesUpdate to set
     */
    public void setSelectedFacilitiesUpdate(List<Enum> selectedFacilitiesUpdate) {
        this.selectedFacilitiesUpdate = selectedFacilitiesUpdate;
    }

}
