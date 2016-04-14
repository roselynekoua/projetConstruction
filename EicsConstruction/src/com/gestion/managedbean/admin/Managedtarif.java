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

import com.gestion.model.Tarif;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedtarif implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedtarif.class);
	private Tarif tarif= new Tarif();
	private Long coutTtcTarif= new Long(0);
	private Long clotureFcfaTarif= new Long(0);
	private Long coutTotalTarif= new Long(0);
	private List<Tarif> listetarif = new ArrayList<>();

	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
	public void enregistrer(){
		try{
		tarif.setCodeTarif(getIdGenerateur().getIdtarif());
		tarif.setClotureFcfaTarif(clotureFcfaTarif);
		tarif.setCoutTtcTarif(coutTtcTarif);
		tarif.setCoutTotalTarif(coutTotalTarif);
		
		
		getObjectService().addObject(tarif);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		tarif = new Tarif();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	public void calcul(){
		coutTotalTarif = new Long(0);
		coutTotalTarif += coutTtcTarif+clotureFcfaTarif;
			System.out.println("++++++++++++++++++++++++++++++cout total" + getCoutTotalTarif());
			
			activer();
	}
	
	
public void update() {
		
		try {
			tarif.setClotureFcfaTarif(clotureFcfaTarif);
			tarif.setCoutTtcTarif(coutTtcTarif);
			tarif.setCoutTotalTarif(coutTotalTarif);
			objectService.updateObject(tarif);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			tarif = new Tarif();
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
			this.listetarif.remove(tarif);
			objectService.deleteObject(tarif);
			tarif = new Tarif();
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
		 coutTtcTarif= new Long(0);
		 clotureFcfaTarif= new Long(0);
		 coutTotalTarif= new Long(0);
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

	

	public List<Tarif> getListetarif() {
		
		listetarif = new ArrayList<Tarif>();
		List<Object> listObject = getObjectService().getObjects("Tarif");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Tarif tarif = (Tarif) it.next();
			try {
				listetarif.add(tarif);
			} catch (Exception e) {
			}
		
		
		
	}
		return listetarif;
	}

	
	


	public void setListetarif(List<Tarif> listetarif) {
		this.listetarif = listetarif;
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





	




	



	public Tarif getTarif() {
		return tarif;
	}





	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
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





	public Long getCoutTtcTarif() {
		return coutTtcTarif;
	}





	public void setCoutTtcTarif(Long coutTtcTarif) {
		this.coutTtcTarif = coutTtcTarif;
	}


	public Long getClotureFcfaTarif() {
		return clotureFcfaTarif;
	}


	public void setClotureFcfaTarif(Long clotureFcfaTarif) {
		this.clotureFcfaTarif = clotureFcfaTarif;
	}


	public Long getCoutTotalTarif() {
		return coutTotalTarif;
	}


	public void setCoutTotalTarif(Long coutTotalTarif) {
		this.coutTotalTarif = coutTotalTarif;
	}



	

	

}
*/