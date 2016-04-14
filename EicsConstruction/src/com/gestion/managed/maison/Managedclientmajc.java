package com.gestion.managed.maison;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managedbean.admin.ManagedConnexion;
import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Maison;
import com.gestion.model.Nationalite;
import com.gestion.model.Profil;
import com.gestion.model.Sexe;
import com.gestion.model.Utilisateur;
import com.gestion.objetDao.RequeteUtilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;


@Component
public class Managedclientmajc implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	
   
   @Autowired
	RequeteUtilisateur requeteUtilisateur;
   
   
   @Autowired
   ManagedConnexion managedConnexion;
   
   @Autowired
   IdGenerateur idGenerateur;
	private String critere;
	private String criteremjcontrat;
   private boolean etatBouton=true;
   private boolean etatAnnuler=true;
   private boolean etatSup=true;
	private Utilisateur utilisateur = new Utilisateur();
	private Sexe sexe = new Sexe();
	
	private Client client = new Client();
	
	private Nationalite nationalite = new Nationalite();
	private Maison maison = new Maison();
	private Contrat contrat = new Contrat();
	
	
	private Client clientrech = new Client();
	private Contrat contratrech = new Contrat();
	private Client clientselected = new Client();
	private Contrat contratselected = new Contrat();
	
	
	private Profil monProfil = new Profil();
	 private List<SelectItem> elements; 
	  
	 
	 private List<SelectItem> elementsexe; 
	 private String selectedsexe; 
	
	 private List<SelectItem> elementnat; 
	 private String selectednat; 
	
	 private List<SelectItem> elementadm; 
	 private String selectedadm; 
	 private List<SelectItem> elementmaison; 
	 private String selectedmaison;
	 

	 
	 private List<Client> listeclient = new ArrayList<>();
	 private List<Contrat> listecontrat = new ArrayList<>();
	 private List<Contrat> listecontratparperiode = new ArrayList<>();
	 private List<Contrat> listecontratrech = new ArrayList<>();
	 private List<Contrat> listecontratmaj = new ArrayList<>();
		


	// primary key
	private java.lang.String id;

    @Autowired
	ObjectService objectService;

	// fields
	private java.lang.String nomUtilisateur;
	private java.lang.String prenomUtilisateur;
	private java.lang.String mailUtilisateur;
	
	private java.lang.String motPasse;
	private java.lang.String matricule;
	private java.util.Date dateCreationMotPasse;
	private java.lang.Integer activite;
	
	private Date dateDeb = new Date() ;
	private Date dateFin = new  Date() ;

	
	
	
	
	
	private BigDecimal qteTerSup = new BigDecimal(0);
	private BigDecimal coutUnitSup= new BigDecimal(0);
	private BigDecimal coutTotalSup= new BigDecimal(0);
	private SimpleDateFormat formate= new SimpleDateFormat("yyyy-MM-dd");
	
	
	private String typeFiltre;
	private String filtre;
	
	public void filtrer() {
		try {
			getListeclient().clear();
			getListeclient().addAll(objectService.filtrerclient(typeFiltre, filtre));
			if (getListeclient().size()==0){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Entrer un bon crit�re de recherche",
								"Erreur"));
				//setFiltre(null);
			}
			else{
				
				setEtatAnnuler(false);
				setEtatBouton(false);
				
			System.out.println("Type filtre :" + typeFiltre + " Filtre :"
					+ filtre);
			
			
			}
			
		} catch (Exception e) {
			System.out.println(e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Une erreur s'est produite lors de traitement",
							"ERREUR"));
		}
	}
	
	public void retriveContrat() {
		
		
		listecontratmaj.clear();
		//contratselected 
		//listecontratmaj= (List<Contrat>) getObjectService().getObjectById(criteremjcontrat, "Contrat");
		
		
		System.out.println("Apr�s Requ�te...........................");
		Contrat Ctrat = (Contrat) getObjectService()
				.getObjectById(getCriteremjcontrat().trim(), "Contrat");
		System.out.println("Apr�s Requ�te...........................");
		

		if (Ctrat == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ce contrat n'existe pas",
							"AUCUNE DONNEE"));
	}
		
	else {
		listecontratmaj.add(Ctrat);
		setContratselected(Ctrat);
		
	}
	
	
	
	}
	
	public void editer(){
		
		//contrat.setMaison(Ctrat.getMaison());
				setContrat(contratselected);
				activer();
	}
	
public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
		setEtatSup(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
		setEtatSup(false);
	}
	
	
	
	/*
	public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesfull", event.getFile().getFileName() + " is uploaded.");
      //  client.setPhotoClt(event.getFile().getContents());
        FacesContext.getCurrentInstance().addMessage(null, message);
        client.setBoitePostClt(event.getFile().getFileName());
    }
	 private UploadedFile file;
	 
	    public UploadedFile getFile() {
	        return file;
	    }
	 
	    public void setFile(UploadedFile file) {
	        this.file = file;
	    }
	     
	    public void upload() {
	        if(file != null) {
	            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	    }
	*/
	
	
	
	public String handleflow(FlowEvent event) {
		String currentStepId = event.getNewStep();
		String stepToGo = event.getOldStep();
		/*
		 * if(skip) return "confirm"; else
		 */
		return event.getNewStep();
	}
	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="org.hibernate.id.Assigned"
	 *               column="CODE_UTILISATEUR"
	 *//*
	public java.lang.String getId() {
		return id;
	}

	*//**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	

	

	
	/**
	 * Return the value associated with the column: NOM_UTILISATEUR
	 */
	public java.lang.String getNomUtilisateur() {
		return nomUtilisateur;
	}

	/**
	 * Set the value related to the column: NOM_UTILISATEUR
	 * 
	 * @param nomUtilisateur
	 *            the NOM_UTILISATEUR value
	 */
	public void setNomUtilisateur(java.lang.String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	/**
	 * Return the value associated with the column: PRENOM_UTILISATEUR
	 */
	public java.lang.String getPrenomUtilisateur() {
		return prenomUtilisateur;
	}

	/**
	 * Set the value related to the column: PRENOM_UTILISATEUR
	 * 
	 * @param prenomUtilisateur
	 *            the PRENOM_UTILISATEUR value
	 */
	public void setPrenomUtilisateur(java.lang.String prenomUtilisateur) {
		this.prenomUtilisateur = prenomUtilisateur;
	}

	/**
	 * Return the value associated with the column: MOT_PASSE
	 */
	public java.lang.String getMotPasse() {
		return motPasse;
	}

	public void setMotPasse(java.lang.String motPasse) {
		this.motPasse = motPasse;
	}

	public SimpleDateFormat getFormate() {
		return formate;
	}





	public void setFormate(SimpleDateFormat formate) {
		this.formate = formate;
	}





	/**
	 * Return the value associated with the column: DATE_CREATION_MOT_PASSE
	 */
	public java.util.Date getDateCreationMotPasse() {
		return dateCreationMotPasse;
	}

	/**
	 * Set the value related to the column: DATE_CREATION_MOT_PASSE
	 * 
	 * @param dateCreationMotPasse
	 *            the DATE_CREATION_MOT_PASSE value
	 */
	public void setDateCreationMotPasse(java.util.Date dateCreationMotPasse) {
		this.dateCreationMotPasse = dateCreationMotPasse;
	}

	public String login() {
		if (nomUtilisateur != null && nomUtilisateur.equals("admin")
				&& motPasse != null && motPasse.equals("admin")) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	
	public void creerIdclient(){
		
		String pseudo = "CLT";
		String formId = getObjectService().getCodeTable(pseudo, 3, 5,
				"client", "NUM_CLT");
		
		client.setNumClt(formId);
		
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}


	
	public void enregistrer() {
		try{
		
			creerIdclient();
			// Recup�ration des donn�es Affich�es
			client.setSexe(getSexe());
			//client.setSexe(sexe);
			
			client.setNationalite(nationalite);
			
			
					getObjectService().addObject(client);
					System.out.println("Client enregistr� avec succ�s!");
					
					enregistrercontrat();
					
					//vider();
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succ�s d'enregistrement ", "Succ�s  !"));
			       
					
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec  d'enregistrement", "Echec  !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectu�");	
	}
		}
	
	
	public void enregistrercontrat() {
		try{
		
		contrat.setCodeContrat(getIdGenerateur().getIdcontrat());
		contrat.setClient(client);
		contrat.setMaison(maison);
		contrat.setQteAcq(1);
//		contrat.setCoutUnitSup(coutUnitSup);
//		contrat.setQteTerSup(qteTerSup);
//		contrat.setCoutTotalSup(coutTotalSup);
		contrat.setUtilisateur(getManagedConnexion().getUtilisateur());
	
//		maison.setQteRestMais(maison.getQteDpbMais()-contrat.getQteAcq());
		getObjectService().updateObject(maison);
		
					getObjectService().addObject(contrat);
					System.out.println("Contrat enregistr� avec succ�s!");
					
					
					
				
					
					
					
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
			System.out.println("Enregistrement non effectu�");	
	}
		}
	
	
	
	

public void  recuperer() {
	

	setListecontratparperiode(requeteUtilisateur.recuplistecontat(formate.format(dateDeb),formate.format(dateFin)));
	if (listecontratparperiode.size()==0){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"", "Aucun contrat sur cette periode !"));
	  }	
		if (dateFin.before(dateDeb)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"", "Entrez une bonne date  !"));
			 	
		}
		}
	
	

public String formatDate(Date d) {
	String X = " ";
	Calendar c = Calendar.getInstance();
	c.setTime(d);
	// AAAA-MM-JJ
	X = X + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
			+ c.get(Calendar.DATE);

	return X;
} 

	public void recuperersexe(){
		
	setSexe((Sexe) getObjectService().getObjectById(selectedsexe, "Sexe"));

}
	


	public void recuperernat(){
		
	setNationalite((Nationalite) getObjectService().getObjectById(selectednat, "Nationalite"));

}
	
	
	public void recuperermaison(){
		
		setMaison((Maison) getObjectService().getObjectById(selectedmaison, "Maison"));

	}	

	
	
	public void cal(){
		
		
		coutTotalSup = coutUnitSup.multiply(qteTerSup);
		//coutTotalSup = qteTerSup*coutUnitSup;
	//	coutTotalSup = qteTerSup.coutUnitSup;
	
	}
	
public void calcontrat(){
		
//	contrat.setCoutTotalSup(contrat.getCoutUnitSup().multiply(contrat.getQteTerSup()));
		//coutTotalSup = coutUnitSup.multiply(qteTerSup);
		//coutTotalSup = qteTerSup*coutUnitSup;
	//	coutTotalSup = qteTerSup.coutUnitSup;
	
	}
	
	public void Rechercheclient() {
		setClientrech((Client) getObjectService().getObjectById(critere,
				"Client"));
		
		
		
		if (getClientrech() == null) {
			System.out.println("client Inexistant");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Avertissement",
							"Ce num�ro de client n'est pas attribu�!"));
		
		}
		
		else {
			for (Contrat contr: getClientrech().getContrats()) {
				getListecontratrech().add(contr);
		}}
		
	}
	
	public void Recherchecontrat() {
		setContratrech((Contrat) getObjectService().getObjectById(critere,
				"Contrat"));
		
		
		
		if (getContratrech() == null) {
			System.out.println("Contrat Inexistant");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Avertissement",
							"Ce num�ro de Contrat n'est pas attribu�!"));
		
		}
		else {
			listecontratrech.add(contratrech);
		}
	}
	
	public void Recherche() {
		getListecontratrech().clear();
		
		switch (option) {
		case "1": // recherche par le num contrat
			Recherchecontrat();;

			break;
		case "0":// recherche par le num de client
			Rechercheclient();;
			break;

		default:
			//option non selectionn�
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Avertissement","Choisir une option"));
			break;
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		getListecontratrech().clear();
		setContratrech(((Contrat) event.getObject()));
		setClientrech(getContratrech().getClient());
	
	}
		
	public void onRowSelectclt(SelectEvent event) {
		getListeclient().clear();
		setClient(getClientselected());
		
		//setContrat((Contrat) getClient().getContrats());
	
	}	
	
	public void reset() {
		setMotPasse(null);
		utilisateur.setMotPasse(null);
		setEtatBouton(true);
	}
	

	public void vider() {
		
		client = new Client();
		 sexe = new Sexe();
		
		 nationalite = new Nationalite();
		setCriteremjcontrat(null);
		listecontratmaj.clear();
		 setSelectedsexe(null);
		
		 setSelectednat(null);
		setSelectedmaison(null);
		contrat = new Contrat();
	

		 qteTerSup = new BigDecimal(0);
		 coutUnitSup= new BigDecimal(0);
		 coutTotalSup= new BigDecimal(0);
		 setEtatBouton(true);
			setEtatSup(true);
	}
	
	
	
public void update() {
		
		try {
			
			objectService.updateObject(contrat);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectu�e dans la base",
							"SUCCES"));
			contrat= new Contrat();
			desactiver();setEtatBouton(true);
			//vider();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				//logger.error("Erreur lors de la modification ", e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
		         e.printStackTrace();
				System.out.println("Enregistrement non effectu�");
		}
	}
	

public void deleteclt() {
	try {
		
		//objectService.deleteObject(profilUtilisateur);
		this.listeclient.remove(client);
		objectService.deleteObject(client);
		client = new Client();
		
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
	

public void deletecontrat() {
	try {
		
		//objectService.deleteObject(profilUtilisateur);
		this.listecontratmaj.remove(contrat);
		objectService.deleteObject(contrat);
		contrat= new Contrat();
		desactiver();
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


	public List<SelectItem> getElements() {
		 if (elements == null) {
			 elements = new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Utilisateur")) {
		
		        	elements.add(new SelectItem(  ((Utilisateur) obj).getCodeUtilisateur(),   ((Utilisateur) obj).getLoginUtilisateur()));
		        
			 }
		    }
		    return elements;
			
		}

	

	public java.lang.Integer getActivite() {
		return activite;
	}

	public void setActivite(java.lang.Integer activite) {
		this.activite = activite;
	}

	public java.lang.String getMailUtilisateur() {
		return mailUtilisateur;
	}

	public void setMailUtilisateur(java.lang.String mailUtilisateur) {
		this.mailUtilisateur = mailUtilisateur;
	}

	
	

	

	public List<SelectItem> getElementmaison() {
		
		
		
		 if (elementmaison == null) {
			 elementmaison= new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Maison")) {
		
				 elementmaison.add(new SelectItem(  ((Maison) obj).getCodeMais(),   ((Maison) obj).getDsgMais()));
		        
			 }
		    }
		return elementmaison;
	}

	public void setElementmaison(List<SelectItem> elementmaison) {
		this.elementmaison = elementmaison;
	}

	public String getSelectedmaison() {
		return selectedmaison;
	}

	public void setSelectedmaison(String selectedmaison) {
		this.selectedmaison = selectedmaison;
	}

	public RequeteUtilisateur getRequeteUtilisateur() {
		return requeteUtilisateur;
	}

	public void setRequeteUtilisateur(RequeteUtilisateur requeteUtilisateur) {
		this.requeteUtilisateur = requeteUtilisateur;
	}

	public java.lang.String getMatricule() {
		return matricule;
	}

	public void setMatricule(java.lang.String matricule) {
		this.matricule = matricule;
	}

	

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	

	public void setElements(List<SelectItem> elements) {
		this.elements = elements;
	}

	public Profil getMonProfil() {
		return monProfil;
	}

	public void setMonProfil(Profil monProfil) {
		this.monProfil = monProfil;
	}

	/*public List<Utilisateur> getListeUser() {
		listeUser.clear();
	
		listeUser= new ArrayList<Utilisateur>();
		List<Object> listObject = getObjectService().getObjects("Utilisateur");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Utilisateur utilisateur = (Utilisateur) it.next();
			try {
				listeUser.add(utilisateur);
			} catch (Exception e) {
			}
		
	}
		return listeUser;
	}

	public void setListeUser(List<Utilisateur> listeUser) {
		this.listeUser = listeUser;
	}*/

	public List<SelectItem> getElementsexe() {
		
		 if (elementsexe == null) {
			 elementsexe= new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Sexe")) {
		      elementsexe.add(new SelectItem(((Sexe) obj).getCodeSexe(), ((Sexe) obj).getLibelleSexe()));
		        
			 }
		    }
		   
		
		return elementsexe;
	}

	public void setElementsexe(List<SelectItem> elementsexe) {
		this.elementsexe = elementsexe;
	}

	public String getSelectedsexe() {
		return selectedsexe;
	}

	public void setSelectedsexe(String selectedsexe) {
		this.selectedsexe = selectedsexe;
	}

	public List<SelectItem> getElementnat() {
		
		
		 if (elementnat == null) {
			 elementnat = new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Nationalite")) {
		      elementnat.add(new SelectItem(((Nationalite) obj).getCodeNat(), ((Nationalite) obj).getLibelleNat()));
		        
			 }
		    }
		   
		return elementnat;
	}

	public void setElementnat(List<SelectItem> elementnat) {
		this.elementnat = elementnat;
	}

	public String getSelectednat() {
		return selectednat;
	}

	public void setSelectednat(String selectednat) {
		this.selectednat = selectednat;
	}

	

	
	

	public void setElementadm(List<SelectItem> elementadm) {
		this.elementadm = elementadm;
	}

	public String getSelectedadm() {
		return selectedadm;
	}

	public void setSelectedadm(String selectedadm) {
		this.selectedadm = selectedadm;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	
	public Nationalite getNationalite() {
		return nationalite;
	}

	public void setNationalite(Nationalite nationalite) {
		this.nationalite = nationalite;
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

	

	

	public List<SelectItem> getElementadm() {
		return elementadm;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Client> getListeclient() {
	
		listeclient.clear();
		
//		listeclient= new ArrayList<Client>();
//		List<Object> listObject = getObjectService().getObjects("Client");
//		for (Iterator it = listObject.iterator(); it.hasNext();) {
//			Client clt = (Client) it.next();
//			try {
//				listeclient.add(clt);
//			} catch (Exception e) {
//			}
//		
//	}
		return listeclient;
	}

	public void setListeclient(List<Client> listeclient) {
		this.listeclient = listeclient;
	}

	

	public Maison getMaison() {
		return maison;
	}

	public void setMaison(Maison maison) {
		this.maison = maison;
	}





	public Contrat getContrat() {
		return contrat;
	}





	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}





	










	

	

	public BigDecimal getQteTerSup() {
		return qteTerSup;
	}

	public void setQteTerSup(BigDecimal qteTerSup) {
		this.qteTerSup = qteTerSup;
	}

	public BigDecimal getCoutUnitSup() {
		return coutUnitSup;
	}

	public void setCoutUnitSup(BigDecimal coutUnitSup) {
		this.coutUnitSup = coutUnitSup;
	}

	public BigDecimal getCoutTotalSup() {
		return coutTotalSup;
	}

	public void setCoutTotalSup(BigDecimal coutTotalSup) {
		this.coutTotalSup = coutTotalSup;
	}

	public ManagedConnexion getManagedConnexion() {
		return managedConnexion;
	}





	public void setManagedConnexion(ManagedConnexion managedConnexion) {
		this.managedConnexion = managedConnexion;
	}





	public List<Contrat> getListecontrat() {
		
		
		
		listecontrat.clear();
		
		listecontrat= new ArrayList<Contrat>();
		List<Object> listObject = getObjectService().getObjects("Contrat");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Contrat clt = (Contrat) it.next();
			try {
				listecontrat.add(clt);
			} catch (Exception e) {
			}
		
	}
		return listecontrat;
	}





	public void setListecontrat(List<Contrat> listecontrat) {
		this.listecontrat = listecontrat;
	}





	public List<Contrat> getListecontratparperiode() {
		return listecontratparperiode;
	}





	public void setListecontratparperiode(List<Contrat> listecontratparperiode) {
		this.listecontratparperiode = listecontratparperiode;
	}





	public Date getDateDeb() {
		return dateDeb;
	}





	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}





	public Date getDateFin() {
		return dateFin;
	}





	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}





	public String getCritere() {
		return critere;
	}





	public void setCritere(String critere) {
		this.critere = critere;
	}


	private String option;



	public String getOption() {
			return option;
		}





		public void setOption(String option) {
			this.option = option;
		}





	public Client getClientrech() {
		return clientrech;
	}





	public void setClientrech(Client clientrech) {
		this.clientrech = clientrech;
	}





	public Contrat getContratrech() {
		return contratrech;
	}





	public void setContratrech(Contrat contratrech) {
		this.contratrech = contratrech;
	}

	public List<Contrat> getListecontratrech() {
		return listecontratrech;
	}





	public void setListecontratrech(List<Contrat> listecontratrech) {
		this.listecontratrech = listecontratrech;
	}





	public Client getClientselected() {
		return clientselected;
	}





	public void setClientselected(Client clientselected) {
		this.clientselected = clientselected;
	}





	public Contrat getContratselected() {
		return contratselected;
	}





	public void setContratselected(Contrat contratselected) {
		this.contratselected = contratselected;
	}



	public String getTypeFiltre() {
		return typeFiltre;
	}



	public void setTypeFiltre(String typeFiltre) {
		this.typeFiltre = typeFiltre;
	}



	public String getFiltre() {
		return filtre;
	}



	public void setFiltre(String filtre) {
		this.filtre = filtre;
	}



	public List<Contrat> getListecontratmaj() {
		return listecontratmaj;
	}



	public void setListecontratmaj(List<Contrat> listecontratmaj) {
		this.listecontratmaj = listecontratmaj;
	}



	public String getCriteremjcontrat() {
		return criteremjcontrat;
	}



	public void setCriteremjcontrat(String criteremjcontrat) {
		this.criteremjcontrat = criteremjcontrat;
	}

	public boolean isEtatSup() {
		return etatSup;
	}

	public void setEtatSup(boolean etatSup) {
		this.etatSup = etatSup;
	}

	

	
	
}
