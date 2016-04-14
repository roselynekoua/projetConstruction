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
	private DonneesSup donn� = new DonneesSup();
	private List<DonneesSup> listedonn� = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrer(){
		try{
		donn�.setCodeDonsup(getIdGenerateur().getIddonesup());
		//donn�.set
		getObjectService().addObject(donn�);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succ�s d'enregistrement", "Enregistrement effectu�"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		donn� = new DonneesSup(); 
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectu�");		
	}
	}
	
	
	public void chargerliste(){
		getObjectService().getojects(donn�);
		
	}
	
	
public void update() {
		
		try {
			
			objectService.updateObject(donn�);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectu�e dans la base",
							"SUCCES"));
			donn� = new DonneesSup();
			desactiver();
			
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				logger.error("Erreur lors de la modification ", e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
		         e.printStackTrace();
				System.out.println("Enregistrement non effectu�");
		}
	}

	public void delete() {
		try {
			this.listedonn�.remove(donn�);
			objectService.deleteObject(donn�);
			donn� = new DonneesSup();
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO,
							"  Suppression effectu�e dans la base de donn�e", "SUCCES"));
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
		donn� = new DonneesSup();
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

	
	
	

	public DonneesSup getDonn�() {
		return donn�;
	}


	public void setDonn�(DonneesSup donn�) {
		this.donn� = donn�;
	}


	public List<DonneesSup> getListedonn�() {
		
		
		
		listedonn� = new ArrayList<DonneesSup>();
		List<Object> listObject = getObjectService().getObjects("DonneesSup");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			DonneesSup donn� = (DonneesSup) it.next();
			try {
				listedonn�.add(donn�);
			} catch (Exception e) {
			}}
		return listedonn�;
	}


	public void setListedonn�(List<DonneesSup> listedonn�) {
		this.listedonn� = listedonn�;
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