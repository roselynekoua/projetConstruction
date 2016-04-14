package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Client;
import com.gestion.model.Utilisateur;
import com.gestion.objetDao.RequeteUtilisateur;
import com.gestion.objetService.ObjectService;


/**
 * 
 *
 *
 */
@Component
public class ManagedConnexion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Injection par Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	
	//Attributs d'instance
	
	private Utilisateur utilisateur;
	private Client client ;
	private String output=" ";
	private List<Client> listeclt = new ArrayList<>();
	
	
	
	
	
	
	
	
	
	/**
	 * Méthode de recuperation des paramètres de connexion de l'utilisateur courrant
	 */
	public void changerParamConexion(){
		//Recuperer L'utilisateur
		try {
			setUtilisateur(getRequeteUtilisateur().RecupererUtilisateurCourrant());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/*****************************Accesseurs*****************************/

	public ObjectService getObjectService() {
		return objectService;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}
	
	
	public RequeteUtilisateur getRequeteUtilisateur() {
		return requeteUtilisateur;
	}

	public void setRequeteUtilisateur(RequeteUtilisateur requeteUtilisateur) {
		this.requeteUtilisateur = requeteUtilisateur;
	}


	public Utilisateur getUtilisateur() {
		
		setUtilisateur(getRequeteUtilisateur().RecupererUtilisateurCourrant());
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}



	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public List<Client> getListeclt() {
		return listeclt;
	}



	public void setListeclt(List<Client> listeclt) {
		this.listeclt = listeclt;
	}

	

	

}
