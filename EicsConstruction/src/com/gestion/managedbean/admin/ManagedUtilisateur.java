package com.gestion.managedbean.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Nationalite;
import com.gestion.model.Profil;
import com.gestion.model.Sexe;
import com.gestion.model.Utilisateur;
import com.gestion.objetDao.RequeteUtilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class ManagedUtilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	
   @Autowired
	ManagedProfil managedProfil;
   @Autowired
	RequeteUtilisateur requeteUtilisateur;
   @Autowired
   IdGenerateur idGenerateur;
	
   private boolean etatBouton=true;
   private boolean etatAnnuler=true;
	private Utilisateur utilisateur = new Utilisateur();
	private Utilisateur utilisateurselected = new Utilisateur();
	private Sexe sexe = new Sexe();
	
	private Nationalite nationalite = new Nationalite();
	
	private Profil monProfil = new Profil();
	 private List<SelectItem> elements; 
	 private List<SelectItem> elementprofil;
	 private String selectedProfil; 
	 
	 private List<SelectItem> elementsexe; 
	 private String selectedsexe; 
	 
	 private List<SelectItem> elementf; 
	 private String selectedf;
	 private List<SelectItem> elementnat; 
	 private String selectednat; 
	 private List<SelectItem> elementpro; 
	 private String selectedpro; 
	 private List<SelectItem> elementadm; 
	 private String selectedadm; 
	 private String photo; 
	 
	 private List<Integer> selectedActive;
	 

	 
	 private List<Utilisateur> listeUser = new ArrayList<>();
	// primary key
	private java.lang.String id;

    @Autowired
	ObjectService objectService;

	// fields
	private java.lang.String nomUtilisateur;
	private java.lang.String prenomUtilisateur;
	private java.lang.String mailUtilisateur;
	private java.lang.String loginUtilisateur;
	private java.lang.String motPasse;
	private java.lang.String matricule;
	private java.util.Date dateCreationMotPasse;
	private java.lang.Integer activite;
	
	
	
	
	
	

	private UploadedFile file;
	private File repectoire;
	private String imagefile;
	private String imagefile2;
	     private String destination ="c:/Dossierphoto/Utilisateurs/";

	     
	    
	     public void action() {
	    	//  repectoire = "C:/Dossierphoto/Utilisateurs";
	    	 setImagefile(destination +getUtilisateurselected().getPhotoUt());
	    	// setImagefile2("c:/Dossierphoto/Utilisateurs/2.jpg");
	    	 System.out.println("methode pr recup image, I/O error");
	     }
	     
	     public void showImage() {
	    	 //setImagefile(destination +getUtilisateurselected().getPhotoUt());
	     	action();
	         try {
	             // Get image file.
	             FileInputStream in = new FileInputStream(getImagefile());

	             // Get image contents.
	             int length = in.available();
	             byte[] bytes = new byte[length];
	             in.read(bytes);
	             in.close();

	             // Get response.
	             FacesContext context = FacesContext.getCurrentInstance();
	             HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	             
	             // Write image contents to response.
	             response.setContentType("image/jpeg,image/png,image/jpg");
	             response.setContentLength(length);
	             response.getOutputStream().write(bytes);
	             context.responseComplete();
	         } catch (IOException e) {
	             System.out.println("Showing image failed, I/O error");
	         }
	     }
	     
	     
	     public void onRowSelect(SelectEvent event) {
	    	 getListeUser().clear();
	 		setUtilisateur(utilisateurselected);
	 		showImage();
	 		// setImagefile(repectoire + "/" +getUtilisateurselected().getPhotoUt());
	 		System.out.println("++++++++++++++++++++++++++++++++utlisateu delectioneeeee"+getUtilisateur().getNomUtilisateur());
	 		//RequestContext.getCurrentInstance().execute("Dialog.show();");
	 	}

	     
	     
	      public void upload(FileUploadEvent event) {  
	    	  
	    	  repectoire = new File("C:/Dossierphoto/Utilisateurs");
	    		if(!repectoire.exists())  {
	    			repectoire.mkdirs();
	    	   	}
	    		

	    	 utilisateur.setPhotoUt(event.getFile().getFileName());
	    	
	          FacesMessage msg = new FacesMessage("Fichier télechargé! ", event.getFile().getFileName()+ " est telechargé.");  
	          
	         // client.setPhotoClt(event.getFile().getContentType());
	          FacesContext.getCurrentInstance().addMessage(null, msg);
	          // Do what you want with the file        
	          try {
	              copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
	          } catch (IOException e) {
	              e.printStackTrace();
	          }

	      }  

	      public void copyFile(String fileName, InputStream in) {
	             try {


	                  // write the inputStream to a FileOutputStream
	                  OutputStream out = new FileOutputStream(new File(repectoire + "/" + fileName));

	                  int read = 0;
	                  byte[] bytes = new byte[1024];

	                  while ((read = in.read(bytes)) != -1) {
	                      out.write(bytes, 0, read);
	                  }

	                  in.close();
	                  out.flush();
	                  out.close();

	                  System.out.println("New file created!");
	                  } catch (IOException e) {
	                  System.out.println(e.getMessage());
	                  }
	      }
	      
	      
	      public String getImagefile() {
	          return imagefile;
	      }

	      public void setImagefile(String imagefile) {
	          this.imagefile = imagefile;
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
	

	public ManagedProfil getManagedProfil() {
		return managedProfil;
	}

	public void setManagedProfil(ManagedProfil managedProfil) {
		this.managedProfil = managedProfil;
	}

	
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

	/**
	 * Set the value related to the column: MOT_PASSE
	 * 
	 * @param motPasse
	 *            the MOT_PASSE value
	 */
	public void setMotPasse(java.lang.String motPasse) {
		this.motPasse = motPasse;
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
	
	
	public void creerIdUtilisateur(){
		
		String pseudo = "USER";
		String formId = getObjectService().getCodeTable(pseudo, 4, 2,
				"utilisateur", "CODE_UTILISATEUR");
		
		utilisateur.setCodeUtilisateur(formId);
		//return formId;
		/*Random rdm = new Random();
		int y = 64;
		while (!(64 < y && y < 91)) {
			y = rdm.nextInt(100);
		}

		char V = (char) y;
		String l = Character.toString(V);
		// (pseudo, taillCar, taillChifr,nomTable, nomCOL);
		String key = getObjectService().getCodeTable("KUSER", 4, 3,
				"utilisateur", "CODE_UTILISATEUR");

		utilisateur.setCodeUtilisateur(key + l);*/
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

public void desactiver() {
		
		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {
		
		setEtatAnnuler(false);
		setEtatBouton(false);
	}
	
public void verifiercombo() {
	if(selectedProfil.equalsIgnoreCase("choisir") )
	{
		
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Selectionner chp obligatoire",
                          "Selectionner chp obligatoire"));

	}
	if( (selectedsexe.equalsIgnoreCase("Choisir ") ))
	{
		
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Selectionner chp obligatoire",
                          "Selectionner chp obligatoire"));

	}
	
	}
	
	
	public void addUtilisateur() {//Repriz by ALekerand
		try{
		//vérifier que l' Login n'est pas déjà en Base
		System.out.println("Etat:"+verifierLogin());
		if(selectedProfil.equals(" ") )
		{
			
	            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                           "Selectionner chp obligatoire",
	                          "Selectionner chp obligatoire"));
	
		}
		
		
		
		
		
		else
		{
		if (verifierLogin()==false){
			// Recupération des données Affichées
			utilisateur.setSexe(sexe);
			utilisateur.setProfil(monProfil);
			utilisateur.setNationalite(nationalite);
			
			 // utilisateur.setPhotoUt(event.getFile().getFileName());
					creerIdUtilisateur();
					utilisateur.setDateCreationUtilisateur(Calendar.getInstance().getTime());
					activaterCompte();
					
					//Renseigner le profil
				//	recupererProfile();
					/*profilUtilisateurId.setCodeProfil(getMonProfil().getCodeProfil());
					profilUtilisateurId.setCodeUtilisateur(getUtilisateur().getCodeUtilisateur());
					
					profilUtilisateur.setId(profilUtilisateurId);
					profilUtilisateur.setDateProfilUtilisateur(Calendar.getInstance().getTime());
					*/
					
					getObjectService().addObject(utilisateur);
					System.out.println("Utilisateur enregistré avec succès!");
					
					System.out.println("ProfilUtilisateur enregistré avec succès!");
					//utilisateur = new Utilisateur();
					//setSelectedProfil("");
					
					resetUtilisateur();
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès d'enregistrement ", "Succès  !"));
			       
					
		}else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login déjà utilisé. Veuillez entrer un autre", ""));  
		}}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec  d'enregistrement", "Echec  !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");	
	}
		}
	
	public boolean verifierLogin(){//By ALekerand
		return getRequeteUtilisateur().chercherLogin(getUtilisateur().getLoginUtilisateur());
	}
	
	public void recupererProfile(){
		//Recupérer l'objet profil en base
	//Profil monProfil = (Profil) getObjectService().getObjectById(getSelectedProfil(), "Profil");
	setMonProfil((Profil) getObjectService().getObjectById(selectedProfil, "Profil"));
	
	
}
	
	public void recuperersexe(){
		
	setSexe((Sexe) getObjectService().getObjectById(selectedsexe, "Sexe"));

}
	


	public void recuperernat(){
		
	setNationalite((Nationalite) getObjectService().getObjectById(selectednat, "Nationalite"));
	//verifiercombo();

}
	
	
	
	
	
	
	
	
	
	/*
	public Profil recupererProfile(){//By ALekerand
			//Recupérer l'objet profil en base
		Profil monProfil = (Profil) getObjectService().getObjectById(getSelectedProfil(), "Profil");
		return monProfil;
		
	}
	
	
	public void Recuppersmed() {
		setPersMedical((PersMedical) getObjectService().getObjectById(
				idpers, "PersMedical"));
		
	}*/
	
	public void activaterCompte(){//By ALekerand
		if(selectedActive.size()<1){
			utilisateur.setActivite(false);
		}else{
			utilisateur.setActivite(true);
		}
		
	}
	
	public void reset() {
		setMotPasse(null);
		utilisateur.setMotPasse(null);
		utilisateur = new Utilisateur();
		setEtatBouton(true);
	}
	

	public void resetUtilisateur() {
		//managedProfil.reset();
		
		//setUtilisateur(null);
		 utilisateur = new Utilisateur();
		
		 sexe = new Sexe();
		
		 nationalite = new Nationalite();
		
		 
		 monProfil = new Profil();
		 
		 setSelectedpro(null);
		 setSelectedsexe(null);
		 setSelectedf(null);
		 setSelectednat(null);
		 setSelectedProfil("");
		//reinitialiser l'activation du compte
		selectedActive.clear();
		
		
	}
	
	
	
public void update() {
		
		try {
			
			objectService.updateObject(utilisateur);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			utilisateur = new Utilisateur();
			desactiver();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				//logger.error("Erreur lors de la modification ", e);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec de modification !"));
		         e.printStackTrace();
				System.out.println("Enregistrement non effectué");
		}
	}
	

public void delete() {
	try {
		
		//objectService.deleteObject(profilUtilisateur);
		this.listeUser.remove(utilisateur);
		objectService.deleteObject(utilisateur);
		utilisateur = new Utilisateur();
		
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
	
	public List<SelectItem> getElements() {
		 if (elements == null) {
			 elements = new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Utilisateur")) {
		
		        	elements.add(new SelectItem(  ((Utilisateur) obj).getCodeUtilisateur(),   ((Utilisateur) obj).getLoginUtilisateur()));
		        
			 }
		    }
		    return elements;
			
		}

	public String getSelectedProfil() {
		return selectedProfil;
	}

	public void setSelectedProfil(String selectedProfil) {
		this.selectedProfil = selectedProfil;
	}

	public java.lang.String getLoginUtilisateur() {
		return loginUtilisateur;
	}

	public void setLoginUtilisateur(java.lang.String loginUtilisateur) {
		this.loginUtilisateur = loginUtilisateur;
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

	
	public List<Integer> getSelectedActive() {
		return selectedActive;
	}

	public void setSelectedActive(List<Integer> selectedActive) {
		this.selectedActive = selectedActive;
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

	public List<SelectItem> getElementprofil() {
		
		
		
		 if (elementprofil == null) {
			 elementprofil = new ArrayList<SelectItem>();
			 for (Object obj : getObjectService().getObjects("Profil")) {
		      elementprofil.add(new SelectItem(((Profil) obj).getCodeProfil(), ((Profil) obj).getLibelleProfil()));
		        
			 }
		    }
		   
			
		return elementprofil;
	}

	public void setElementprofil(List<SelectItem> elementprofil) {
		this.elementprofil = elementprofil;
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

	
	
	
	public List<Utilisateur> getListeUser() {
		listeUser.clear();
	
	//	listeUser= new ArrayList<Utilisateur>();
		listeUser = getObjectService().getObjects("Utilisateur");
		
		
		/*for (Iterator it = listObject.iterator(); it.hasNext();) {*/
		/*for(Utilisateur util: listObject  ){
		
			try {
				listeUser.add(utilisateur);
				
				//  photo= "c:/Users/rosyj3a/Dossierphoto/"+ utilisateur.getPhotoUt();
			} catch (Exception e) {
			}
		
	}*/
		return listeUser;
	}

	public void setListeUser(List<Utilisateur> listeUser) {
		this.listeUser = listeUser;
	}

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

	

	public void setElementpro(List<SelectItem> elementpro) {
		this.elementpro = elementpro;
	}

	public String getSelectedpro() {
		return selectedpro;
	}

	public void setSelectedpro(String selectedpro) {
		this.selectedpro = selectedpro;
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

	

	public void setElementf(List<SelectItem> elementf) {
		this.elementf = elementf;
	}

	public String getSelectedf() {
		return selectedf;
	}

	public void setSelectedf(String selectedf) {
		this.selectedf = selectedf;
	}

	public List<SelectItem> getElementadm() {
		return elementadm;
	}

	

	public List<SelectItem> getElementpro() {
		return elementpro;
	}

	public String getPhoto() {
		
		setImagefile(repectoire + "/" + utilisateur.getPhotoUt());
		//photo= "c:/Users/rosyj3a/Dossierphoto/"+ utilisateur.getPhotoUt();
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public File getRepectoire() {
		return repectoire;
	}

	public void setRepectoire(File repectoire) {
		this.repectoire = repectoire;
	}

	public Utilisateur getUtilisateurselected() {
		return utilisateurselected;
	}

	public void setUtilisateurselected(Utilisateur utilisateurselected) {
		this.utilisateurselected = utilisateurselected;
	}

	public String getImagefile2() {
		return imagefile2;
	}

	public void setImagefile2(String imagefile2) {
		this.imagefile2 = imagefile2;
	}
	

	
	
}
