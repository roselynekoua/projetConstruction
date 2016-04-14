/*package com.gestion.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gestion.model.Utilisateur;
import com.gestion.utilitaires.AutorityClass;


@Component
@Scope(value="session")
public class UserController implements Serializable{
	Logger logger = Logger.getLogger(UserController.class);

	private static final long serialVersionUID = 1L;
	
	
	private Utilisateur utilisateur = new Utilisateur();
	private TOperateur operateur = new TOperateur();
	private TMotdepasse motdepasse = new TMotdepasse();
	private TFonction fonction = new TFonction();
	private Date dateCon ;
	private AutorityClass autority = new AutorityClass();
	private String name;
	private String password;
	
	@Autowired
	UserService userService;
	
	@PostConstruct
	public void postConstru() throws IOException {
		//setOperateur(userService.getOperateurs());
		//setFonction(userService.getFonctions());
		//setAutority(userService.getAutoritys());
		//setUtilisateur(utilisateur.getLoginUtilisateur());
		setUtilisateur(userService.getuser());
		
		setDateCon(userService.getDateCons());
		renderPage();
		logger.info("Demarrage de l'application");
	}
	
	public void renderPage() throws IOException{
		String url = "accueil.jsf";
		if(getAutority().isMenuAdmin()){
			url = "Creation/LEB2.jsf";
		}
		
		if(getAutority().isMenucreation()){
			url = "Creation/LEB2.jsf";
		}
		
		if(getAutority().isMenuImputation()){
			url = "Imputation/accueil.jsf";
		}
		
		if(getAutority().isMenuExecution()){
			url = "E.jsf";
		}
		 
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		//return "Page/accueil?faces-redirect=true";
	}
	
	public Date getDateCon() {
		return dateCon;
	}
	public void setDateCon(Date dateCon) {
		this.dateCon = dateCon;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public AutorityClass getAutority() {
		return autority;
	}

	public void setAutority(AutorityClass autority) {
		this.autority = autority;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
}
*/