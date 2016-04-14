/*package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.DonneesSup;
import com.gestion.model.Sexe;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Manageddonesup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Manageddonesup.class);
	private DonneesSup donné = new DonneesSup();
	private List<DonneesSup> listedonné = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrer(){
		try{
		donné.setCodeDonsup(getIdGenerateur().getIddonesup());
		//donné.set
		getObjectService().addObject(donné);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		donné = new DonneesSup(); 
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	public void chargerliste(){
		getObjectService().getojects(donné);
		
	}
	
	
public void update() {
		
		try {
			
			objectService.updateObject(donné);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			donné = new DonneesSup();
			desactiver();
			
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				logger.error("Erreur lors de la modification ", e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
		         e.printStackTrace();
				System.out.println("Enregistrement non effectué");
		}
	}

	public void delete() {
		try {
			this.listedonné.remove(donné);
			objectService.deleteObject(donné);
			donné = new DonneesSup();
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO,
							"  Suppression effectuée dans la base de donnée", "SUCCES"));
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
		donné = new DonneesSup();
		setEtatBouton(true);
	}
	
  public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
	}


// geters et seters

	
	
	

	public DonneesSup getDonné() {
		return donné;
	}


	public void setDonné(DonneesSup donné) {
		this.donné = donné;
	}


	public List<DonneesSup> getListedonné() {
		
		
		
		listedonné = new ArrayList<DonneesSup>();
		List<Object> listObject = getObjectService().getObjects("DonneesSup");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			DonneesSup donné = (DonneesSup) it.next();
			try {
				listedonné.add(donné);
			} catch (Exception e) {
			}}
		return listedonné;
	}


	public void setListedonné(List<DonneesSup> listedonné) {
		this.listedonné = listedonné;
	}


	public ObjectService getObjectService() {
		return objectService;
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
*/