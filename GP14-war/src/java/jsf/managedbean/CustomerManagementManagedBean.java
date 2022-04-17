/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteCustomerException;

/**
 *
 * @author ernestcyw
 */
@Named(value = "customerManagementManagedBean")
@RequestScoped
public class CustomerManagementManagedBean {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    
    private List<Customer> customers;

    /**
     * Creates a new instance of CustomerManagementManagedBean
     */
    public CustomerManagementManagedBean() {
        customers = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        setCustomers(customerSessionBeanLocal.retrieveAllCustomers());
    }

    public void deleteCustomer(ActionEvent event) {
        try {
            Customer customerToDelete = (Customer) event.getComponent().getAttributes().get("customerToDelete");
            customerSessionBeanLocal.deleteCustomer(customerToDelete.getUsername());

            getCustomers().remove(customerToDelete);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer deleted successfully", null));
        } catch (CustomerNotFoundException | DeleteCustomerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting customer: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
}
