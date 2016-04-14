package com.gestion.managedbean.admin;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class MBimage implements Serializable{
	Logger logger = Logger.getLogger(MBimage.class);
	
	
	
	
	
	
	
	
	
	
	/*
	private static final long serialVersionUID = 1L;
	    @Autowired
		private ContratMB contratMB;
	    @Autowired
		private ClientMB clientMB;
	    @Autowired
		private CarteGriseMB carteGriseMB;
	    @Autowired
	  	private GarantiesCompagnies garantiesCompagnies;    
		private Conducteur cduc;
		private Boolean showNavBar = false;
		private Exercice exercice = new Exercice();
		@Autowired
		private ManagedQuittanceAuto managedQuittanceAuto;
		@Autowired
		private GarantieMB garantieMB;

		private String nomclient, adresseclient, sousCategorieV, risque;

		@Autowired
		IdGenerateur idGenerateur;
		@Autowired
		ConditionPartAuto conditionPartAuto;
		@Autowired
		private QuittanceDesignAuto quittanceDesignAuto;
		
		@Autowired
		private RecupObjetRow recupObjetRow;
		private Contrat contratF = new Contrat();
		private Avenant avenantF = new Avenant();

		@Autowired
		private VerificationAuthentif verificationAuthentif;//By ALekerand

		
		@PostConstruct
		public void postConstru() {
			risque = "Automobile";
			//getContratMB().setUtilisateur(getContratMB().utlsateur());
		
			getContratMB().setDureeEnjour((long) 365);
			setExercice(getContratMB().getExerciceOuvert()); 
			
		}

		
		
		//irene
		public void validerContrat(){
			     //client
		    	getClientMB().addPersonnePhysique();
		    	
		    	//contrat
				String pv = getContratMB().getUtilisateur().getPointVente()
				.getCodePointVente();
				
				
					
			 String util =getContratMB().getUtilisateur().getCodeUtilisateur();
				getContratMB().getContrat().setNumPolice(getIdGenerateur().getPoliceID(pv, util, "AUT"));
				getContratMB().getContrat().setPointVente(getContratMB().getUtilisateur().getPointVente());
				getContratMB().getContrat().setPersonne(getClientMB().getPhysique().getPersonne());
				getContratMB().getContrat().setRisque(getContratMB().getRisque());
				getContratMB().getContrat().setSocieteAssurance(getContratMB().getSocieteAssurance());
				getContratMB().getObjectService().addObject(getContratMB().getContrat());
				
				//avenant
				String idAven = getIdGenerateur().getIdNewAvenant(getContratMB().getContrat().getNumPolice());
				getContratMB().getAvenant().setNumAvenant(idAven);
				short d = (short) (getContratMB().getDuree()* 30);
				int mail = 1;
				getContratMB().getAvenant().setDateAvenant(getContratMB().getdateAvenant());
				getContratMB().getAvenant().setEffet(getContratMB().getEffet());
				getContratMB().getAvenant().setDateEmission(getContratMB().getEmission());
				getContratMB().getAvenant().setMouvement(getContratMB().getMouvement());
				getContratMB().getAvenant().setEcheance(getContratMB().getEcheance());
				getContratMB().getAvenant().setContrat(getContratMB().getContrat());
				getContratMB().getAvenant().setUtilisateur(getContratMB().getUtilisateur());
				getContratMB().getAvenant().setCompagnieAssurance(getGarantiesCompagnies().getSelectedTarifWeb().getCompagnieAssurance());
				getContratMB().getObjectService().addObject(getContratMB().getAvenant());
				
				
				
		}


		
		
		
		public void choixSousCat() {
			// recupération du taux de l'apporteur et l'apporteur
			//getCarteGriseMB().getSlctdVehiRw().setApporteur(
			//		getContratMB().getCodeApporteur());

			getContratMB().getCalculCommission().getTauxCommissionApporteur(
					getCarteGriseMB().getSlctdVehiRw().getApporteur().getCodeApporteur(),
					getCarteGriseMB().getSlctdVehiRw().getSouCatVehi()
							.getCategorie().getCodeCategorie());
			//getCarteGriseMB().getSlctdVehiRw().setTauxCommissionApporteur(
				//	getContratMB().getCalculCommission().getTauxCommission());
			getCarteGriseMB().choixSousCat();
		}

		// les methodes de choix Avenant Affaire Nouvelle
		public void tabChange() {
		
			
			try {
				getGarantiesCompagnies().setDureeJour(getContratMB().getDureeEnjour());
				getGarantiesCompagnies().setDuree(getContratMB().getDuree());
				System.out.println("-------------Check herrrrrrrrrrrrrrrrrrrrrrrrrrrrrre-----------getCarteGriseMB().getSlctdVehiRw()----------------- "+getCarteGriseMB().getSlctdVehiRw());
				getCarteGriseMB().getSlctdVehiRw().getTarifwebComp().addAll(getGarantiesCompagnies().returnTarif(getCarteGriseMB().getSlctdVehiRw()));
				
				
				
			} catch (NullPointerException e) {
				
			}
		}

		// methode de validation de la prime
		public void rechercherClient() {
			getClientMB().rechercherClient();
			if (getClientMB().getStatutRechercheClient() == true) {
				showNavBar = true;
			}

		}

		
		

		public String validerPrime() {
			//getGarantieMB().validerPrime();

			// listGarantieparVehicule
			getCarteGriseMB().getVehiculeList().clear();
			getCarteGriseMB().getSlctdVehiRw().getListGarantieparVehicule()
					.clear();
			String valider =	getGarantiesCompagnies().validerTarif();
			if(valider.equalsIgnoreCase("validationchoixOK")){
				getCarteGriseMB().getSlctdVehiRw().setTarifwebCompSelectd(getGarantiesCompagnies().getSelectedTarifWeb());
				getCarteGriseMB().getSlctdVehiRw().getListegaranties().clear();
				getCarteGriseMB().getSlctdVehiRw().getListegaranties()
						.addAll(getCarteGriseMB().getSlctdVehiRw().getTarifwebCompSelectd().getListegarantieSelectd());
				getCarteGriseMB().getVehiculeList().add(getCarteGriseMB().getSlctdVehiRw());
				getCarteGriseMB().setValidVehiEtat(false);	
			}
			
			return valider;
		}

		public void majConducteur() {

			getCarteGriseMB().getSlctdVehiRw().getConduHab().setDateNaissCond(
					getClientMB().getMonPhysique().getDateNaissPers());
			getCarteGriseMB().getSlctdVehiRw().getConduHab().setNonCond(
					getClientMB().getMaPersonne().getNomRaisonSociale());
			
			getCarteGriseMB().getSlctdVehiRw().getConduHab().setPrenomsCond(getClientMB().getMonPhysique().getPrenomPers());
			getCarteGriseMB().getSlctdVehiRw().getConduHab().setLieuNaisCond(getClientMB().getMonPhysique().getLieuNaissPers());
			
			
			getCarteGriseMB().getSlctdVehiRw().getConduHab().setNumCond(getClientMB().getMonPhysique().getNumPiecePers());
			getCarteGriseMB().getSlctdVehiRw().getConduHab().setDureepermiscond((short) 0);
			System.out.println("+++++++Conducteur habituel du Vehicule+++++++"
					+ getCarteGriseMB().getSlctdVehiRw().getConduHab().getNonCond());

		}

		public boolean isClientConduc() {
			String numpiece = "";
			if (getClientMB().getMaPersonne().getPhysique()!=null) {
				numpiece = (getClientMB().getMaPersonne().getPhysique())
						.getNumPiecePers();
			}
			if (getClientMB().getMaPersonne().getMorale()!=null) {
				numpiece = (getClientMB().getMaPersonne().getMorale()).getNumRc();
			}

			return numpiece.equalsIgnoreCase(getCarteGriseMB()
					.getSlctdVehiRw().getConduHab().getNumCond());
		}

		public void chxConducteur() {
			if (isClientConduc()) {
				majConducteur();
			} else {
				cduc = (Conducteur) getCarteGriseMB().getObjectService()
						.getObjectById(
								getCarteGriseMB().getSlctdVehiRw()
										.getConduHab().getNumCond(), "Conducteur");
				if (cduc != null) {
					getCarteGriseMB().getSlctdVehiRw().setConduHab(cduc);
				} else {
					getCarteGriseMB().getSlctdVehiRw().setConduHab(
							new Conducteur(getCarteGriseMB().getSlctdVehiRw()
									.getConduHab().getNumCond()));
				}
			}
		}

		public void ValiderCotation() {
			getGarantieMB().validerGarantie();
			getCarteGriseMB().getSlctdVehiRw().getListegaranties().clear();
	if(!getGarantieMB().getListeGarantiesSelect().isEmpty()){
			getCarteGriseMB().getSlctdVehiRw().getListegaranties()
					.addAll(getGarantieMB().getListeGarantiesSelect());
			getCarteGriseMB().getSlctdVehiRw().setPrimeNette(
					getGarantieMB().getPrimeTotale());
			// calcul de l'accessoire de l'apporteur
			getCarteGriseMB().getSlctdVehiRw().setCommissionApporteur(BigDecimal.ZERO);
			getCarteGriseMB().validerVehicule();

		
	}
	
	
		}
		public String voirQuittance(){
			getManagedQuittanceAuto().getListVehicules().clear();
			getManagedQuittanceAuto().getListVehicules().addAll(
					getCarteGriseMB().getVehiculeList());
		
			getContratMB().getAvenant().setEffet(getContratMB().getEffet());
			getContratMB().getAvenant().setDateEmission(getContratMB().getEmission());
			getContratMB().getAvenant().setEcheance(getContratMB().getEcheance());
			
			getManagedQuittanceAuto().setAvenant(
					getContratMB().getAvenant());

			getManagedQuittanceAuto().setExercice(getExercice().getCodeexercice());// A corriger

			getManagedQuittanceAuto().calculPrime();
			getManagedQuittanceAuto().calculQuittance();

			return "Validation";
		}
		
		public String voirQuittance1(){
			getManagedQuittanceAuto().getListVehicules().clear();
			getManagedQuittanceAuto().getListVehicules().addAll(
					getCarteGriseMB().getVehiculeList());
		
			getContratMB().getAvenant().setEffet(getContratMB().getEffet());
			getContratMB().getAvenant().setDateEmission(getContratMB().getEmission());
			getContratMB().getAvenant().setEcheance(getContratMB().getEcheance());
			
			getManagedQuittanceAuto().setAvenant(
					getContratMB().getAvenant());

			getManagedQuittanceAuto().setExercice(getExercice().getCodeexercice());// A corriger

			getManagedQuittanceAuto().calculPrime();
			getManagedQuittanceAuto().calculQuittance();

			return "ValidationPrime";
		}

		public void sendQuittance(){
			voirQuittance();
			sendDevis();
		}
		
		
		public void sendDevis(){
			//chargement des données pour le rapport pdf
			ReportingAuto report = new ReportingAuto();
			report.setAvenant(getContratMB().getAvenant());
			report.setContrat(getContratMB().getContrat());
			report.setListVehiculeRow(getCarteGriseMB().getVehiculeList());
			report.setCategorie(getCarteGriseMB().getSlctdVehiRw().getSouCatVehi().getCategorie().getLibelleCategorie());
			Quittance quit = new Quittance();
			quit.setAccessoire(getManagedQuittanceAuto().getQuittanceAuto().getTotalAccessoire());
			quit.setTaxes(getManagedQuittanceAuto().getQuittanceAuto().getTaxeEnr());
			quit.setFga(getManagedQuittanceAuto().getQuittanceAuto().getTaxeFGA());
			quit.setNetAPayer(getManagedQuittanceAuto().getQuittanceAuto().getNetteApayer());
			report.setQuittance(quit);
			
			report.setNom(getClientMB().getNomClient());
			report.setPersonne(getClientMB().getClient());
			if(getContratMB().getUtilisateur().getPointVente()!=null){
			report.setPointVente(getContratMB().getUtilisateur().getPointVente());
			System.out.println("************************point de vente cotation Auto not null PV:" +report.getPointVente());
			System.out.println("************************point de vente cotation Auto not null PV:" +report.getPointVente().getLibellePointVente());
			}else {
				getContratMB().setUtilisateur(getContratMB().utlsateur());
				String pv = getContratMB().getUtilisateur().getPointVente().getCodePointVente();
				
				System.out.println("************************point de vente cotation Auto:" +pv);
				report.setPointVente(getContratMB().getUtilisateur().getPointVente());
			}
			report.setRisque(getContratMB().getRisque());
			report.setSocieteAssurance(getContratMB().getSocieteAssurance());
			try{
			getConditionPartAuto().setReportingAuto(report);
			getConditionPartAuto().editerConditionPart(report, (HttpServletRequest) FacesContext.getCurrentInstance()
							.getExternalContext().getRequest(),
					(HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		}
		
		public void rechercheContrat() {
			Date date = new Date();
			date = Calendar.getInstance().getTime();
			String police="";
			if(getContratF().getNumPolice()!=null){
				  police = getContratF().getNumPolice();
			}else{
				  police = "001WEB115AUT005";
			}
          
			// getManagedClient().getValeurRecherche();
			System.out.println("Recherche Contrat : Police avant "+police);
				setContratF((Contrat)getContratMB().getObjectService().getObjectById(police,"Contrat"));

				System.out.println("Recherche Contrat : Police après "+getContratF().getNumPolice());
				getContratMB().setContrat(getContratF());

				avenantF = getContratMB().getObjectService().DernierAvenant(police);
				System.out.println("Recherche Contrat : Avenant "+avenantF.getNumAvenant());
				avenantF.setMouvement("Affaire Nouvelle");
				avenantF.setDateAvenant(date);
				avenantF.setDateEmission(date);
				avenantF.setEffet(date);
				//avenantF.setCodeexercice(getExercice());
				avenantF.setEcheance(getContratMB().getDateEcheance(date,
						getAvenantF().getDuree()));
				System.out.println("Recherche Contrat : Vehicule Assuree-"+avenantF.getVehiculesAssures().getIdVehiculesAssures());
				System.out.println("Recherche Contrat :Liste de Vehicule -"+avenantF.getVehiculesAssures().getVehicules().size());
				rechercheVehicule();

			
		}
		
		public void rechercheVehicule() {
			
	String immat =	getCarteGriseMB().getSlctdVehiRw().getVehi().getNumImmat();
				getGarantiesCompagnies().setNumImmatriculation(immat);
				
			
				VehiculeRow vehiculeRow = new VehiculeRow();

				vehiculeRow = getGarantiesCompagnies().returnVehiculeRow();
				
				if(vehiculeRow !=null){
				getCarteGriseMB().getVehiculeList().add(vehiculeRow);
				System.out.println("/contenu du vehicule/"
						+ vehiculeRow.getVehi().getCodeVehicule() + "--"
						+ vehiculeRow.getListegaranties().size());
				try {
					getCarteGriseMB().setSlctdVehiRw(
							getCarteGriseMB().getVehiculeList().get(0));
						} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
			
			getGarantieMB().getListegaranties().addAll(
					getCarteGriseMB().getVehiculeList().get(0)
							.getListegaranties());

		}
		public String editerAttestation(){
			
			rechercheContrat();
			
			
			//chargement des données pour le rapport pdf
			ReportingAuto report = new ReportingAuto();
			report.setAvenant(avenantF);
			report.setContrat(contratF);
			report.setListVehiculeRow(getCarteGriseMB().getVehiculeList());
			//Quittance quit = new Quittance();
			quit.setAccessoire(getManagedQuittanceAuto().getQuittanceAuto().getAccessoire());
			quit.setTaxes(getManagedQuittanceAuto().getQuittanceAuto().getTaxeEnr());
			quit.setFga(getManagedQuittanceAuto().getQuittanceAuto().getTaxeFGA());
			quit.setNetAPayer(getManagedQuittanceAuto().getQuittanceAuto().getNetteApayer());
			report.setQuittance(quit);
			report.setNom(contratF.getPersonne().getNomRaisonSociale());
			if(contratF.getPersonne().getPhysique()!=null){
				report.setPersonne(getClientMB().getClient());
				report.setPersonne(contratF.getPersonne());
			}
			
			
			try{
				getConditionPartAuto().setReportingAuto(report);
				getConditionPartAuto().printPdf(report, (HttpServletRequest) FacesContext.getCurrentInstance()
								.getExternalContext().getRequest(),
						(HttpServletResponse) FacesContext.getCurrentInstance()
								.getExternalContext().getResponse());
			} catch (IOException e) {
				
			} catch (Exception e) {
				
			}
		
			return"attestation";
		}

		public void validerVehicule() {
			if (!getCarteGriseMB().getVehiculeList().contains(
					getCarteGriseMB().getSlctdVehiRw())) {
				getCarteGriseMB().getSlctdVehiRw().setNumOrdr(
						getCarteGriseMB().getVehiculeList().size() + 1);
				getCarteGriseMB().getVehiculeList().add(
						getCarteGriseMB().getSlctdVehiRw());

			}
			getCarteGriseMB().setSlctdVehiRw(new VehiculeRow());
			majConducteur();
			getCarteGriseMB().setEditGarEtat(true);
			getCarteGriseMB().setValidVehiEtat(true);
			getCarteGriseMB().setSlctdVehiRwTb(null);
			// System.out.println("ùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùù"+getContratMB().getBaremes());
		}
		public String handleflow(FlowEvent event) {
			
			String oldStep = event.getOldStep();
			String newStep = event.getNewStep();
			String a = newStep;
			
			// TRAITEMENT POUR LE PASSAGE DE Immat A categorie
			if (newStep.equalsIgnoreCase("ongletCategorie")
					&& oldStep.equalsIgnoreCase("ongletImmatriculation")) {
				System.out.println("TRAITEMENT POUR LE PASSAGE DE Immat A categorie");
				
		
					
			
			}
			// TRAITEMENT POUR LE PASSAGE DE LA CATEGORIE AU VEHICULE 
			if (newStep.equalsIgnoreCase("ongletVehicule")
					&& oldStep.equalsIgnoreCase("ongletCategorie")) {
				System.out.println("TRAITEMENT POUR LE PASSAGE DE categorie A vehicule");
			}
			
			// TRAITEMENT POUR LE PASSAGE DE VEHICULE AU CONDUCTEUR
						if (newStep.equalsIgnoreCase("ongletConducteur")
								&& oldStep.equalsIgnoreCase("ongletVehicule")) {
							if (getClientMB().isEtatClient()==true){
								//getClientMB().majconducteur();
								majConducteur();
								//System.out.println("conducteur"+getCarteGriseMB().getSlctdVehiRw().getConduHab().getNumCond());
								//getCarteGriseMB().getSlctdVehiRw().setConduHab(getClientMB().getConducteur());
							}
							System.out.println("TRAITEMENT POUR LE PASSAGE DE Vehicule A conducteur");
						}
						
			// TRAITEMENT POUR LE PASSAGE DE VEHICULE A QUITTANCE
						if (newStep.equalsIgnoreCase("ongletGarantie")
								&& oldStep.equalsIgnoreCase("ongletConducteur")) {
							System.out.println("TRAITEMENT POUR LE PASSAGE du conducteur aux garanties");
							getCarteGriseMB().validerProp();
							tabChange();
							if(getGarantieMB().affichegarantiesAuto(getCarteGriseMB().getSlctdVehiRw())==null){
								//a = "ongletCategorie";
							}
							
							
							
						}
			//logs.info(">>>>/ END -handleflow-");
			return a;
		}
		
		
		
		public void gererNewClient(){
			getVerificationAuthentif().recupererUtilisateur();
			
		}

		

		public void addContrats() {
			  //client
	    	getClientMB().addPersonnePhysique();
	    	//System.out.println("------>>personne"+getClientMB().getPhysique().get);//Clean after
	    	
	    	//contrat
	    	System.out.println("------>>Utilisateur"+getContratMB().getUtilisateur().getLoginUtilisateur());//Clean after
			String pv = getContratMB().getUtilisateur().getPointVente()
			.getCodePointVente();
			String util =getContratMB().getUtilisateur().getCodeUtilisateur();
			
			//Ajout des contrats par compagnie pour chaque vehicule
			for(VehiculeRow vrow: getCarteGriseMB().getVehiculeList()){
				String police = getIdGenerateur().getPoliceID(pv, util, "AUT");
				getContratMB().getContrat().setNumPolice(police);
				getContratF().setNumPolice(police);
				getContratMB().getContrat().setPointVente(getContratMB().getUtilisateur().getPointVente());
				getContratMB().getContrat().setPersonne(getClientMB().getPhysique().getPersonne());
				getContratMB().getContrat().setRisque(getContratMB().getRisque());
				getContratMB().getContrat().setSocieteAssurance(getContratMB().getSocieteAssurance());
				getContratMB().getContrat().setCompagnieAssurance(vrow.getTarifwebCompSelectd().getCompagnieAssurance());
				getContratMB().getContrat().setModeReconduction(getContratMB().getModeReconduction());
				//getContratMB().getObjectService().addObject(getContratMB().getContrat());
				
				//avenant
				String idAven = getIdGenerateur().getIdNewAvenant(getContratMB().getContrat().getNumPolice());
				getContratMB().getAvenant().setNumAvenant(idAven);
				
				getContratMB().getAvenant().setDateAvenant(getContratMB().getdateAvenant());
				getContratMB().getAvenant().setEffet(getContratMB().getEffet());
				getContratMB().getAvenant().setDateEmission(getContratMB().getEmission());
				getContratMB().getAvenant().setMouvement(getContratMB().getMouvement());
				getContratMB().getAvenant().setEcheance(getContratMB().getEcheance());
				getContratMB().getAvenant().setContrat(getContratMB().getContrat());
				double d = getContratMB().getDureeEnjour();
				getContratMB().getAvenant().setDuree((short)d);
				getContratMB().getAvenant().setUtilisateur(getContratMB().getUtilisateur());
				
				//getContratMB().getObjectService().addObject(getContratMB().getAvenant());
				
				voirQuittance();
				
				
				getManagedQuittanceAuto().setQuittanceid(
				getIdGenerateur().getIdQuittance(idAven));
				
				//try {
					 System.out.println("********Prime de la compagnie et commissiont du courtier**********");
					 
					String idQuitt = "";
					idQuitt = getManagedQuittanceAuto().getQuittanceid();
					// MAJ des champs de l'Exercice
					 getExercice().getChiffreAffExo().add(getManagedQuittanceAuto().getQuittanceAuto().getPrimeTotale());
					double CA = getExercice().getChiffreAffExo().doubleValue()
							+ getManagedQuittanceAuto().getQuittanceAuto()
									.getPrimeTotale().doubleValue();
					double PE = getExercice().getPrimeExercice().doubleValue()
							+ getManagedQuittanceAuto().getQuittanceAuto()
									.getPrimeExoEncours().doubleValue();
					double PR = getExercice().getPrimeAReporterExo().doubleValue()
							+ getManagedQuittanceAuto().getQuittanceAuto()
									.getPrimeReport().doubleValue();
					double PREC = getExercice().getPrecExo().doubleValue()
							+ getManagedQuittanceAuto().getQuittanceAuto().getPrec()
									.doubleValue();

					getExercice().setChiffreAffExo(
							BigDecimal.valueOf(CA).setScale(0, BigDecimal.ROUND_UP));
					getExercice().setPrimeExercice(BigDecimal.valueOf(PE));
					getExercice().setPrimeAReporterExo(BigDecimal.valueOf(PR));
					getExercice().setPrecExo(BigDecimal.valueOf(PREC));
					getExercice().setLibelleExercice("" + getExercice().getCodeexercice() + "");
					

					getContratMB().setExercice(getExercice());
					getContratMB().addContrat();
					//getContratMB().majExercice();
					getCarteGriseMB().gestionCarteGrise(
							getContratMB().getAvenant());
					getManagedQuittanceAuto().addQuittance(
							getContratMB().getAvenant());
					System.out.println("------->>> Edition  condition particulière");// Clean
																						// after
					FacesMessage msg = new FacesMessage(
							"Le Contrat est Enregistré avec la Quittance " + idQuitt
									+ " !");
					FacesContext.getCurrentInstance().addMessage(null, msg);

					// Edition des pieces
					getConditionPartAuto().editerConditionPart(
							idQuitt,
							(HttpServletRequest) FacesContext.getCurrentInstance()
									.getExternalContext().getRequest(),
							(HttpServletResponse) FacesContext.getCurrentInstance()
									.getExternalContext().getResponse());
					getQuittanceDesignAuto().editerQuittance(
							idQuitt,
							(HttpServletRequest) FacesContext.getCurrentInstance()
									.getExternalContext().getRequest(),
							(HttpServletResponse) FacesContext.getCurrentInstance()
									.getExternalContext().getResponse());
				} catch (IOException e) {
					logger.error("Erreur d'édition de piece", e);
				} catch (Exception e) {
					logger.error(
							"Erreur lors de l'enregistrement du enregistrement du contrat Auto",
							e);
				}
			}
			

		}

		public String okEtNewContrat() {
			addContrats();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();

			return "/pages/Auto/producteur/AffaireNouvelle?faces-redirect=true";
		}

		public String okContrat() {
			addContrats();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.clear();

			return "/pages/Auto/producteur/MapPointVente?faces-redirect=true";
		}

		public void testRecord() {
			// addContrats();
			getCarteGriseMB().gestionCarteGrise(
					getContratMB().getAvenant());
		}

		
		public void succes(){
			addContrats();
			sendDevis();
			//editerAttestation();
		}
		
		
		
		
		
		
		
		
		
		public String getNomclient() {
			try {
				Personne personne = getClientMB().getMaPersonne();

				if (personne.getPhysique()!=null) {
					Physique physique = personne.getPhysique();

					nomclient = physique.getNomRaisonSociale() + " "
							+ physique.getPrenomPers();
				} else {
					if (personne.getMorale()!=null) {
						Morale morale =  personne.getMorale();
						nomclient = morale.getNomRaisonSociale();
					}
				}

			} catch (Exception e) { //
				// log.error("Erreur dans la méthode getNomclient !", e);
			}

			return nomclient;
		}

		public void setNomclient(String nomclient) {
			this.nomclient = nomclient;
		}

		public String getAdresseclient() {

			try {
				Personne personne = getClientMB().getMaPersonne();

				adresseclient = personne.getAdresse();
			} catch (Exception e) { //
				// log.error("Erreur dans la méthode getNomclient !", e);
			}

			return adresseclient;
		}

		public void setAdresseclient(String adresseclient) {
			this.adresseclient = adresseclient;
		}


		public CarteGriseMB getCarteGriseMB() {
			return carteGriseMB;
		}

		public void setManagedCarteGrise(CarteGriseMB carteGriseMB) {
			this.carteGriseMB = carteGriseMB;
		}

		public String getSousCategorieV() {
			return sousCategorieV;
		}

		public void setSousCategorieV(String sousCategorieV) {
			this.sousCategorieV = sousCategorieV;
		}

		public Boolean getShowNavBar() {
			return showNavBar;
		}

		public void setShowNavBar(Boolean showNavBar) {
			this.showNavBar = showNavBar;
		}

		public IdGenerateur getIdGenerateur() {
			return idGenerateur;
		}

		public void setIdGenerateur(IdGenerateur idGenerateur) {
			this.idGenerateur = idGenerateur;
		}

		

		public String getRisque() {
			return risque;
		}

		public void setRisque(String risque) {
			this.risque = risque;
		}

		public ConditionPartAuto getConditionPartAuto() {
			return conditionPartAuto;
		}

		public void setConditionPartAuto(ConditionPartAuto conditionPartAuto) {
			this.conditionPartAuto = conditionPartAuto;
		}

		public QuittanceDesignAuto getQuittanceDesignAuto() {
			return quittanceDesignAuto;
		}

		public void setQuittanceDesignAuto(QuittanceDesignAuto quittanceDesignAuto) {
			this.quittanceDesignAuto = quittanceDesignAuto;
		}

		public Exercice getExercice() {
			return exercice;
		}

		public void setExercice(Exercice exercice) {
			this.exercice = exercice;
		}

		public Conducteur getCduc() {
			return cduc;
		}

		public void setCduc(Conducteur cduc) {
			this.cduc = cduc;
		}

		public void setCarteGriseMB(CarteGriseMB carteGriseMB) {
			this.carteGriseMB = carteGriseMB;
		}

		public ManagedQuittanceAuto getManagedQuittanceAuto() {
			return managedQuittanceAuto;
		}

		public void setManagedQuittanceAuto(ManagedQuittanceAuto managedQuittanceAuto) {
			this.managedQuittanceAuto = managedQuittanceAuto;
		}

		public GarantieMB getGarantieMB() {
			return garantieMB;
		}

		public void setGarantieMB(GarantieMB garantieMB) {
			this.garantieMB = garantieMB;
		}

		public ContratMB getContratMB() {
			return contratMB;
		}

		public void setContratMB(ContratMB contratMB) {
			this.contratMB = contratMB;
		}

		public ClientMB getClientMB() {
			return clientMB;
		}

		public void setClientMB(ClientMB clientMB) {
			this.clientMB = clientMB;
		}



		public RecupObjetRow getRecupObjetRow() {
			return recupObjetRow;
		}



		public void setRecupObjetRow(RecupObjetRow recupObjetRow) {
			this.recupObjetRow = recupObjetRow;
		}



		public Contrat getContratF() {
			return contratF;
		}



		public void setContratF(Contrat contratF) {
			this.contratF = contratF;
		}



		public Avenant getAvenantF() {
			return avenantF;
		}



		public void setAvenantF(Avenant avenantF) {
			this.avenantF = avenantF;
		}



		public GarantiesCompagnies getGarantiesCompagnies() {
			return garantiesCompagnies;
		}



		public void setGarantiesCompagnies(GarantiesCompagnies garantiesCompagnies) {
			this.garantiesCompagnies = garantiesCompagnies;
		}



		public VerificationAuthentif getVerificationAuthentif() {
			return verificationAuthentif;
		}



		public void setVerificationAuthentif(VerificationAuthentif verificationAuthentif) {
			this.verificationAuthentif = verificationAuthentif;
		}*/
	}

	
	
	

