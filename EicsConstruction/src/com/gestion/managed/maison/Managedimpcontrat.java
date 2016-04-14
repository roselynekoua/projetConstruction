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
import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Maison;
import com.gestion.model.Nationalite;
import com.gestion.model.Profil;
import com.gestion.model.PrototypeMaison;
import com.gestion.model.Sexe;
import com.gestion.model.Utilisateur;
import com.gestion.objetDao.RequeteUtilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class Managedimpcontrat implements Serializable {

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
	private boolean etatBouton = true;
	private boolean etatAnnuler = true;
	private Utilisateur utilisateur = new Utilisateur();
	private Sexe sexe = new Sexe();
private PrototypeMaison prototype= new PrototypeMaison();
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
	private String option;
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

	private Date dateDeb = new Date();
	private Date dateFin = new Date();

	private BigDecimal qteTerSup = new BigDecimal(0);
	private BigDecimal coutUnitSup = new BigDecimal(0);
	private BigDecimal coutTotalSup = new BigDecimal(0);
	private BigDecimal montantProjet = new BigDecimal(0);
	private BigDecimal fraisdossier = new BigDecimal(0);
	private SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
	private BigDecimal coutTotal = new BigDecimal(0);
	private String typeFiltre;
	private String filtre;

	private UploadedFile file;

	// private String
	// destination="C:/Users/Richard/printing~subversion/fileupload/web/Uploaded";

	private String destination = "C:/Users/rosyj3a/Dossierphoto/photoclt";

	public void upload(FileUploadEvent event) {
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

	
	
	public void calculer() {
		setCoutTotal(contratrech.getPrototypeMaison().getCoutTtcPrototype());
		// setCoutTotal(prototype.getCoutTtcPrototype().multiply(getPrototype().getQteSouscrPrototype()));
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

	public void enregistrer() {
		try {

			creerIdclient();
			// Recupération des données Affichées
			client.setSexe(getSexe());
			// client.setSexe(sexe);

			client.setNationalite(nationalite);

			getObjectService().addObject(client);
			System.out.println("Client enregistré avec succès!");

			enregistrercontrat();

			// vider();

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
			contrat.setMaison(maison);
			contrat.setQteAcq(1);
//			contrat.setCoutUnitSup(coutUnitSup);
			// contrat.setQteTerSup(contrat.getQteAcq());
			// contrat.setQteAcq(qteTerSup);
//			contrat.setCoutTotalSup(coutTotalSup);
			contrat.setCoutMtRestant(getMontantProjet());

			contrat.setUtilisateur(getManagedConnexion().getUtilisateur());

//			maison.setQteRestMais(maison.getQteDpbMais() - contrat.getQteAcq());
			getObjectService().updateObject(maison);

			getObjectService().addObject(contrat);
			System.out.println("Contrat enregistré avec succès!");

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	public void recuperer() {

		setListecontratparperiode(requeteUtilisateur.recuplistecontat(
				formate.format(dateDeb), formate.format(dateFin)));
		if (listecontratparperiode.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							"Aucun contrat sur cette periode !"));
		}
		if (dateFin.before(dateDeb)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							"Entrez une bonne date  !"));

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

	public void recuperersexe() {

		setSexe((Sexe) getObjectService().getObjectById(selectedsexe, "Sexe"));

	}

	public void recuperernat() {

		setNationalite((Nationalite) getObjectService().getObjectById(
				selectednat, "Nationalite"));

	}

	public void recuperermaison() {

		setMaison((Maison) getObjectService().getObjectById(selectedmaison,
				"Maison"));

	}

	public void cal() {

		// coutTotalSup = 0.00;
		coutTotalSup = coutUnitSup.multiply(qteTerSup);
		// coutTotalSup = qteTerSup*coutUnitSup;
		// coutTotalSup = qteTerSup.coutUnitSup;
//		montantProjet = getMaison().getCoutTotalMais().add(getCoutTotalSup().add(fraisdossier));
	}

	public void calcontrat() {

//		contrat.setCoutTotalSup(contrat.getCoutUnitSup().multiply(
//				contrat.getQteTerSup()));
		// coutTotalSup = coutUnitSup.multiply(qteTerSup);
		// coutTotalSup = qteTerSup*coutUnitSup;
		// coutTotalSup = qteTerSup.coutUnitSup;
//		montantProjet = getMaison().getCoutTotalMais().add(getCoutTotalSup());

	}

	public void Rechercheclient() {
		//listecontratrech.clear();
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
				setContrat(contr);
			}
		}

	}
	
	
	
	
	public void Recherchecontrat() {
		//listecontratrech.clear();
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
			System.out.println("Contrat esisstan");
		}
	}

	public void recherche() {
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
		setContratselected(((Contrat) event.getObject()));
		setContrat(contratselected);
		//setContratrech(((Contrat) event.getObject()));
		setClient(getContrat().getClient());

	}

	public void onRowSelectclt(SelectEvent event) {
		getListeclient().clear();
		setClient(getClientselected());

		// setContrat((Contrat) getClient().getContrats());

	}

	public void reset() {
		client = new Client();
		coutTotal = new BigDecimal(0);
		contrat = new Contrat();
        maison = new Maison();
        contratselected = new Contrat();
        listecontratrech.clear();
        setCritere("");
		setEtatBouton(true);
	}

	public void vider() {

		client = new Client();
		sexe = new Sexe();
		nationalite = new Nationalite();
		setSelectedsexe(null);
		setSelectednat(null);
		setSelectedmaison(null);
		contrat = new Contrat();
        maison = new Maison();
		montantProjet = new BigDecimal(0);
		fraisdossier = new BigDecimal(0);
		qteTerSup = new BigDecimal(0);
		
		coutTotalSup = new BigDecimal(0);
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
			elementmaison = new ArrayList<SelectItem>();
			for (Object obj : getObjectService().getObjects("Maison")) {

				elementmaison.add(new SelectItem(((Maison) obj).getCodeMais(),
						((Maison) obj).getDsgMais()));

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

	public List<SelectItem> getElementsexe() {

		if (elementsexe == null) {
			elementsexe = new ArrayList<SelectItem>();
			for (Object obj : getObjectService().getObjects("Sexe")) {
				elementsexe.add(new SelectItem(((Sexe) obj).getCodeSexe(),
						((Sexe) obj).getLibelleSexe()));

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
				elementnat.add(new SelectItem(((Nationalite) obj).getCodeNat(),
						((Nationalite) obj).getLibelleNat()));

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

		// listeclient= new ArrayList<Client>();
		// List<Object> listObject = getObjectService().getObjects("Client");
		// for (Iterator it = listObject.iterator(); it.hasNext();) {
		// Client clt = (Client) it.next();
		// try {
		// listeclient.add(clt);
		// } catch (Exception e) {
		// }
		//
		// }
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
	public java.lang.String getNomUtilisateur() {
		return nomUtilisateur;
	}

	
	public void setNomUtilisateur(java.lang.String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	
	public java.lang.String getPrenomUtilisateur() {
		return prenomUtilisateur;
	}

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

	public PrototypeMaison getPrototype() {
		return prototype;
	}

	public void setPrototype(PrototypeMaison prototype) {
		this.prototype = prototype;
	}

	public BigDecimal getCoutTotal() {
		setCoutTotal(contratrech.getPrototypeMaison().getCoutTtcPrototype().multiply(new BigDecimal(contratrech.getQteAcq())));
		return coutTotal;
	}

	public void setCoutTotal(BigDecimal coutTotal) {
		this.coutTotal = coutTotal;
	}
	
	
}
