package com.gestion.managed.maison;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.gestion.model.Batiment;
import com.gestion.model.Client;
import com.gestion.model.Commune;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Maison;
import com.gestion.model.Nationalite;
import com.gestion.model.Pays;
import com.gestion.model.Profession;
import com.gestion.model.Profil;
import com.gestion.model.PrototypeMaison;
import com.gestion.model.Sexe;
import com.gestion.model.TypeClient;
import com.gestion.model.TypeMaison;
import com.gestion.model.Utilisateur;
import com.gestion.model.Ville;
import com.gestion.objetDao.RequeteUtilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedclient implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";

	@Autowired
	RequeteUtilisateur requeteUtilisateur;

	@Autowired
	ManagedConnexion managedConnexion;

	@Autowired
	IdGenerateur idGenerateur;

	@Autowired
	ObjectService objectService;

	private String critere;
	private String criteremjcontrat;
	private boolean etatBouton = true;
	private boolean etatAnnuler = true;
	private boolean etatImprimer = true;
	private Utilisateur utilisateur = new Utilisateur();
	private Sexe sexe = new Sexe();
	private List<Sexe> listesexe = new ArrayList<>();

	private Client client = new Client();
	private Nationalite nationalite = new Nationalite();
	private List<Nationalite> listenat = new ArrayList<>();

	private TypeClient typeclient = new TypeClient();
	private List<TypeClient> listetypeclient = new ArrayList<>();
	private Profession profession = new Profession();
	private List<Profession> listeprof = new ArrayList<>();

	private Pays pays = new Pays();
	private List<Pays> listepays = new ArrayList<>();
	private Commune commune = new Commune();
	private List<Commune> listecomune = new ArrayList<>();
	private Ville ville = new Ville();
	private List<Ville> listeville = new ArrayList<>();

	private Facture facture = new Facture();
	private List<Facture> listefacture = new ArrayList<>();
	private PrototypeMaison prototype = new PrototypeMaison();
	private List<PrototypeMaison> listeproto = new ArrayList<>();

	private Batiment batiment = new Batiment();
	private List<Batiment> listebatiment = new ArrayList<>();
	private TypeMaison typemaison = new TypeMaison();
	private List<TypeMaison> listetmaison = new ArrayList<>();

	private Maison maison = new Maison();
	private Contrat contrat = new Contrat();

	private Client clientrech = new Client();
	private Contrat contratrech = new Contrat();
	private Client clientselected = new Client();
	private Contrat contratselected = new Contrat();

	private Profil monProfil = new Profil();
	private List<SelectItem> elements;

	private List<Client> listeclient = new ArrayList<>();
	private List<Contrat> listecontrat = new ArrayList<>();
	private List<Contrat> listecontratparperiode = new ArrayList<>();
	private List<Contrat> listecontratrech = new ArrayList<>();
	private List<Contrat> listecontratmaj = new ArrayList<>();

	// primary key
	private java.lang.String id;

	private Date datecreaClt = Calendar.getInstance().getTime();
	private BigDecimal qteTerSup = new BigDecimal(0);
	private BigDecimal coutUnitSup = new BigDecimal(0);
	private BigDecimal coutTotalSup = new BigDecimal(0);
	private BigDecimal montantProjet = new BigDecimal(0);
	private BigDecimal fraisdossier = new BigDecimal(0);
	private BigDecimal coutTotal = new BigDecimal(0);
	
	private BigDecimal resteapayer = new BigDecimal(0);
	private BigDecimal montantdejapaye = new BigDecimal(0);
	
	private Integer nbreacquise = new Integer(0);
	private SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");

	private String typeFiltre;
	private String filtre;
	private UploadedFile file;

	private File repectoire;

	private String destination = "C:/Dossierphoto/Clients";

	public void upload(FileUploadEvent event) {
		
		
		repectoire = new File("C:/Dossierphoto/Clients");
		if(!repectoire.exists())  {
			repectoire.mkdirs();
	   	}
		FacesMessage msg = new FacesMessage("Fichier telechargé! ");
		// client.setPhotoClt(event.getFile().getContentType());
		client.setPhotoClt(event.getFile().getFileName());
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

	public void upload2() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void filtrer() {
		try {
			getListeclient().clear();
			getListeclient().addAll(
					objectService.filtrerclient(typeFiltre, filtre));
			if (getListeclient().size() == 0) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Entrer un bon critère de recherche",
										"Erreur"));
				// setFiltre(null);
			} else {

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
		System.out.println("Après Requête...........................");
		Contrat Ctrat = (Contrat) getObjectService().getObjectById(
				getCriteremjcontrat(), "Contrat");
		System.out.println("Après Requête...........................");

		if (Ctrat == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ce contrat n'existe pas", "AUCUNE DONNEE"));
		}

		else {
			setContrat(Ctrat);
		}

	}

/*	
public	class Payment {
	BigDecimal itemCost=new BigDecimal(BigInteger.ZERO, 2);
	BigDecimal totalCost=new BigDecimal(BigInteger.ZERO, 2); 
	public BigDecimal calculateCost(int itemQuantity,BigDecimal itemPrice) 
	{ 
		BigDecimal itemCost = itemPrice.multiply(new BigDecimal(itemQuantity)); return totalCost.add(itemCost); 
		} 
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * public void handleFileUpload(FileUploadEvent event) { FacesMessage
	 * message = new FacesMessage("Succesfull", event.getFile().getFileName() +
	 * " is uploaded."); // client.setPhotoClt(event.getFile().getContents());
	 * FacesContext.getCurrentInstance().addMessage(null, message);
	 * client.setBoitePostClt(event.getFile().getFileName()); } private
	 * UploadedFile file;
	 * 
	 * public UploadedFile getFile() { return file; }
	 * 
	 * public void setFile(UploadedFile file) { this.file = file; }
	 * 
	 * public void upload() { if(file != null) { FacesMessage message = new
	 * FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	 * FacesContext.getCurrentInstance().addMessage(null, message); } }
	 */

	public String handleflow(FlowEvent event) {
		String currentStepId = event.getNewStep();
		String stepToGo = event.getOldStep();
		/*
		 * if(skip) return "confirm"; else
		 */
		return event.getNewStep();
	}

	public void desactiver() {

		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void activer() {

		setEtatAnnuler(false);
		setEtatBouton(false);
	}

	
	public String nvoenregistrment() {
   vider();
		return "Pages/gestionClient/enregistrement_client.xhtml";
	}
	public void calculer() {
		// coutTotal = new BigDecimal(0);
		//setCoutTotal(prototype.getCoutTtcPrototype());
		//setMontantdejapaye(getRequeteUtilisateur().sommevers(facture));
		setCoutTotal(prototype.getCoutTtcPrototype().multiply(new BigDecimal(contrat.getQteAcq())));
		// setCoutTotal(prototype.getCoutTtcPrototype().multiply(getPrototype().getQteSouscrPrototype()));
	}

	
	//code a completer sur la page
	/*  
	<h:commandLink   id="but3" value="Nouvel enregistrement"
			
			actionListener="#{managedclient.nvoenregistrment}"
			update=":frm:buttonrep,frm" style="margin-left:20px"
			 />*/
	
	public void enregistrer() {
		try {

			creerIdclient();
			// Recupération des données Affichées
			client.setSexe(getSexe());

			client.setDatecreaClt(datecreaClt);
			client.setNationalite(nationalite);
			client.setProfession(profession);
			client.setPays(pays);
			client.setCommune(commune);
			client.setTypeClient(typeclient);
			client.setVille(ville);

			getObjectService().addObject(client);
			System.out.println("Client enregistré avec succès!");
			enregistrermaison();
			enregistrercontrat();
			
			
			desactiver();
			setEtatImprimer(false);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Succès d'enregistrement ", "Succès  !"));

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de l'enregistrement ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Echec  d'enregistrement", "Echec  !"));
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	public void enregistrercontrat() {
		try {

			contrat.setCodeContrat(getIdGenerateur().getIdcontrat());
			contrat.setClient(client);

			//contrat.setFacture(facture);
			contrat.setPrototypeMaison(prototype);
			contrat.setUtilisateur(getManagedConnexion().getUtilisateur());

			contrat.setMaison(maison);
			getObjectService().addObject(contrat);
			System.out.println("Contrat enregistré avec succès!");
			
			maison.setContrat(contrat);
			getObjectService().updateObject(maison);
			nbreacquise= getContrat().getQteAcq()+ prototype.getQteSouscrPrototype() ;
			prototype.setQteSouscrPrototype(nbreacquise);
			getObjectService().updateObject(prototype);
			
			System.out.println("+++++++++quantitr prototype::"+nbreacquise);
//contrat.getPrototypeMaison().setQteSouscrPrototype(getContrat().getQteAcq()+ contrat.getPrototypeMaison().getQteSouscrPrototype() );
			
			//eregistremet facture
			facture.setCodeFact(getIdGenerateur().getIdfacture());
			facture.setMontantTtcFact(coutTotal);
			facture.setRemiseFact(new BigDecimal(0));
			facture.setDateFact(Calendar.getInstance().getTime());
			facture.setUtilisateur(getManagedConnexion().getUtilisateur());
			facture.setContrat(contrat);
			getObjectService().addObject(facture);
			contrat.setFacture(facture);
			getObjectService().updateObject(contrat);
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	public void enregistrermaison() {
		try {
			maison.setBatiment(batiment);
			maison.setCodeMais(getIdGenerateur().getIdma(batiment));

			//maison.setContrat(contrat);
			maison.setTypeMaison(typemaison);
			maison.setUtilisateur(getManagedConnexion().getUtilisateur());

			
			/*contrat.setMaison(maison);
			getObjectService().updateObject(contrat);*/
			getObjectService().addObject(maison);
			System.out.println("maison enregistré avec succès!");

			

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Enregistrement maison non effectué");
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

	/*
	 * public void recuperersexe() {
	 * 
	 * setSexe((Sexe) getObjectService().getObjectById(selectedsexe, "Sexe"));
	 * 
	 * }
	 * 
	 * public void recuperernat() {
	 * 
	 * setNationalite((Nationalite) getObjectService().getObjectById(
	 * selectednat, "Nationalite"));
	 * 
	 * }
	 * 
	 * public void recuperermaison() {
	 * 
	 * setMaison((Maison) getObjectService().getObjectById(selectedmaison,
	 * "Maison"));
	 * 
	 * }
	 */

	public void cal() {

		// coutTotalSup = 0.00;
		coutTotalSup = coutUnitSup.multiply(qteTerSup);
		// coutTotalSup = qteTerSup*coutUnitSup;
		// coutTotalSup = qteTerSup.coutUnitSup;
		// montantProjet =
		// getMaison().getCoutTotalMais().add(getCoutTotalSup().add(fraisdossier));
	}

	public void calcontrat() {

		// contrat.setCoutTotalSup(contrat.getCoutUnitSup().multiply(
		// contrat.getQteTerSup()));
		// coutTotalSup = coutUnitSup.multiply(qteTerSup);
		// coutTotalSup = qteTerSup*coutUnitSup;
		// coutTotalSup = qteTerSup.coutUnitSup;
		// montantProjet =
		// getMaison().getCoutTotalMais().add(getCoutTotalSup());

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
							"Ce numéro de client n'est pas attribué!"));

		}

		else {
			for (Contrat contr : getClientrech().getContrats()) {
				getListecontratrech().add(contr);
			}
		}

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
							"Ce numéro de Contrat n'est pas attribué!"));

		} else {
			listecontratrech.add(contratrech);
		}
	}

	public void Recherche() {
		getListecontratrech().clear();

		switch (option) {
		case "1": // recherche par le num contrat
			Recherchecontrat();

			break;
		case "0":// recherche par le num de client
			Rechercheclient();

			break;

		default:
			// option non selectionné
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Avertissement", "Choisir une option"));
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

		// setContrat((Contrat) getClient().getContrats());

	}

	public void reset() {
		
		utilisateur.setMotPasse(null);
		setEtatBouton(true);
	}

	public void vider() {

		client = new Client();
		sexe = new Sexe();
		listetmaison.clear();
		listebatiment.clear();
		nationalite = new Nationalite();
		typeclient = new TypeClient();
		profession = new Profession();
		pays = new Pays();
		commune = new Commune();
		ville = new Ville();
prototype= new PrototypeMaison();
		contrat = new Contrat();
		maison = new Maison();
		coutTotal = new BigDecimal(0);
		datecreaClt = Calendar.getInstance().getTime();
		setEtatBouton(true);

	}

	public void update() {

		try {

			objectService.updateObject(client);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Modification effectuée dans la base", "SUCCES"));
			client = new Client();
			desactiver();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de la modification ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
							"Echec de modification !"));
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	public void deleteclt() {
		try {

			// objectService.deleteObject(profilUtilisateur);
			this.listeclient.remove(client);
			objectService.deleteObject(client);
			client = new Client();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"  Suppression effectuée dans la base de donnée",
							"SUCCES"));
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

			// objectService.deleteObject(profilUtilisateur);
			this.listecontratmaj.remove(contrat);
			objectService.deleteObject(contrat);
			contrat = new Contrat();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"  Suppression effectuée dans la base de donnée",
							"SUCCES"));
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

				elements.add(new SelectItem(((Utilisateur) obj)
						.getCodeUtilisateur(), ((Utilisateur) obj)
						.getLoginUtilisateur()));

			}
		}
		return elements;

	}

	// getter et setter

	public SimpleDateFormat getFormate() {
		return formate;
	}

	public void setFormate(SimpleDateFormat formate) {
		this.formate = formate;
	}

	public void creerIdclient() {

		String pseudo = "CLT";
		String formId = getObjectService().getCodeTable(pseudo, 3, 5, "client",
				"NUM_CLT");

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

	public BigDecimal getFraisdossier() {
		return fraisdossier;
	}

	public void setFraisdossier(BigDecimal fraisdossier) {
		this.fraisdossier = fraisdossier;
	}

	public RequeteUtilisateur getRequeteUtilisateur() {
		return requeteUtilisateur;
	}

	public void setRequeteUtilisateur(RequeteUtilisateur requeteUtilisateur) {
		this.requeteUtilisateur = requeteUtilisateur;
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

	/*
	 * public List<Utilisateur> getListeUser() { listeUser.clear();
	 * 
	 * listeUser= new ArrayList<Utilisateur>(); List<Object> listObject =
	 * getObjectService().getObjects("Utilisateur"); for (Iterator it =
	 * listObject.iterator(); it.hasNext();) { Utilisateur utilisateur =
	 * (Utilisateur) it.next(); try { listeUser.add(utilisateur); } catch
	 * (Exception e) { }
	 * 
	 * } return listeUser; }
	 * 
	 * public void setListeUser(List<Utilisateur> listeUser) { this.listeUser =
	 * listeUser; }
	 */

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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Client> getListeclient() {

		listeclient.clear();

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

		listecontrat = new ArrayList<Contrat>();
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

	public BigDecimal getMontantProjet() {
		return montantProjet;
	}

	public void setMontantProjet(BigDecimal montantProjet) {
		this.montantProjet = montantProjet;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Date getDatecreaClt() {
		return datecreaClt;
	}

	public void setDatecreaClt(Date datecreaClt) {
		this.datecreaClt = datecreaClt;
	}

	public boolean isEtatImprimer() {
		return etatImprimer;
	}

	public void setEtatImprimer(boolean etatImprimer) {
		this.etatImprimer = etatImprimer;
	}

	public List<Sexe> getListesexe() {

		if (listesexe.isEmpty()) {
			listesexe = getObjectService().getObjects("Sexe");
		}
		return listesexe;
	}

	public void setListesexe(List<Sexe> listesexe) {
		this.listesexe = listesexe;
	}

	public List<Nationalite> getListenat() {

		if (listenat.isEmpty()) {
			listenat = getObjectService().getObjects("Nationalite");
		}
		return listenat;
	}

	public void setListenat(List<Nationalite> listenat) {
		this.listenat = listenat;
	}

	public TypeClient getTypeclient() {
		return typeclient;
	}

	public void setTypeclient(TypeClient typeclient) {
		this.typeclient = typeclient;
	}

	public List<TypeClient> getListetypeclient() {

		if (listetypeclient.isEmpty()) {
			listetypeclient = getObjectService().getObjects("TypeClient");
		}
		return listetypeclient;
	}

	public void setListetypeclient(List<TypeClient> listetypeclient) {
		this.listetypeclient = listetypeclient;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public List<Profession> getListeprof() {
		if (listeprof.isEmpty()) {
			listeprof = getObjectService().getObjects("Profession");
		}
		return listeprof;
	}

	public void setListeprof(List<Profession> listeprof) {
		this.listeprof = listeprof;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public List<Pays> getListepays() {

		if (listepays.isEmpty()) {
			listepays = getObjectService().getObjects("Pays");
		}
		return listepays;
	}

	public void setListepays(List<Pays> listepays) {
		this.listepays = listepays;
	}

	public Commune getCommune() {
		return commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	public List<Commune> getListecomune() {

		if (listecomune.isEmpty()) {
			listecomune = getObjectService().getObjects("Commune");
		}
		return listecomune;
	}

	public void setListecomune(List<Commune> listecomune) {
		this.listecomune = listecomune;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public List<Ville> getListeville() {

		if (listeville.isEmpty()) {
			listeville = getObjectService().getObjects("Ville");
		}
		return listeville;
	}

	public void setListeville(List<Ville> listeville) {
		this.listeville = listeville;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public List<Facture> getListefacture() {
		/*
		 * if (listefacture.isEmpty()) { listefacture =
		 * getObjectService().getObjects("Facture"); }
		 */
		return listefacture;
	}

	public void setListefacture(List<Facture> listefacture) {
		this.listefacture = listefacture;
	}

	public PrototypeMaison getPrototype() {
		return prototype;
	}

	public void setPrototype(PrototypeMaison prototype) {
		this.prototype = prototype;
	}

	public List<PrototypeMaison> getListeproto() {
		if (listeproto.isEmpty()) {
			listeproto = getObjectService().getObjects("PrototypeMaison");
		}

		return listeproto;
	}

	public void setListeproto(List<PrototypeMaison> listeproto) {
		this.listeproto = listeproto;
	}

	public Batiment getBatiment() {
		return batiment;
	}

	public void setBatiment(Batiment batiment) {
		this.batiment = batiment;
	}

	public List<Batiment> getListebatiment() {
		if (listebatiment.isEmpty()) {
			listebatiment = getObjectService().getObjects("Batiment");
		}
		return listebatiment;
	}

	public void setListebatiment(List<Batiment> listebatiment) {
		this.listebatiment = listebatiment;
	}

	public TypeMaison getTypemaison() {
		return typemaison;
	}

	public void setTypemaison(TypeMaison typemaison) {
		this.typemaison = typemaison;
	}

	public List<TypeMaison> getListetmaison() {
		if (listetmaison.isEmpty()) {
			listetmaison = getObjectService().getObjects("TypeMaison");
		}
		return listetmaison;
	}

	public void setListetmaison(List<TypeMaison> listetmaison) {
		this.listetmaison = listetmaison;
	}

	public BigDecimal getCoutTotal() {
		return coutTotal;
	}

	public void setCoutTotal(BigDecimal coutTotal) {
		this.coutTotal = coutTotal;
	}

	public Integer getNbreacquise() {
		return nbreacquise;
	}

	public void setNbreacquise(Integer nbreacquise) {
		this.nbreacquise = nbreacquise;
	}

	public BigDecimal getResteapayer() {
		return resteapayer;
	}

	public void setResteapayer(BigDecimal resteapayer) {
		this.resteapayer = resteapayer;
	}

	public BigDecimal getMontantdejapaye() {
		return montantdejapaye;
	}

	public void setMontantdejapaye(BigDecimal montantdejapaye) {
		this.montantdejapaye = montantdejapaye;
	}

	public File getRepectoire() {
		return repectoire;
	}

	public void setRepectoire(File repectoire) {
		this.repectoire = repectoire;
	}

}
