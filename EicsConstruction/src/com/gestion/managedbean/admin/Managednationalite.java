package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gestion.model.Nationalite;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component

public class Managednationalite implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	private static Logger logger = Logger.getLogger(Managednationalite.class);
	private Nationalite nationalite = new Nationalite();
	private List listenat = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	
	
	public void enregistrer(){
		try{
			nationalite.setCodeNat(getIdGenerateur().getIdnat());
		getObjectService().addObject(nationalite);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		nationalite = new Nationalite();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	
	
public void update() {
		
		try {
			
			objectService.updateObject(nationalite);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			nationalite = new Nationalite();vider();
			
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				logger.error("Erreur lors de la modification ", e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
		         e.printStackTrace();
				System.out.println("modification non effectuée");
		}
	}

	public void delete() {
		try {
			this.listenat.remove(nationalite);
			objectService.deleteObject(nationalite);
			nationalite = new Nationalite();
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO,
							"  Suppression effectuée dans la base de donnée", "SUCCES"));
			vider();
		} catch (Exception e) {
			System.out.println(e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Une erreur s'est produite lors de traitement",
							"ERREUR"));
		}
	}
	
	public void vider(){
		nationalite= new Nationalite();
		setEtatBouton(true);
		setEtatAnnuler(true);
	}
	
	
	
public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
	}
	
	public List chargernat(){
		setListenat(getObjectService().getObjects("nationalite"));
		return listenat;
	}
// geters et seters

	

	public ObjectService getObjectService() {
		return objectService;
	}


	public Nationalite getNationalite() {
		return nationalite;
	}


	public void setNationalite(Nationalite nationalite) {
		this.nationalite = nationalite;
	}


	public List getListenat() {
		
		
		listenat = new ArrayList<Nationalite>();
		List<Object> listObject = getObjectService().getObjects("Nationalite");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Nationalite nationalite = (Nationalite) it.next();
			try {
				listenat.add(nationalite);
			} catch (Exception e) {
			}
		
		
		
	}
		
		
		return listenat;
	}


	public void setListenat(List listenat) {
		this.listenat = listenat;
	}


	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}


	public IdGenerateur getIdGenerateur() {
		return idGenerateur;
	}


	public void setIdGenerateur(IdGenerateur idGenerateur) {
		this.idGenerateur = idGenerateur;
	}




	public boolean isEtatBouton() {
		return etatBouton;
	}




	public void setEtatBouton(boolean etatBouton) {
		this.etatBouton = etatBouton;
	}




	public boolean isEtatAnnuler() {
		return etatAnnuler;
	}




	public void setEtatAnnuler(boolean etatAnnuler) {
		this.etatAnnuler = etatAnnuler;
	}



	

	

}
