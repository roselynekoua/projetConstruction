/*
package com.gestion.managedbean.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Commande;
import com.gestion.model.Utilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

@Component

public class Managedadmin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	private static Logger logger = Logger.getLogger(Managedpersmed.class);
	
	
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
    private boolean etatRecheche=false;
	private Commande commande = new Commande();
	private Utilisateur utilisateur= new Utilisateur();
	private Utilisateur personelmed= new Utilisateur();
	private String code ;
	private List listepers = new ArrayList<>();
	private List listepharma = new ArrayList<>();

	 private List<SelectItem> elementsuser;
	
	 private String iduser;
	 private List<SelectItem> elementscmde;
		
	 private String idcmde; 
	
	@PostConstruct
	public void activerpage(){
		activer();
	}
	 
	public void enregistrer(){
		try{
			
		//personel.setCommande(commande);
	personel.setUtilisateur(utilisateur);
	personel.setCodeSexe(getUtilisateur().getSexe().getLibelleSexe());
	personel.setCodeProfil(getUtilisateur().getProfil().getLibelleProfil());
	
	
	//	personel.setNumPm(getCode());
		getObjectService().addObject(personel);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		personel = new PersMedical();
		vider();
		desactiver();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
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
	
	
	
	public Utilisateur recherPersmedical(String valeur){
		etatRecheche = false;
			List<Object> listNumeroSous = getObjectService().getObjects("Utilisateur");
			for (Iterator<Object> it = listNumeroSous.iterator(); it.hasNext();) {
				Utilisateur objet = (Utilisateur) it.next();
					if (objet.getFonction().getLibelleFonction().equalsIgnoreCase(valeur)){
					try {
						setPersonelmed(objet);
						etatRecheche = true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
					}
			}
			return personelmed;
			
		}
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        // creation du writer -> PDF ou HTML 
     //PdfWriter.getInstance(document, new FileOutputStream(out));
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
    
    
     // etape 4: Ajout du contenu au document
        Phrase ph=new Phrase("                                                          LISTE DES ADMINISTRATEURS");
        ph.setLeading(100f);
      
        		
       
    // pdf.add(new Phrase("LISTE FACTURE"));
     pdf.add(ph);
		
		
		
      //  ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
       // String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator +  "images" + File.separator + "logogest1.jpg";
        //logo.substring(20, 20);
       // logo.subSequence(10,10);
       // pdf.add(Image.getInstance(logo));
        
       
      
    }
	
	public void Recupuser() {
		setUtilisateur((Utilisateur) getObjectService().getObjectById(
				iduser, "Utilisateur"));
		
		activer();
		
	}
	
	public void Recupcmde() {
		setCommande((Commande) getObjectService().getObjectById(
				idcmde, "Commande"));
		
		
	}
	 
	
	public void vider(){
		
		commande = new Commande();
		utilisateur = new Utilisateur();
		setIdcmde(null);
		setEtatBouton(true);
		setIduser(null);;
		
		
	}
	
// geters et seters

	

	public ObjectService getObjectService() {
		return objectService;
	}



	


	public List getListepers() {
		
		listepers = new ArrayList<Utilisateur>();
		List<Object> listObject = getObjectService().getObjects("Utilisateur");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Utilisateur personel = (Utilisateur) it.next();
			try {
				
				
				if (personel.getProfil().getLibelleProfil().equalsIgnoreCase("ROLE_ADMIN")){
				
				
				listepers.add(personel);}
			} catch (Exception e) {
			}
	}
		return listepers;
	}




	public void setListepers(List listepers) {
		this.listepers = listepers;
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




	
	
	
	
	public Commande getCommande() {
		return commande;
	}




	public void setCommande(Commande commande) {
		this.commande = commande;
	}




	public List<SelectItem> getElementsuser() {
		if (elementsuser == null) {
			elementsuser = new ArrayList<SelectItem>();
			try {
				for (Object obj : getObjectService().getObjects("Utilisateur")) {
	   
					elementsuser.add(new SelectItem(((Utilisateur) obj)
							.getCodeUtilisateur(), ((Utilisateur) obj).getNomUtilisateur()));

				}
			} catch (Exception e) {

			}
		}
		
		return elementsuser;
	}




	public void setElementsuser(List<SelectItem> elementsuser) {
		this.elementsuser = elementsuser;
	}




	public String getIduser() {
		return iduser;
	}




	public void setIduser(String iduser) {
		this.iduser = iduser;
	}




	public List<SelectItem> getElementscmde() {
		
		elementscmde = new ArrayList<SelectItem>();
		try {
			for (Object obj : getObjectService().getObjects("Commande")) {
   
				elementscmde.add(new SelectItem(((Commande) obj).getNumCmde(), ((Commande) obj).getDsgCmde()));

			}
		} catch (Exception e) {

		}
	
	
	
		return elementscmde;
	}




	public void setElementscmde(List<SelectItem> elementscmde) {
		this.elementscmde = elementscmde;
	}




	public String getIdcmde() {
		return idcmde;
	}




	public void setIdcmde(String idcmde) {
		this.idcmde = idcmde;
	}




	public Utilisateur getUtilisateur() {
		return utilisateur;
	}




	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}




	public String getCode() {
		return getIdGenerateur().getIdpersmed();
	}




	public void setCode(String code) {
		this.code = code;
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

	public boolean isEtatRecheche() {
		return etatRecheche;
	}

	public void setEtatRecheche(boolean etatRecheche) {
		this.etatRecheche = etatRecheche;
	}

	public Utilisateur getPersonelmed() {
		return personelmed;
	}

	public void setPersonelmed(Utilisateur personelmed) {
		this.personelmed = personelmed;
	}

	public List getListepharma() {
		
		listepharma= new ArrayList<Utilisateur>();
		List<Object> listObject = getObjectService().getObjects("Utilisateur");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Utilisateur personel = (Utilisateur) it.next();
			try {
				
				if (personel.getFonction().getLibelleFonction().equalsIgnoreCase("pharmacien")){
				
					listepharma.add(personel);}
			} catch (Exception e) {
			}
	}
	
		
		
		return listepharma;
	}

	public void setListepharma(List listepharma) {
		this.listepharma = listepharma;
	}




	



	



	


	

	

}







package com.gestion.managedbean.admin;

import java.io.Serializable;
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

import com.gestion.model.Utilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component

public class Managedadmin implements Serializable {
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	private static Logger logger = Logger.getLogger(Managedadmin.class);
	
	
	
	private Utilisateur utilisateur= new Utilisateur();
	private String code ;
	private List listepers = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;

	 private List<SelectItem> elementsuser;
	
	 private String iduser;
	 
	
	
	public void enregistrer(){
		try{
			
		administrateur.setNumAdmin(getCode());	
		administrateur.setUtilisateur(utilisateur);
		
		getObjectService().addObject(administrateur);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		administrateur = new Administrateur();
		iduser = null;
		//desactiver();
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
			//objectService.updateObject(utilisateur);
			//utilisateur.set
			administrateur.setUtilisateur(utilisateur);;
			objectService.updateObject(administrateur);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			administrateur = new Administrateur();
			setIduser(null);
			//desactiver();
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
		
			this.listepers.remove(administrateur);
			
			objectService.deleteObject(administrateur);
			administrateur = new Administrateur();
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
	
	
	public void Recupuser() {
		setUtilisateur((Utilisateur) getObjectService().getObjectById(
				iduser, "Utilisateur"));
		//activer();
	}
	
	
	
	public void vider(){
		setCode(null);
		setIduser(null);
		administrateur = new Administrateur();
		utilisateur = new Utilisateur();
		//setEtatBouton(true);
		
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

	

	public ObjectService getObjectService() {
		return objectService;
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




	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

	




	public List getListepers() {
		
		listepers = new ArrayList<Administrateur>();
		List<Object> listObject = getObjectService().getObjects("Administrateur");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Administrateur administrateur = (Administrateur) it.next();
			try {
				listepers.add(administrateur);
			} catch (Exception e) {
			}
	}
		return listepers;
	}




	public void setListepers(List listepers) {
		this.listepers = listepers;
	}




	

	public IdGenerateur getIdGenerateur() {
		return idGenerateur;
	}


	public void setIdGenerateur(IdGenerateur idGenerateur) {
		this.idGenerateur = idGenerateur;
	}




	
	
	
	
	



	public List<SelectItem> getElementsuser() {
		if (elementsuser == null) {
			elementsuser = new ArrayList<SelectItem>();
			try {
				for (Object obj : getObjectService().getObjects("Utilisateur")) {
	   
					elementsuser.add(new SelectItem(((Utilisateur) obj)
							.getCodeUtilisateur(), ((Utilisateur) obj).getNomUtilisateur()));

				}
			} catch (Exception e) {

			}
		}
		
		return elementsuser;
	}




	public void setElementsuser(List<SelectItem> elementsuser) {
		this.elementsuser = elementsuser;
	}




	public String getIduser() {
		return iduser;
	}




	public void setIduser(String iduser) {
		this.iduser = iduser;
	}




	



	public Utilisateur getUtilisateur() {
		return utilisateur;
	}




	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}




	public String getCode() {
		return getIdGenerateur().getIdadmin();
	}




	public void setCode(String code) {
		this.code = code;
	}




	public Administrateur getAdministrateur() {
		return administrateur;
	}




	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}




	



	



	


	

	

}
*/