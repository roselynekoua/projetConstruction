package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Maison;
import com.gestion.model.PrototypeMaison;
import com.gestion.model.Utilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component

public class Managedprotomaison implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	@Autowired
	ManagedConnexion managedConnexion;
	
	@Autowired
	ManagedUtilisateur managedUtilisateur;
	
	private static Logger logger = Logger.getLogger(Managedprotomaison.class);
	private PrototypeMaison maison = new PrototypeMaison();
	
	


	private List<PrototypeMaison> listemaison = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
    
    
    private Utilisateur user = new  Utilisateur();
   
    
    private BigDecimal coutTtcMais= new BigDecimal(0);
	private BigDecimal clotureMais =new BigDecimal(0);
	private BigDecimal coutTotalMais= new BigDecimal(0);
    
    private List<SelectItem> elementuser; 
	 private String selecteduser; 
	 private List<SelectItem> elementtarif; 
	 private String selectedtarif; 
	
	public void enregistrer(){
		try {
			
			
			maison.setCodePrototype(getIdGenerateur().getIdprotomaison());
			//maison.setClotureMais(clotureMais);
			//maison.setCoutTtcMais(coutTtcMais);
		//	maison.setCoutTotalMais(coutTotalMais);
		//	maison.setQteRestMais(maison.getQteDpbMais());
			//maison.setUtilisateur(getManagedConnexion().getUtilisateur());
				
		getObjectService().addObject(maison);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		 maison = new PrototypeMaison();vider();
	} catch (NullPointerException e) {
		// TODO Auto-generated catch block
		logger.error("Erreur lors de la modification ", e);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
         e.printStackTrace();
		System.out.println("enregistrement non effectuée");
}
	}
	
	

	public void calcul(){
		
		
	
		
		coutTotalMais = new BigDecimal(0);
		coutTotalMais = coutTtcMais.add(clotureMais);
			System.out.println("++++++++++++++++++++++++++++++cout total" + getCoutTotalMais());
			
			activer();
	}
	
	
/*
public void calculmaj(){
		
		maison.setCoutTotalMais(maison.getCoutTtcMais().add(maison.getClotureMais()));
	
		
		
			
			activer();
	}*/





public void update() {
		
		try {
			
			//maison.setTypeMais(typeMais);
			//objectService.updateObject(typeMais);
			objectService.updateObject(maison);
			
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			 maison = new PrototypeMaison();
			vider();
			
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
			this.listemaison.remove(maison);
			objectService.deleteObject(maison);
			 maison = new PrototypeMaison();
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
	
	public void viderenr(){
		maison = new PrototypeMaison();
	 
	 setSelecteduser(null);
	 setSelectedtarif(null);
		setEtatBouton(true);
		setEtatAnnuler(true);
	}
	
	public void vider(){
	
	// setSelecteduser(null);
	
		setEtatBouton(true);
		maison = new PrototypeMaison();
		//coutTtcMais= new BigDecimal(0);
		// clotureMais =new BigDecimal(0);
		//coutTotalMais= new BigDecimal(0);
		
		
		
	}
	
public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
	}
	
	public List chargermaison(){
		setListemaison(getObjectService().getObjects("maison"));
		return listemaison;
	}
	
	
	public void recupereruser(){
		
		setUser((Utilisateur) getObjectService().getObjectById(selecteduser, "Utilisateur"));

	}
		
	
		
	
	
// geters et seters

	

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





	




	public List<SelectItem> getElementuser() {
		
		 if (elementuser == null) {
			 elementuser= new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Utilisateur")) {
		      elementuser.add(new SelectItem(((Utilisateur) obj).getCodeUtilisateur(), ((Utilisateur) obj).getNomUtilisateur(),((Utilisateur) obj).getPrenomUtilisateur()));
		        
			 }
		    }
		
		
		
		return elementuser;
	}




	public void setElementuser(List<SelectItem> elementuser) {
		this.elementuser = elementuser;
	}




	public String getSelecteduser() {
		return selecteduser;
	}




	public void setSelecteduser(String selecteduser) {
		this.selecteduser = selecteduser;
	}




	




	public String getSelectedtarif() {
		return selectedtarif;
	}




	public void setSelectedtarif(String selectedtarif) {
		this.selectedtarif = selectedtarif;
	}




	




	public Utilisateur getUser() {
		return user;
	}




	public void setUser(Utilisateur user) {
		this.user = user;
	}




	



	public ManagedConnexion getManagedConnexion() {
		return managedConnexion;
	}




	public void setManagedConnexion(ManagedConnexion managedConnexion) {
		this.managedConnexion = managedConnexion;
	}



	public BigDecimal getCoutTtcMais() {
		return coutTtcMais;
	}



	public void setCoutTtcMais(BigDecimal coutTtcMais) {
		this.coutTtcMais = coutTtcMais;
	}



	public BigDecimal getClotureMais() {
		return clotureMais;
	}



	public void setClotureMais(BigDecimal clotureMais) {
		this.clotureMais = clotureMais;
	}



	public BigDecimal getCoutTotalMais() {
		return coutTotalMais;
	}



	public void setCoutTotalMais(BigDecimal coutTotalMais) {
		this.coutTotalMais = coutTotalMais;
	}



	public ManagedUtilisateur getManagedUtilisateur() {
		return managedUtilisateur;
	}



	public void setManagedUtilisateur(ManagedUtilisateur managedUtilisateur) {
		this.managedUtilisateur = managedUtilisateur;
	}



	public List<PrototypeMaison> getListemaison() {
		
		//if (listemaison.isEmpty()) {
			listemaison= getObjectService().getObjects("PrototypeMaison ");
		//}
		return listemaison;
	}



	public void setListemaison(List<PrototypeMaison> listemaison) {
		this.listemaison = listemaison;
	}



	public PrototypeMaison getMaison() {
		return maison;
	}

	public void setMaison(PrototypeMaison maison) {
		this.maison = maison;
	}




	

	

	

}
