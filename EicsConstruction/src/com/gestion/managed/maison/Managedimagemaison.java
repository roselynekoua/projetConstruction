package com.gestion.managed.maison;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Batiment;
import com.gestion.model.Image;
import com.gestion.model.Maison;
import com.gestion.model.PrototypeMaison;
import com.gestion.model.Sexe;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedimagemaison implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedimagemaison.class);
	
private Image image = new Image();
	
	private List<Image> listeimage = new ArrayList<>();
private Maison maison= new Maison();
	
	private List<Maison> listemaison = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
	
    private UploadedFile file;


   	private String destination = "C:/Users/rosyj3a/Dossierphoto/PhotoMaison";

   	public void upload(FileUploadEvent event) {
   		FacesMessage msg = new FacesMessage("Fichier telechargé! ");
   		
   		image.setLibelleImage(event.getFile().getFileName());
   		
   		FacesContext.getCurrentInstance().addMessage(null, msg);
   		// Do what you want with the file
   		try {
   			copyFile(event.getFile().getFileName(), event.getFile()
   					.getInputstream());
   		} catch (IOException e) {
   			e.printStackTrace();
   		}

   	}

   	public void copyFile(String fileName, InputStream in) {
   		try {

   			// write the inputStream to a FileOutputStream
   			OutputStream out = new FileOutputStream(new File(destination
   					+ fileName));

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
    
    
    
    
    
    
    
    
	public void enregistrer(){
		try{
			
			image.setCodeImage(getIdGenerateur().getIdimage());
			image.setMaison(maison);
			
		getObjectService().addObject(image);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		image = new Image();
		maison= new Maison();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	public void chargerliste(){
		getObjectService().getojects(image);
		
	}
	
	
public void update() {
		
		try {
			
			objectService.updateObject(image);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			 image = new Image();
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
			this.listeimage.remove(image);
			objectService.deleteObject(image);
			 image = new Image();
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
		 image = new Image();
		 maison= new Maison();
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Image> getListeimage() {
		return listeimage;
	}

	public void setListeimage(List<Image> listeimage) {
		this.listeimage = listeimage;
	}

	public Maison getMaison() {
		return maison;
	}

	public void setMaison(Maison maison) {
		this.maison = maison;
	}

	public List<Maison> getListemaison() {
		
		
		
		if (listemaison.isEmpty()) {
			listemaison = getObjectService().getObjects("Maison");
		}
		return listemaison;
	}

	public void setListemaison(List<Maison> listemaison) {
		this.listemaison = listemaison;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}


	


	

	

}
