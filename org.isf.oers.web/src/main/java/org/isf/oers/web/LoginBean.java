package org.isf.oers.web;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044605452109550730L;

	private String username;
	private String password;
	
	public LoginBean() {
//		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		if (session != null) 
//			session.invalidate();
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
	
	public String login() {
		String message = "";
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			request.login(username, password);
			
			Principal principal = request.getUserPrincipal();
			System.out.println("success");
			return "success";
		} catch(ServletException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An Error Occured: Login failed", null));
            e.printStackTrace();
		}
		System.out.println("failture");
		return "failure";
	}
	
	public void logout() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) 
			session.invalidate();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
	}
	
}
