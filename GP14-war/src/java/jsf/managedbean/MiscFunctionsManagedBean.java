/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AdminSessionBeanLocal;
import java.io.Serializable;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ernestcyw
 */
@Named(value = "miscFunctionsManagedBean")
@ViewScoped
public class MiscFunctionsManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private StreamedContent termsOfService;

    private Properties properties;
    private String to;
    private String from;
    private String clientPhone;
    private String password;
    private Session session;

    /**
     * Creates a new instance of MiscFunctionsManagedBean
     */
    public MiscFunctionsManagedBean() {
        termsOfService = DefaultStreamedContent.builder()
                .name("termsOfService.pdf")
                .contentType("application/pdf")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/documents/termsOfService.pdf"))
                .build();
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

    }

    @PostConstruct
    public void postConstruct() {

        try {
            //Pulls admin address from EJB module
            to = adminSessionBeanLocal.retrieveAdminByUsername("admin").getEmail();
        } catch (Exception e) {
            System.out.println(to);
            System.out.println("admin email address not valid or cannot be found");
        }
        from = "climbcircle@gmail.com"; //ClimbCircle to create dummy gmail account to send mail. MUST HAVE LESS SECURE APP ACCESS.
        password = "IS3106Password"; //Change this variable

    }

    public void sendEmail(ActionEvent actionEvent) {

        try {

            Session session1 = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            Message message = new MimeMessage(session1);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("A New Customer Awaits");
            message.setText("ClimbCircle, \n Phone: " + getClientPhone());

            Transport.send(message);

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while sending email: " + ex.getMessage(), null));
        }

    }

    /**
     * @return the termsOfService
     */
    public StreamedContent getTermsOfService() {
        return termsOfService;
    }

    /**
     * @param termsOfService the termsOfService to set
     */
    public void setTermsOfService(StreamedContent termsOfService) {
        this.termsOfService = termsOfService;
    }

    /**
     * @return the clientPhone
     */
    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * @param clientPhone the clientPhone to set
     */
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

}
