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
import org.springframework.stereotype.Component;

import com.gestion.model.Commune;
import com.gestion.model.Pays;
import com.gestion.model.Profession;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedcommune implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedcommune.class);
	private  Commune comune = new Commune();
	private List<Commune> listecomune  = new ArrayList<>();

	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrer(){
		try{
			comune.setCodeCommune(getIdGenerateur().getIdcomune());
			
		getObjectService().addObject(comune);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		comune = new Commune();
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
			
			objectService.updateObject(comune);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			comune = new Commune();
			desactiver();
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
			this.listecomune.remove(comune);
			objectService.deleteObject(comune);
			comune = new Commune();
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
	
	
	public void vider (){
		comune = new Commune();
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





	public Commune getComune() {
		return comune;
	}





	public void setComune(Commune comune) {
		this.comune = comune;
	}





	public List<Commune> getListecomune() {
		
		
		//if (listecomune.isEmpty()) {
			listecomune= getObjectService().getObjects("Commune");
		//}
		return listecomune;
	}





	public void setListecomune(List<Commune> listecomune) {
		this.listecomune = listecomune;
	}



	

	

}
