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
import com.gestion.model.Contrat;
import com.gestion.model.Image;
import com.gestion.model.PrototypeMaison;
import com.gestion.model.Sexe;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedimageproto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedimageproto.class);
	
	private Image image = new Image();
	
	private List<Image> listeimage = new ArrayList<>();
private PrototypeMaison prototype= new PrototypeMaison();
	
	private List<PrototypeMaison> listeprototype = new ArrayList<>();
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
    private int i;
    private UploadedFile file;
    private File repectoire;
    private String lib;
    private List<String> listefile;

   	private String destination = "C:/PhotoPrototype";

   	public void upload(FileUploadEvent event) {
   		
   		
   	  repectoire = new File("C:/Dossierphoto/PhotoPrototype");
   	if(!repectoire.exists())  {
		repectoire.mkdirs();
   	}
   		FacesMessage msg = new FacesMessage("Fichier telechargé! ");
   		//for( i= 0;i<4;i++)  {
   			
   		lib =event.getFile().getFileName();
   		//listefile.add(lib);
   		//i++;
   		//}
   		
   		FacesContext.getCurrentInstance().addMessage(null, msg);
   		
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
   			OutputStream out = new FileOutputStream(new File(repectoire	+ "/" + fileName));

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
			//for (i= 0;i<listefile.size();i++) {
			
		   		
				image.setLibelleImage(lib);
		   		image.setCodeImage(getIdGenerateur().getIdimage());
				
				
				image.setPrototypeMaison(prototype);
				//image.setCodeImage("");
				getObjectService().addObject(image);
				//i++;
				//}
			//
			
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succès d'enregistrement", "Enregistrement effectué"));
		
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		image = new Image();
		prototype = new PrototypeMaison();
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
		 prototype = new PrototypeMaison();
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


	public PrototypeMaison getPrototype() {
		return prototype;
	}


	public void setPrototype(PrototypeMaison prototype) {
		this.prototype = prototype;
	}


	public List<PrototypeMaison> getListeprototype() {
		
		if (listeprototype.isEmpty()) {
			listeprototype = getObjectService().getObjects("PrototypeMaison");
		}
		return listeprototype;
	}


	public void setListeprototype(List<PrototypeMaison> listeprototype) {
		this.listeprototype = listeprototype;
	}

	public File getRepectoire() {
		return repectoire;
	}

	public void setRepectoire(File repectoire) {
		this.repectoire = repectoire;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<String> getListefile() {
		return listefile;
	}

	public void setListefile(List<String> listefile) {
		this.listefile = listefile;
	}


	


	

	

}
