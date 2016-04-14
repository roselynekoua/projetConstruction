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

import com.gestion.model.Pays;
import com.gestion.model.Profession;
import com.gestion.model.Ville;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedville implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedville.class);
	
	private Ville ville = new Ville();
	private Pays pays = new Pays();
	private List listeville = new ArrayList<>();

	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrer(){
		try{
			ville.setCodeVille(getIdGenerateur().getIdville());
			//pays.setCodePays(getIdGenerateur().getIdpays());
		getObjectService().addObject(ville);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		ville = new Ville();
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
			
			objectService.updateObject(ville);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			ville = new Ville();
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
			this.listeville.remove(ville);
			objectService.deleteObject(ville);
			ville = new Ville();
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
		ville = new Ville();
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

	
	




	public Pays getPays() {
		return pays;
	}





	public Ville getVille() {
		return ville;
	}





	public void setVille(Ville ville) {
		this.ville = ville;
	}





	public List getListeville() {
		
		//if (listeville.isEmpty()) {
			listeville = getObjectService().getObjects("Ville");
		//}
		return listeville;
	}





	public void setListeville(List listeville) {
		this.listeville = listeville;
	}





	public void setPays(Pays pays) {
		this.pays = pays;
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



	

	

}
