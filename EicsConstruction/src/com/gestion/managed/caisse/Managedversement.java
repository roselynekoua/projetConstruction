package com.gestion.managed.caisse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managedbean.admin.ManagedConnexion;
import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Maison;
import com.gestion.model.Versement;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedversement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	ManagedConnexion managedConnexion;
	@Autowired
	IdGenerateur idGenerateur;
	
	private static Logger logger = Logger.getLogger(Managedversement.class);
	
	
	
	private Client client= new Client();
	private Contrat contrat= new Contrat();
	private Versement versement = new Versement();
	
	private Client selectclient= new Client();
	 private BigDecimal coutTotal = new BigDecimal(0);

	private List<SelectItem> elementclient; 
	 private String selectedclient; 
	private List<Versement> listeversement = new ArrayList<>();
	
	private List<Client> listeclient = new ArrayList<>();
	private List<Contrat> listecontrat = new ArrayList<>();
	private Contrat selectcontrat= new Contrat();
	private String typeFiltre;
	private String filtre;
	private Facture facture= new Facture();
	private BigDecimal montantFact = new BigDecimal(0);
	private BigDecimal resteAPayerFact = new BigDecimal(0);
	private BigDecimal reste = new BigDecimal(0);
	private BigDecimal remiseFact = new BigDecimal(0);
	private BigDecimal montanttotal = new BigDecimal(0);
	private BigDecimal montantProjet= new BigDecimal(0);
	private boolean etatBouton=true;
    private boolean etatAnnuler=true;
    private boolean etatimprimer=true;
    private InputText montantInput;
	private InputText checqueInput;
	
	
	private FileUpload checqueInput2;
	private String typeVers;
	private SelectOneMenu modeOneMenu;
	private Date date=Calendar.getInstance().getTime();
	 private String destination ="C:/Users/rosyj3a/Dossierphoto/photocheque";
	
	
	 public void upload(FileUploadEvent event) {  
    	 versement.setNchequeVers( event.getFile().getFileName());
    	
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
              OutputStream out = new FileOutputStream(new File(destination + fileName));

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
	
    public void activerCheque(){
    	System.out.println("Méthode activation called");
		if(typeVers.equals("Cheque")){
			checqueInput2.setDisabled(false);
			checqueInput2.setRequired(true);
			
		}else{
			checqueInput2.setDisabled(true);
			checqueInput2.setRequired(false);
			
		}		
	}
    
	public void enregistrerversement(){
		try{
			
		versement.setCodeVers(getIdGenerateur().getIdversement());
	versement.setFacture(facture);;
		versement.setUtilisateur(getManagedConnexion().getUtilisateur());
		versement.setTypeVers(typeVers);
		versement.setDateVers(date);
		
		getObjectService().addObject(versement);
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		//Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		//sessionMap.clear();
		//versement = new Versement();vider();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	
	
	 public void onRowSelect(SelectEvent event) {
			
			
			
			 setContrat(selectcontrat);
			 setClient(getSelectcontrat().getClient());
			setFacture(getSelectcontrat().getFacture());
				
			
	//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Une erreur s'est produite lors de traitement","ERREUR"));
			
			}
	 
	public void filtrer() {
		try {
			getListecontrat().clear();
			getListecontrat().addAll(objectService.filtrercontrat(typeFiltre, filtre));
			if (getListecontrat().size()==0){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Entrer un bon critère de recherche",
								"Erreur"));
				//setFiltre(null);
			}
			else{
				//calculer();
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
	
	public void calculer() {
		
		setCoutTotal(selectcontrat.getPrototypeMaison().getCoutTtcPrototype().multiply(new BigDecimal(selectcontrat.getQteAcq())));
		
		// setCoutTotal(prototype.getCoutTtcPrototype().multiply(getPrototype().getQteSouscrPrototype()));
	}
	
	public void enregistrer() {
		try {
			enregistrerfacture();
		enregistrerversement();
		
		setEtatAnnuler(true);
		setEtatBouton(true);
		setEtatimprimer(false);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		//setFacture(new Facture());  vider();		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Enregistrement effectué"));
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ", "Echec d'enregistrement !"));
	         e.printStackTrace();	// TODO: handle exception
		}	
//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Une erreur s'est produite lors de traitement","ERREUR"));
		
		}
	
	public void enregistrerfacture(){
		try{
		//facture.setCodeFact(getIdGenerateur().getIdfacture());
	    facture.setContrat(selectcontrat);
		facture.setUtilisateur(getManagedConnexion().getUtilisateur());

		//montantpaye
		facture.setApportInitial(montantFact);
	    facture.setMontantTtcFact(coutTotal);
        facture.setRemiseFact(new BigDecimal(0));
		facture.setDateFact(Calendar.getInstance().getTime());
		
		getObjectService().updateObject(facture);
	   
		
		
		//Maison maison= contrat.getMaison().getCoutTotalMais();
		//test
		//contrat.setCoutMtRestant(resteAPayerFact);
		//contrat.setCoutTotalSup(resteAPayerFact);
	//	getObjectService().updateObject(contrat);
		//montanttotal= getContrat().getMaison().getCoutTotalMais();
		
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//logger.error("Erreur lors de l'enregistrement ", e);
			
	         e.printStackTrace();
			System.out.println("Enregistrement non effectué");		
	}
	}
	
	public void miseajr() {
		montantFact= new BigDecimal(0);
		montantFact = getVersement().getMontantVers();
		
//		setMontanttotal(getContrat().getMaison().getCoutTotalMais());
//		setMontantProjet(getContrat().getMaison().getCoutTotalMais().add(getContrat().getCoutTotalSup()));
		
	//	resteAPayerFact =getContrat().getFacture().getMontantTtcFact().subtract(versement.getMontantVers());
		//resteAPayerFact =getContrat().getCoutMtRestant().subtract(versement.getMontantVers());
		//resteAPayerFact = getContrat().getMaison().getCoutTotalMais().add(getContrat().getCoutTotalSup()).subtract(versement.getMontantVers());
		//montantFact =+ getContrat().getMaison().getTarif().getCoutTotalTarif() -getMontantTtc();
		
		activer();	
		
		System.out.println("montannt et reste a payer   +++++++++++++++++++++++++++++"+ getMontantFact()  +"" + getResteAPayerFact());		
			
	
		}

	
	public void recupererclient(){
		
	setClient((Client) getObjectService().getObjectById(selectedclient, "Client"));

}
public void update() {
		
		try {
			
			objectService.updateObject(versement);
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effectuée dans la base",
							"SUCCES"));
			versement = new Versement();
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
			this.listeversement.remove(versement);
			objectService.deleteObject(versement);
			versement = new Versement();
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
		versement = new Versement();
		setSelectedclient(null);
		client = new Client();
		facture = new Facture();
		contrat = new Contrat();
		setTypeVers("");
		setMontantProjet(new BigDecimal(0));
		setMontanttotal(new BigDecimal(0));
		setEtatBouton(true);
		setMontantFact(new BigDecimal(0));
		setResteAPayerFact(new BigDecimal(0));
		
		setRemiseFact(new BigDecimal(0));
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

	


	public Client getSelectclient() {
		return selectclient;
	}


	public void setSelectclient(Client selectclient) {
		this.selectclient = selectclient;
	}
	


	public Client getClient() {
		return client;
	}





	public List<SelectItem> getElementclient() {
		
		if (elementclient == null) {
			elementclient = new ArrayList<SelectItem>();
			try {
				for (Object obj : getObjectService().getObjects("Client")) {
	   
					elementclient.add(new SelectItem(((Client) obj)
							.getNumClt(), ((Client) obj).getNomClt()));

				}
			} catch (Exception e) {

			}
		}
		
		
		
		
		return elementclient;
	}





	public void setElementclient(List<SelectItem> elementclient) {
		this.elementclient = elementclient;
	}





	public String getSelectedclient() {
		return selectedclient;
	}





	public void setSelectedclient(String selectedclient) {
		this.selectedclient = selectedclient;
	}





	public void setClient(Client client) {
		this.client = client;
	}





	public Versement getVersement() {
		return versement;
	}





	public void setVersement(Versement versement) {
		this.versement = versement;
	}








	public List<Versement> getListeversement() {
		
		if (listeversement.isEmpty()) {
			listeversement = getObjectService().getObjects("Versement");
		}
		return listeversement;
	}





	public void setListeversement(List<Versement> listeversement) {
		this.listeversement = listeversement;
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




	public ManagedConnexion getManagedConnexion() {
		return managedConnexion;
	}




	public void setManagedConnexion(ManagedConnexion managedConnexion) {
		this.managedConnexion = managedConnexion;
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




	public Facture getFacture() {
		return facture;
	}




	public List<Contrat> getListecontrat() {
		return listecontrat;
	}


	public void setListecontrat(List<Contrat> listecontrat) {
		this.listecontrat = listecontrat;
	}


	public Contrat getSelectcontrat() {
		return selectcontrat;
	}


	public void setSelectcontrat(Contrat selectcontrat) {
		this.selectcontrat = selectcontrat;
	}


	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public List<Client> getListeclient() {
		return listeclient;
	}

	public void setListeclient(List<Client> listeclient) {
		listeclient = listeclient;
	}


	public Contrat getContrat() {
		return contrat;
	}


	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}



	public BigDecimal getMontantFact() {
		return montantFact;
	}



	public void setMontantFact(BigDecimal montantFact) {
		this.montantFact = montantFact;
	}



	public BigDecimal getResteAPayerFact() {
		return resteAPayerFact;
	}



	public void setResteAPayerFact(BigDecimal resteAPayerFact) {
		this.resteAPayerFact = resteAPayerFact;
	}



	public BigDecimal getRemiseFact() {
		return remiseFact;
	}



	public void setRemiseFact(BigDecimal remiseFact) {
		this.remiseFact = remiseFact;
	}



	public BigDecimal getMontanttotal() {
		return montanttotal;
	}



	public void setMontanttotal(BigDecimal montanttotal) {
		this.montanttotal = montanttotal;
	}



	public BigDecimal getReste() {
		return reste;
	}



	public void setReste(BigDecimal reste) {
		this.reste = reste;
	}



	public BigDecimal getMontantProjet() {
		return montantProjet;
	}



	public void setMontantProjet(BigDecimal montantProjet) {
		this.montantProjet = montantProjet;
	}
	public InputText getMontantInput() {
		return montantInput;
	}
	public void setMontantInput(InputText montantInput) {
		this.montantInput = montantInput;
	}
	public InputText getChecqueInput() {
		return checqueInput;
	}
	public void setChecqueInput(InputText checqueInput) {
		this.checqueInput = checqueInput;
	}
	public String getTypeVers() {
		return typeVers;
	}
	public void setTypeVers(String typeVers) {
		this.typeVers = typeVers;
	}
	public SelectOneMenu getModeOneMenu() {
		return modeOneMenu;
	}
	public void setModeOneMenu(SelectOneMenu modeOneMenu) {
		this.modeOneMenu = modeOneMenu;
	}
	public boolean isEtatimprimer() {
		return etatimprimer;
	}
	public void setEtatimprimer(boolean etatimprimer) {
		this.etatimprimer = etatimprimer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public FileUpload getChecqueInput2() {
		return checqueInput2;
	}


	public void setChecqueInput2(FileUpload checqueInput2) {
		this.checqueInput2 = checqueInput2;
	}


	public BigDecimal getCoutTotal() {
		return coutTotal;
	}


	public void setCoutTotal(BigDecimal coutTotal) {
		this.coutTotal = coutTotal;
	}


	


	


	
	
	

	

}
