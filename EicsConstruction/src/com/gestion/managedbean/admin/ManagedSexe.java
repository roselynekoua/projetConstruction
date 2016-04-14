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

import com.gestion.model.Sexe;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class ManagedSexe implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(ManagedSexe.class);
	private Sexe monSexe = new Sexe();
	private List<Sexe> listeSexe = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrerSexe(){
		try{
		monSexe.setCodeSexe(getIdGenerateur().getIdsexe());
		getObjectService().addObject(monSexe);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		monSexe = new Sexe(); 
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	public void chargerliste(){
		getObjectService().getojects(monSexe);
		
	}
	
	
public void update() {
		
		try {
			
			objectService.updateObject(monSexe);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			monSexe = new Sexe();
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
			this.listeSexe.remove(monSexe);
			objectService.deleteObject(monSexe);
			monSexe = new Sexe();
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
   // setMonSexe(null);
		//monSexe = new Sexe();
    monSexe.setCodeSexe(" ");
    monSexe.setLibelleSexe(" ");
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

	public Sexe getMonSexe() {
		return monSexe;
	}


	public void setMonSexe(Sexe monSexe) {
		this.monSexe = monSexe;
	}

	public List<Sexe> getListeSexe() {
		
		listeSexe = new ArrayList<Sexe>();
		List<Object> listObject = getObjectService().getObjects("Sexe");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Sexe monSexe = (Sexe) it.next();
			try {
				listeSexe.add(monSexe);
			} catch (Exception e) {
			}
		
		
		
	}
		return listeSexe;
	}

	public void setListeSexe(List<Sexe> listeSexe) {
		this.listeSexe = listeSexe;
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
