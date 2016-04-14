package com.gestion.managedbean.patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Consultation;
import com.gestion.model.Medicament;
import com.gestion.model.Nationalite;
import com.gestion.model.Ordonnance;
import com.gestion.model.Patient;
import com.gestion.model.Prescrire;
import com.gestion.model.PrescrireId;
import com.gestion.model.Profession;
import com.gestion.model.Sexe;
import com.gestion.model.TypePatient;
import com.gestion.model.Utilisateur;
import com.gestion.objetService.ObjectService;
import com.gestion.rechercheClass.RechercheMethode;
import com.gestion.utilitaires.IdGenerateur;

@Component
public class ProcessuconsultationMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Injection de Spring
	@Autowired
	ObjectService objectService;
	@Autowired
	IdGenerateur idGenerateur;

	@Autowired
	private Managedmajpatient managedmajpatient;
	@Autowired
	private Managedenrconsult managedenrconsult;
	@Autowired
	private Managedenrord managedenrord;

	@Autowired
	private AncienconsMB ancienconsMB;

	

	private int qte, duree;
	private String posologie;
	

	private static Logger logger = Logger
			.getLogger(ProcessuconsultationMB.class);
	private Patient patient = new Patient();
	private Sexe sexe = new Sexe();
	private Profession profession = new Profession();
	private Nationalite nationalite = new Nationalite();
	private TypePatient typePat = new TypePatient();

	private String id = new String();
	private boolean etatBouton = true;
	private boolean etatAnnuler = true;
	private boolean etatrep = true;
	private List<Medicament> listMedicaments = new ArrayList<>();
	private List<Consultation> listconsutation = new ArrayList<>();
	private List<SelectItem> elementsexe;
	private String selectedsexe;
	private List<SelectItem> elementnat;
	private String selectednat;
	private List<SelectItem> elementpro;
	private String selectedpro;

	

	private String typeFiltre;
	private String filtre;
	private String code;

	private List<Patient> listepatient = new ArrayList<>();

	private String identitepatient;
	private Patient selectedPatient;

	// pour consu

	private String outputMessage = "";
	private Consultation consultation = new Consultation();

	private Utilisateur utilisateur = new Utilisateur();

	private List listeconsul = new ArrayList<>();
	private String codecons;

	private Date dateordo = Calendar.getInstance().getTime();
	private Date datecons = Calendar.getInstance().getTime();

	private List<SelectItem> elementspersmed;
	private List<SelectItem> elementspat;

	private String idpers;
	private String idpat;

	private List listeconsult = new ArrayList<>();

	// pour ordonance
	private Ordonnance ordonance = new Ordonnance();

	private Prescrire prescrire = new Prescrire();
	private PrescrireId prescrireId = new PrescrireId();

	private List listeordon = new ArrayList<>();
	private String codeor;
	private Medicament medicament = new Medicament();
	
	

	private Prescrire prescrire1 = new Prescrire();
	private Prescrire prescrire2 = new Prescrire();
	private Prescrire prescrire3 = new Prescrire();
	private Prescrire prescrire4 = new Prescrire();
	private Prescrire prescrire5 = new Prescrire();

	private PrescrireId prescrireId1 = new PrescrireId();
	private PrescrireId prescrireId2 = new PrescrireId();
	private PrescrireId prescrireId3 = new PrescrireId();
	private PrescrireId prescrireId4 = new PrescrireId();
	private PrescrireId prescrireId5 = new PrescrireId();

	private Medicament medicament1 = new Medicament();
	private Medicament medicament2 = new Medicament();
	private Medicament medicament3 = new Medicament();
	private Medicament medicament4 = new Medicament();
	private Medicament medicament5 = new Medicament();

	private Ordonnance ordonance1 = new Ordonnance();
	private List<SelectItem> elementsord;
	private String idord;
	private List listemedprescri = new ArrayList<>();
	private List<Prescrire> listeprescrire = new ArrayList<>();
	
	
	
	
	public String handleflow(FlowEvent event) {
		String currentStepId = event.getNewStep();
		String stepToGo = event.getOldStep();
		/*
		 * if(skip) return "confirm"; else
		 */
		return event.getNewStep();
	}

	@Autowired
	RechercheMethode rechercheMethode;

	public void onRowSelect(SelectEvent event) {
		// setLivrer(((Livrer) event.getObject()));
		// setMedicament((Medicament) event.getObject());

		try {
			getRechercheMethode().rechercherPersonne(
					selectedPatient.getNumPat());
			setPatient(getRechercheMethode().getPatient());
			identitepatient = getPatient().getNomPat();
			identitepatient.concat(" " + patient.getPrenomPat());// Recuperation
																	// du nom du
																	// souscripteur
			System.out.println("************************patient :"
					+ getPatient().getNomPat() + getPatient().getNomPat());

			// setIdentiteClient(getIdentiteSouscripteur().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(
					"<<--->> Erreur d'édition du patient après la recherche par le nom ",
					e);
		}
		activer();
	}

	public void editerpatient() {

		// viderObjet();
		try {
			getRechercheMethode().rechercherPersonne(
					selectedPatient.getNumPat());
			setPatient(getRechercheMethode().getPatient());
			identitepatient = getPatient().getNomPat();
			setIdentitepatient(identitepatient.concat(" "
					+ patient.getPrenomPat()));// Recuperation du nom complet
			// patient= getSelectedPatient();

			// anciene consultation
			ancienconsMB.setPatient(getSelectedPatient());
			ancienconsMB.setListecons(null);
			ancienconsMB.setListecons(ancienconsMB.getRequeteUtilisateur()
					.recupconsult(getPatient().getNumPat()));
			System.out.println("************************patient :"
					+ getPatient().getNomPat() + getPatient().getPrenomPat());

			// setIdentiteClient(getIdentiteSouscripteur().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(
					"<<--->> Erreur d'édition du patient après la recherche par le nom ",
					e);
		}
	}

	
	
	
	public void valider() {

		try {

			
			enregistrercons();
			enregistrerordprocessus();
			enrmedocprescri();
			setEtatrep(false);
			setEtatBouton(true);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Enregistrement effectuée dans la base", "SUCCES"));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error("Erreur lors de la modification ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
							"Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("enregistrem non effectuée");
		}
	}

	public void validerconsut() {

		try {

			enregistrerordsimple();
			enrmedocprescri();
	
			//desactiver();
			setEtatBouton(true);
			setEtatrep(false);
			//viderpro();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Enregistrement effectuée dans la base", "SUCCES"));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error("Erreur lors de la modification ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
							"Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("enregistrem non effectuée");
		}
	}

	// recupere le dernier
	public void chargederniertraitem() {
		// getObjectService().getojects(patient);

	}

	public void chargerliste() {
		getObjectService().getojects(patient);

	}

	public void recuperersexe() {

		setSexe((Sexe) getObjectService().getObjectById(selectedsexe, "Sexe"));

	}

	public void recupererpro() {

		setProfession((Profession) getObjectService().getObjectById(
				selectedpro, "Profession"));

	}

	public void recuperernat() {

		setNationalite((Nationalite) getObjectService().getObjectById(
				selectednat, "Nationalite"));

	}

	public void Recuppersmed() {

		setUtilisateur((Utilisateur) getObjectService().getObjectById(idpers,
				"Utilisateur"));

	}

	public void activer() {

		setEtatAnnuler(false);
		setEtatBouton(false);
	}

	public void desactiver() {

		setEtatAnnuler(true);
		setEtatBouton(true);
	}

	public void viderpro() {
		// managedenrconsult= new Managedenrconsult();
		consultation = new Consultation();
		patient = new Patient();
		ordonance = new Ordonnance();
		prescrire1 = new Prescrire();
		prescrire2 = new Prescrire();
		prescrire3 = new Prescrire();
		prescrire4 = new Prescrire();
		prescrire5 = new Prescrire();

		prescrireId1 = new PrescrireId();
		prescrireId2 = new PrescrireId();
		prescrireId3 = new PrescrireId();
		prescrireId4 = new PrescrireId();
		prescrireId5 = new PrescrireId();

		medicament1 = new Medicament();
		medicament2 = new Medicament();
		medicament3 = new Medicament();
		medicament4 = new Medicament();
		medicament5 = new Medicament();

		setEtatrep(true);
		setEtatBouton(true);
	}

	public void vider() {
		sexe = new Sexe();
		profession = new Profession();
		nationalite = new Nationalite();
		patient = new Patient();

		setSelectednat(null);
		setSelectedpro(null);
		setSelectedsexe(null);
		setId(null);
		// setEtatBouton(true);
	}

	public void filtrer() {
		try {
			getListepatient().clear();
			getListepatient().addAll(
					objectService.filtrerpatient(typeFiltre, filtre));
			if (getListepatient().size() == 0) {
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

	// enr ord

	public void enregistrerordprocessus() {
		try {

			ordonance.setNumOrdo(getCodeor());
			ordonance.setConsultation(getConsultation());
			
			ordonance.setDateOrdo(dateordo);
			getObjectService().addObject(ordonance);
			System.out
					.println("**********************************ordon recuperer effectué   "
							+ getOrdonance().getNumOrdo());
			consultation.setOrdonnance(ordonance);
			getObjectService().updateObject(consultation);

			/*
			 * //for(listepres listepres:prescrire.getMedicament().getNumMed()){
			 * if (prescrire.getMedicament().getNumMed() !=
			 * prescrire.getId().getNumMed()) {
			 * //prescrire.getMedicament().getNumMed().
			 * 
			 * prescrireId.setNumMed(getMedicament().getNumMed());
			 * prescrireId.setNumOrdo(getOrdonance().getNumOrdo());
			 * 
			 * prescrire.setId(prescrireId);
			 * prescrire.setMedicament(medicament);
			 * prescrire.setOrdonnance(ordonance); //prescrire.setQteDistr(new
			 * Long(0)); }
			 * 
			 * 
			 * System.out.println("*************prescrir**********************"+
			 * getPrescrire().getId()+
			 * getPrescrire().getMedicament().getNumMed() );
			 * 
			 * getObjectService().addObject(prescrire);
			 * System.out.println("**********************************en effectué"
			 * );
			 */
			System.out
					.println("**********************Enregistrement  ordonance effectué*********");

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de l'enregistrement ", e);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
			// "Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	
	
	
	
	
	
	
	//enregistrer consultaion simple
	public void enregistrerordsimple() {
		try {

			ordonance.setNumOrdo(getCodeor());
			ordonance.setConsultation(getConsultation());
			patient= getConsultation().getPatient();
			ordonance.setDateOrdo(dateordo);
			getObjectService().addObject(ordonance);
			System.out
					.println("**********************************ordon recuperer effectué   "
							+ getOrdonance().getNumOrdo());
			consultation.setOrdonnance(ordonance);
			getObjectService().updateObject(consultation);

			System.out
					.println("**********************Enregistrement  ordonance effectué*********");

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de l'enregistrement ", e);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
			// "Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("Enregistrement non effectué");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void viderord() {

		ordonance = new Ordonnance();
		patient = new Patient();

		setCodeor(null);
		setIdpers(null);
	
		setIdpat(null);
		// setEtatAnnuler(false);
		setEtatBouton(true);

	}

	

	// consulation

	public void enregistrercons() {
		try {
			consultation.setNumConsult(getCodecons());
			consultation.setPatient(getPatient());
			consultation.setDateCons(datecons);
			consultation.setUtilisateur(utilisateur);
			// patient.getNationalite().getLibelleNat()

			// Cas de test
			// setOutputMessage("test ne figure pas dans les fichiers");
			// RequestContext.getCurrentInstance().execute("test.show();");
			getObjectService().addObject(consultation);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage("Succes", "Enregistrement effectué"));
			System.out.println("Enregistrement consultation effectué");
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();
			// viderpat();
			// setEtatAnnuler(true);

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de l'enregistrement ", e);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
			// "Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("Enregistrement  consultation non effectué");
		}
	}

	public void vidercons() {

		consultation = new Consultation();
		patient = new Patient();
		setCodecons(null);
		setIdpers(null);
		// setIdpat(null);

		setEtatBouton(true);
	}

	public void Recuppat() {
		setPatient((Patient) getObjectService().getObjectById(idpat, "Patient"));
		setEtatAnnuler(false);
		setEtatBouton(false);

	}

	public void viderpresc() {
		prescrire1 = new Prescrire();
		prescrire2 = new Prescrire();
		prescrire3 = new Prescrire();
		prescrire4 = new Prescrire();
		prescrire5 = new Prescrire();
		setIdpers(null);
		setEtatBouton(true);
	}

	public void test1() {

		if (getMedicament2().getDsgMed() != getMedicament1().getDsgMed()) {
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());

			System.out.println("****eeee1 et  2 nonidentique");

		} else {

			System.out.println("****+++++++++++++++justemedica1 "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());

			System.out.println("****+++++++++++++1 et  2 identique");

		}

	}

	public void test2() {
		if ((getMedicament3().getDsgMed() == getMedicament1().getDsgMed())
				&& (getMedicament2().getDsgMed() == getMedicament3()
						.getDsgMed()))

		{// RequestContext.getCurrentInstance().execute("test.show();");
			System.out.println("********************************presecri deja");

		}
	}

	public void enrmedocprescri() {
		try {
            listeprescrire.clear();
			if(getMedicament1()== null){
				System.out.println("+++++++++++++++++++++++obje nullllll");	
			}else {

			
			prescrireId1.setNumMed(getMedicament1().getNumMed());
			prescrireId1.setNumOrdo(getOrdonance().getNumOrdo());
			prescrire1.setId(prescrireId1);
			prescrire1.setMedicament(getMedicament1());
			prescrire1.setOrdonnance(ordonance);
			prescrire1.setQteDistr(0);

			getObjectService().addObject(prescrire1);
			listeprescrire.add(prescrire1);
			}
		
			if(getMedicament2()== null){
				System.out.println("+++++++++++++++++++++++obje prsvire2 nullllll");	
			}else {
			prescrireId2.setNumMed(medicament2.getNumMed());
			prescrireId2.setNumOrdo(getOrdonance().getNumOrdo());
			prescrire2.setId(prescrireId2);
			prescrire2.setMedicament(getMedicament2());
			prescrire2.setOrdonnance(ordonance);
			prescrire2.setQteDistr(0);
			
			getObjectService().addObject(prescrire2);
			listeprescrire.add(prescrire2);
			}

			System.out.println("Enregistrement presrire2  effectué");
			System.out.println("Enregistrement presrire2 medicament2: "
					+ getMedicament2().getDsgMed());
			
			if(getMedicament3()==null){
				System.out.println("+++++++++++++++++++++++obje prescrire3 nullllll");	
			}else {
			prescrireId3.setNumMed(medicament3.getNumMed());
			prescrireId3.setNumOrdo(getOrdonance().getNumOrdo());
			prescrire3.setId(prescrireId3);
			prescrire3.setMedicament(getMedicament3());
			prescrire3.setOrdonnance(ordonance);
			prescrire3.setQteDistr(0);
			getObjectService().addObject(prescrire3);
			System.out.println("Enregistrement presrire3  effectué");
			listeprescrire.add(prescrire3);
			}

			if( getMedicament4()==null){
				System.out.println("+++++++++++++++++++++++obje prescrire4 nullllll");	
			}else {
			prescrireId4.setNumMed(getMedicament4().getNumMed());
			prescrireId4.setNumOrdo(getOrdonance().getNumOrdo());
			prescrire4.setId(prescrireId4);
			prescrire4.setMedicament(getMedicament4());
			prescrire4.setOrdonnance(ordonance);
			prescrire4.setQteDistr(0);
			getObjectService().addObject(prescrire4);
			System.out.println("Enregistrement presrire4  effectué");}

			if(getMedicament5()==null){
				System.out.println("+++++++++++++++++++++++obje prescrire5 nullllll");	
			}else {
			prescrireId5.setNumMed(getMedicament5().getNumMed());
			prescrireId5.setNumOrdo(getOrdonance().getNumOrdo());
			prescrire5.setId(prescrireId5);
			prescrire5.setMedicament(getMedicament5());
			prescrire5.setOrdonnance(ordonance);
			prescrire5.setQteDistr(0);
			getObjectService().addObject(prescrire5);
			System.out.println("Enregistrement presrire5  effectué");
			listeprescrire.add(prescrire5);
			
			}
			// Cas de test
			// setOutputMessage("test ne figure pas dans les fichiers");
			RequestContext.getCurrentInstance().execute("test.show();");
			// viderpresc();
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage("Succes", "Enregistrement effectué"));
			System.out.println("Enregistrement prescrit  effectué");
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();
			
			
			
			System.out.println("taille +++++" +listeprescrire.size());
			// viderpat();
			// setEtatAnnuler(true);

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// logger.error("Erreur lors de l'enregistrement ", e);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
			// "Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("Enregistrement  prescription non effectué");
		}
	}

	public void testCombo() {
		
		if (getMedicament2().getDsgMed() == getMedicament1().getDsgMed() || getMedicament3().getDsgMed() == getMedicament1().getDsgMed()|| getMedicament4().getDsgMed() == getMedicament1().getDsgMed()  || getMedicament5().getDsgMed() == getMedicament1().getDsgMed() ||
				getMedicament2().getDsgMed() == getMedicament3().getDsgMed() || getMedicament4().getDsgMed() == getMedicament2().getDsgMed()|| getMedicament5().getDsgMed() == getMedicament2().getDsgMed()  || 		
						getMedicament4().getDsgMed() == getMedicament3().getDsgMed() || getMedicament3().getDsgMed() == getMedicament5().getDsgMed()|| 
				
								getMedicament4().getDsgMed() == getMedicament5().getDsgMed()  
				
				) {
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());
			RequestContext.getCurrentInstance().execute("test.show();");
			setMedicament2(null);setMedicament3(null);setMedicament4(null);setMedicament5(null);
			
		
			/*FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Vous avez dejà choisi cet médicament! ",
							"Attention !"));*/
			
			System.out.println("****eeee1 et  2 identique");

		} else {


			System.out.println("****+++++++++++++1 et  2 nonidentique");

		}
		
	}
	
	
	public void testCombo1() {
		
		if (getMedicament2() == getMedicament1() || getMedicament3() == getMedicament1()|| getMedicament4() == getMedicament1()  || getMedicament5() == getMedicament1() )
				
				
				{
			//setMedicament1(null);
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());
			RequestContext.getCurrentInstance().execute("test.show();");
			
			//setMedicament2(null);setMedicament3(null);setMedicament4(null);setMedicament5(null);
			/*FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Vous avez dejà choisi cet médicament! ",
							"Attention !"));*/
			
			System.out.println("****eeee1 et  2 identique");

		} else {


			System.out.println("****+++++++++++++1 et  2 nonidentique");

		}
		
	}
	
	public void testCombo2() {
		System.out.println("Medicament: ------------->>>>> "
				+ getMedicament1().getDsgMed());
		
		
		if (getMedicament2().getDsgMed() == getMedicament1().getDsgMed() || getMedicament3().getDsgMed() == getMedicament2().getDsgMed()|| getMedicament4().getDsgMed() == getMedicament2().getDsgMed()  || getMedicament5().getDsgMed() == getMedicament2().getDsgMed() )
				
				
				{
			//setMedicament2(null);
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());
			RequestContext.getCurrentInstance().execute("test.show();");
			//setMedicament3(null);setMedicament4(null);setMedicament5(null);
		
			
			System.out.println("****eeee1 et  2 identique");

		} else {


			System.out.println("****+++++++++++++1 et  2 nonidentique");

		}
		
	}
	
	
	public void testCombo3() {
		
		if (getMedicament1().getDsgMed() == getMedicament3().getDsgMed() || getMedicament3().getDsgMed() == getMedicament2().getDsgMed()|| getMedicament3().getDsgMed() == getMedicament4().getDsgMed()  || getMedicament5().getDsgMed() == getMedicament3().getDsgMed() )
				
				
				{
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());
			RequestContext.getCurrentInstance().execute("test.show();");
			//setMedicament4(null);setMedicament5(null);
			
			System.out.println("****eeee3 et  autre identique");

		} else {


			System.out.println("****+++++++++++++1 et  2 nonidentique");

		}
		
	}
	
public void testCombo4() {
		
		if (getMedicament1().getDsgMed() == getMedicament4().getDsgMed() || getMedicament2().getDsgMed() == getMedicament4().getDsgMed()|| getMedicament3().getDsgMed() == getMedicament4().getDsgMed()  || getMedicament5().getDsgMed() == getMedicament4().getDsgMed() )
				
				
				{
			System.out.println("****+++++++++++++++medicaent1  "
					+ getMedicament1().getDsgMed()
					+ getMedicament2().getDsgMed());
			RequestContext.getCurrentInstance().execute("test.show();");
			
			//setMedicament5(null);
			

		} else {
        System.out.println("****+++++++++++++1 et  2 nonidentique");

		}
		
	}
	
	
public void testCombo5() {
	
	if (getMedicament1().getDsgMed() == getMedicament5().getDsgMed() || getMedicament5().getDsgMed() == getMedicament2().getDsgMed()|| getMedicament5().getDsgMed() == getMedicament4().getDsgMed()  || getMedicament5().getDsgMed() == getMedicament3().getDsgMed() )
			
			
			{
		System.out.println("****+++++++++++++++medicaent1  "
				+ getMedicament1().getDsgMed()
				+ getMedicament2().getDsgMed());
		RequestContext.getCurrentInstance().execute("test.show();");
		
		
		System.out.println("****eeee3 et  autre identique");

	} else {


		System.out.println("****+++++++++++++1 et  2 nonidentique");

	}
	
}

public void testcom(){
	testCombo2();testCombo3();testCombo4();testCombo5();testCombo1();
	
	
}
	
	
	

	public void validerprescrire() {

		try {

			objectService.updateObject(prescrire1);
			objectService.updateObject(prescrire2);
			objectService.updateObject(prescrire3);
			objectService.updateObject(prescrire4);
			objectService.updateObject(prescrire5);
			viderpresc();
			/*
			 * enregistrercons(); enregistrerordprocessus(); enrmedocprescri();
			 */

			desactiver();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Enregistrement effectuée dans la base", "SUCCES"));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			logger.error("Erreur lors de la modification ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
							"Echec d'enregistrement !"));
			e.printStackTrace();
			System.out.println("enregistrem non effectuée");
		}
	}

	/*
	 * public void validerm(){
	 * 
	 * try {
	 * prescrire.setDureeTrait((getManagedHybridePrescrire().setDuree(duree)));
	 * managedHybridePrescrire.setPosologie(posologie);
	 * managedHybridePrescrire.setQte(qte); //editerpatient();
	 * enregistrercons(); enregistrerordprocessus();
	 * getManagedenrconsult().enregistrerpat();
	 * getManagedenrord().enregistrerordprocessus(); desactiver();
	 * 
	 * FacesContext.getCurrentInstance().addMessage( null,new
	 * FacesMessage(FacesMessage.SEVERITY_INFO,
	 * "Enregistrement effectuée dans la base", "SUCCES")); } catch
	 * (NullPointerException e) { // TODO Auto-generated catch block
	 * logger.error("Erreur lors de la modification ", e);
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_INFO, "Echec ",
	 * "Echec d'enregistrement !")); e.printStackTrace();
	 * System.out.println("enregistrem non effectuée"); } }
	 */
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

	public String getId() {

		setId(getIdGenerateur().getIdpat());
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateordo() {
		return dateordo;
	}

	public void setDateordo(Date dateordo) {
		this.dateordo = dateordo;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public Nationalite getNationalite() {
		return nationalite;
	}

	public void setNationalite(Nationalite nationalite) {
		this.nationalite = nationalite;
	}

	public TypePatient getTypePat() {
		return typePat;
	}

	public void setTypePat(TypePatient typePat) {
		this.typePat = typePat;
	}

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

	public List<SelectItem> getElementpro() {

		if (elementpro == null) {
			elementpro = new ArrayList<SelectItem>();
			for (Object obj : getObjectService().getObjects("Profession")) {
				elementpro.add(new SelectItem(((Profession) obj).getCodeProf(),
						((Profession) obj).getLibelleProf()));

			}
		}
		return elementpro;
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

	public List<Patient> getListepatient() {

		/*
		 * listepatient = new ArrayList<Patient>(); List<Object> listObject =
		 * getObjectService().getObjects("Patient"); for (Iterator it =
		 * listObject.iterator(); it.hasNext();) { Patient patient = (Patient)
		 * it.next(); try { listepatient.add(patient); } catch (Exception e) { }
		 * 
		 * }
		 */

		return listepatient;
	}

	public void setListepatient(List<Patient> listepatient) {
		this.listepatient = listepatient;
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

	public Managedmajpatient getManagedmajpatient() {
		return managedmajpatient;
	}

	public void setManagedmajpatient(Managedmajpatient managedmajpatient) {
		this.managedmajpatient = managedmajpatient;
	}

	public Managedenrconsult getManagedenrconsult() {
		return managedenrconsult;
	}

	public void setManagedenrconsult(Managedenrconsult managedenrconsult) {
		this.managedenrconsult = managedenrconsult;
	}

	public Managedenrord getManagedenrord() {
		return managedenrord;
	}

	public void setManagedenrord(Managedenrord managedenrord) {
		this.managedenrord = managedenrord;
	}

	public String getIdentitepatient() {
		return identitepatient;
	}

	public void setIdentitepatient(String identitepatient) {
		this.identitepatient = identitepatient;
	}

	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	public RechercheMethode getRechercheMethode() {
		return rechercheMethode;
	}

	public void setRechercheMethode(RechercheMethode rechercheMethode) {
		this.rechercheMethode = rechercheMethode;
	}

	public String getOutputMessage() {
		return outputMessage;
	}

	public void setOutputMessage(String outputMessage) {
		this.outputMessage = outputMessage;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List getListeconsul() {

		listeconsul = new ArrayList<Consultation>();
		List<Object> listObject = getObjectService().getObjects("Consultation");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Consultation consultation = (Consultation) it.next();
			try {
				listeconsul.add(consultation);
			} catch (Exception e) {
			}

		}
		return listeconsul;
	}

	public void setListeconsul(List listeconsul) {
		this.listeconsul = listeconsul;
	}

	public List<SelectItem> getElementspersmed() {

		if (elementspersmed == null) {
			elementspersmed = new ArrayList<SelectItem>();
			try {
				for (Object obj : getObjectService().getObjects("Utilisateur")) {
					if (((Utilisateur) obj).getProfil().getLibelleProfil()
							.equalsIgnoreCase("ROLE_MEDECIN")) {
						elementspersmed.add(new SelectItem(((Utilisateur) obj)
								.getCodeUtilisateur(), ((Utilisateur) obj)
								.getNomUtilisateur()));
					}

				}
			} catch (Exception e) {

			}
		}
		return elementspersmed;
	}

	public void setElementspersmed(List<SelectItem> elementspersmed) {
		this.elementspersmed = elementspersmed;
	}

	public List<SelectItem> getElementspat() {

		if (elementspat == null) {
			elementspat = new ArrayList<SelectItem>();
			try {
				for (Object obj : getObjectService().getObjects("Patient")) {

					elementspat.add(new SelectItem(((Patient) obj).getNumPat(),
							((Patient) obj).getNomPat()));

				}
			} catch (Exception e) {

			}
		}

		return elementspat;
	}

	public void setElementspat(List<SelectItem> elementspat) {
		this.elementspat = elementspat;
	}

	public String getIdpers() {
		return idpers;
	}

	public void setIdpers(String idpers) {
		this.idpers = idpers;
	}

	public String getIdpat() {
		return idpat;
	}

	public void setIdpat(String idpat) {
		this.idpat = idpat;
	}

	public Ordonnance getOrdonance() {
		return ordonance;
	}

	public void setOrdonance(Ordonnance ordonance) {
		this.ordonance = ordonance;
	}

	public Prescrire getPrescrire() {
		return prescrire;
	}

	public void setPrescrire(Prescrire prescrire) {
		this.prescrire = prescrire;
	}

	public PrescrireId getPrescrireId() {
		return prescrireId;
	}

	public void setPrescrireId(PrescrireId prescrireId) {
		this.prescrireId = prescrireId;
	}

	public List getListeordon() {
		return listeordon;
	}

	public void setListeordon(List listeordon) {
		this.listeordon = listeordon;
	}

	public String getCodeor() {

		setCodeor(getIdGenerateur().getIdord());
		return codeor;
	}

	public void setCodeor(String codeor) {
		this.codeor = codeor;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public String getCodecons() {
		return getIdGenerateur().getIdcons(patient);

	}

	public void setCodecons(String codecons) {
		this.codecons = codecons;
	}


	

	public List getListeconsult() {
		return listeconsult;
	}

	public void setListeconsult(List listeconsult) {
		this.listeconsult = listeconsult;
	}

	

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getPosologie() {
		return posologie;
	}

	public void setPosologie(String posologie) {
		this.posologie = posologie;
	}

	public PrescrireId getPrescrireId1() {
		return prescrireId1;
	}

	public void setPrescrireId1(PrescrireId prescrireId1) {
		this.prescrireId1 = prescrireId1;
	}

	public PrescrireId getPrescrireId2() {
		return prescrireId2;
	}

	public void setPrescrireId2(PrescrireId prescrireId2) {
		this.prescrireId2 = prescrireId2;
	}

	public PrescrireId getPrescrireId3() {
		return prescrireId3;
	}

	public void setPrescrireId3(PrescrireId prescrireId3) {
		this.prescrireId3 = prescrireId3;
	}

	public PrescrireId getPrescrireId4() {
		return prescrireId4;
	}

	public void setPrescrireId4(PrescrireId prescrireId4) {
		this.prescrireId4 = prescrireId4;
	}

	public PrescrireId getPrescrireId5() {
		return prescrireId5;
	}

	public void setPrescrireId5(PrescrireId prescrireId5) {
		this.prescrireId5 = prescrireId5;
	}

	public Prescrire getPrescrire1() {
		return prescrire1;
	}

	public void setPrescrire1(Prescrire prescrire1) {
		this.prescrire1 = prescrire1;
	}

	public Prescrire getPrescrire2() {
		return prescrire2;
	}

	public void setPrescrire2(Prescrire prescrire2) {
		this.prescrire2 = prescrire2;
	}

	public Prescrire getPrescrire3() {
		return prescrire3;
	}

	public void setPrescrire3(Prescrire prescrire3) {
		this.prescrire3 = prescrire3;
	}

	public Prescrire getPrescrire4() {
		return prescrire4;
	}

	public void setPrescrire4(Prescrire prescrire4) {
		this.prescrire4 = prescrire4;
	}

	public Prescrire getPrescrire5() {
		return prescrire5;
	}

	public void setPrescrire5(Prescrire prescrire5) {
		this.prescrire5 = prescrire5;
	}

	public void setOrdonance1(Ordonnance ordonance1) {
		this.ordonance1 = ordonance1;
	}

	public List<SelectItem> getElementsord() {

		if (elementsord == null) {
			elementsord = new ArrayList<SelectItem>();
			for (Object obj : getObjectService().getObjects("Ordornnance")) {
				elementsord.add(new SelectItem(((Ordonnance) obj).getNumOrdo(),
						((Ordonnance) obj).getNumOrdo()));

			}
		}

		return elementsord;
	}

	public void setElementsord(List<SelectItem> elementsord) {
		this.elementsord = elementsord;
	}

	public String getIdord() {
		return idord;
	}

	public Medicament getMedicament1() {
		return medicament1;
	}

	public void setMedicament1(Medicament medicament1) {
		this.medicament1 = medicament1;
	}

	public Medicament getMedicament2() {
		return medicament2;
	}

	public void setMedicament2(Medicament medicament2) {
		this.medicament2 = medicament2;
	}

	public Medicament getMedicament3() {
		return medicament3;
	}

	public void setMedicament3(Medicament medicament3) {
		this.medicament3 = medicament3;
	}

	public Medicament getMedicament4() {
		return medicament4;
	}

	public void setMedicament4(Medicament medicament4) {
		this.medicament4 = medicament4;
	}

	public Medicament getMedicament5() {
		return medicament5;
	}

	public void setMedicament5(Medicament medicament5) {
		this.medicament5 = medicament5;
	}

	public Ordonnance getOrdonance1() {
		return ordonance1;
	}

	public void setIdord(String idord) {
		this.idord = idord;
	}

	public boolean isEtatrep() {
		return etatrep;
	}

	public void setEtatrep(boolean etatrep) {
		this.etatrep = etatrep;
	}

	public List getListemedprescri() {

		listemedprescri = new ArrayList<Prescrire>();
		List<Object> listObject = getObjectService().getObjects("Prescrire");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			Prescrire prescrire = (Prescrire) it.next();
			try {
				listemedprescri.add(prescrire);
			} catch (Exception e) {
			}

		}
		return listemedprescri;
	}

	public void setListemedprescri(List listemedprescri) {
		this.listemedprescri = listemedprescri;
	}

	public Date getDatecons() {
		return datecons;
	}

	public void setDatecons(Date datecons) {
		this.datecons = datecons;
	}

	public AncienconsMB getAncienconsMB() {
		return ancienconsMB;
	}

	public void setAncienconsMB(AncienconsMB ancienconsMB) {
		this.ancienconsMB = ancienconsMB;
	}

	

	public List<Medicament> getListMedicaments() {
		if (listMedicaments.isEmpty()) {
			listMedicaments = getObjectService().getObjects("Medicament");
		}
		return listMedicaments;
	}

	public void setListMedicaments(List<Medicament> listMedicaments) {
		this.listMedicaments = listMedicaments;
	}

	public List<Consultation> getListconsutation() {
		if (listconsutation.isEmpty()) {
			listconsutation = getObjectService().getObjects("Consultation");
		}
		
		return listconsutation;
	}

	public void setListconsutation(List<Consultation> listconsutation) {
		this.listconsutation = listconsutation;
	}

	public List<Prescrire> getListeprescrire() {
		return listeprescrire;
	}

	public void setListeprescrire(List<Prescrire> listeprescrire) {
		this.listeprescrire = listeprescrire;
	}

	
	
	
}
