package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.gestion.model.Profil;
import com.gestion.objetService.ObjectService;
  
@Component
public class ManagedProfilmaj implements Serializable {

	@Autowired
	ObjectService objectService;
	private static final long serialVersionUID = 1L;
	public static String PROP_ID = "Id";
	private Profil profil = new Profil();
	
	private static Logger logger = Logger.getLogger(ManagedProfilmaj.class);
	private int hashCode = Integer.MIN_VALUE;

	
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	// primary key
	private java.lang.String id;
	private java.lang.String libelleProfil;
	private Date dateProfil;
	private List<Profil> listeProfil = new ArrayList<>();

	public Date getDateProfil() {
	setDateProfil(Calendar.getInstance().getTime());
		return dateProfil;
	}

	public void setDateProfil(Date dateProfil) {
		this.dateProfil = dateProfil;
	}

	public java.lang.String getLibelleProfil() {
		return libelleProfil;
	}

	public void setLibelleProfil(java.lang.String libelleProfil) {
		this.libelleProfil = libelleProfil;
	}

	public Profil getProfil() {
		Random rdm = new Random();
		/*int y = 64;
		while (!(64 < y && y < 91)) {
			y = rdm.nextInt(100);
		}

		char V = (char) y;
		String l = Character.toString(V);
		// (pseudo, taillCar, taillChifr,nomTable, nomCOL);
		String key = getObjectService().getCodeTable("PROFIL", 6, 3, "profil",
				"CODE_PROFIL");

		profil.setCodeProfil(key + l);*/

		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="org.hibernate.id.Assigned"
	 *               column="CODE_UTILISATEUR"
	 */
	public java.lang.String getId() {
		
		String pseudo = "PROFIL";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"profil", "CODE_PROFIL");
		setId(formId);
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MAX_VALUE;
	}

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

	public String addProfil() {
		try {
			// Recupération des données Affichées
			setProfil(this.profil);
			//getProfil();// Attribuer un code au client
			profil.setCodeProfil(getId());
			profil.setLibelleProfil(libelleProfil);
			profil.setDateProfilUtilisateur(getDateProfil());
			
			getObjectService().addObject(profil);
			   FacesContext.getCurrentInstance().addMessage(
	                    null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                            "Succes d'enregistrement",
	                            "Succes"));
			
			   Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
				sessionMap.clear();
		profil= new Profil();desactiver();
		setDateProfil(null);setLibelleProfil(null);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return "enrgProfilOK";
	}

	
	public void chargerliste(){
		getObjectService().getojects(profil);
		
	}
	
public void update() {
		
		try {
			
			objectService.updateObject(profil);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			profil = new Profil();
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
			this.listeProfil.remove(profil);
			objectService.deleteObject(profil);
			profil = new Profil();
			desactiver();
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
	
	
public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
	}
	
	public void reset() {
		//this.setLibelleProfil("");
		profil = new Profil();
		setEtatBouton(true);
		setLibelleProfil(null);
	}

	public List<Profil> getListeProfil() {
		listeProfil = new ArrayList<Profil>();
		List<Object> listObject = getObjectService().getObjects("Profil");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Profil profil = (Profil) it.next();
			try {
				listeProfil.add(profil);
			} catch (Exception e) {
			}
		
		
		
	}
		
		return listeProfil;
	}

	public void setListeProfil(List<Profil> listeProfil) {
		this.listeProfil = listeProfil;
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
