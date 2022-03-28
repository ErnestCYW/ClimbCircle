package jsf.managedbean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.GymEntitySessionBeanLocal;
import entity.Admin;
import entity.GymEntity;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;



@Named(value = "loginManagedBean")
@RequestScoped

public class LoginManagedBean 
{

    @EJB(name = "GymEntitySessionBeanLocal")
    private GymEntitySessionBeanLocal gymEntitySessionBeanLocal;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    
    
    private String username;
    private String password;
    private String usertype;
    
    
    
    public LoginManagedBean() 
    {
    }
    
    
    
    public void login(ActionEvent event) throws IOException
    {
        try
        {
           
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usertype", usertype);
            
            if (usertype.equals("Admin")) {
                Admin admin = adminSessionBeanLocal.login(username, password);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", admin);
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/mainmenuAdmin.xhtml");
            } else {
                GymEntity gym = gymEntitySessionBeanLocal.login(username, password);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", gym);
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/mainmenuGym.xhtml");
            }

            
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void logout(ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the usertype
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * @param usertype the usertype to set
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
