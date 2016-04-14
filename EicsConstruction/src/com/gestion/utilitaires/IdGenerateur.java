package com.gestion.utilitaires;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Batiment;
import com.gestion.model.Maison;
import com.gestion.objetService.ObjectService;



@Component
public class IdGenerateur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ObjectService objectService;

	

	public String getIdsexe() {
		String pseudo = "Codsex";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"sexe", "CODE_SEXE");
		return formId;
	}

	public String getIdnat() {
		String pseudo = "Codnat";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"nationalite", "CODE_NAT");
		return formId;
	}
	
	public String getIdfrn() {
		String pseudo = "Codfrn";
		String formId = getObjectService().getCodeTable(pseudo, 6, 2,
				"frns_pharm", "NUM_FRNS");
		return formId;
	}
	
	public String getIdtarif() {
		String pseudo = "Codtar";
		String formId = getObjectService().getCodeTable(pseudo, 6, 2,
				"tarif", "CODE_TARIF");
		return formId;
	}
	public String getIddonesup() {
		String pseudo = "Coddons";
		String formId = getObjectService().getCodeTable(pseudo, 7, 2,
				"donnees_sup", "CODE_DONSUP");
		return formId;
	}
	
	public String getIdprof() {
		String pseudo = "Prof";
		String formId = getObjectService().getCodeTable(pseudo, 4, 3,
				"profession", "CODE_PROFESSION");
		return formId;
	}
	
	public String gettypemaison() {
		String pseudo = "Typmais";
		String formId = getObjectService().getCodeTable(pseudo, 7, 2,
				"type_maison", "CODE_TYPMAISON");
		return formId;
	}
	
	public String getIdville() {
		String pseudo = "Ville";
		String formId = getObjectService().getCodeTable(pseudo, 5, 3,
				"ville", "CODE_VILLE");
		return formId;
	}
	
	public String getIdcomune() {
		String pseudo = "Commune";
		String formId = getObjectService().getCodeTable(pseudo, 7, 2,
				"commune", "CODE_COMMUNE");
		return formId;
	}
	
	
	public String getIdmaison() {
		String pseudo = "maison";
		String formId = getObjectService().getCodeTable(pseudo, 6, 4,
				"maison", "CODE_MAIS");
		return formId;
	}
	
	
	public String getIdma(Batiment batiment) {
		

		String pseudo = batiment.getLibelleBat() + "MAB";
		String Idmaison = getObjectService().getCodeTable(pseudo, 25, 4,
				"maison", "CODE_MAIS");
		//System.out.println("****Id de la du trafic =" + Idtrafic);
		return Idmaison;
	}
	
	
	public String getIdprotomaison() {
		String pseudo = "Proto";
		String formId = getObjectService().getCodeTable(pseudo, 5, 3,
				"prototype_maison", "CODE_PROTOTYPE");
		return formId;
	}
	
	public String getIdimage() {
		String pseudo = "Image";
		String formId = getObjectService().getCodeTable(pseudo, 5, 3,
				"image", "CODE_IMAGE");
		return formId;
	}
	
	//
	public String getIdadr() {
		String pseudo = "adr";
		String formId = getObjectService().getCodeTable(pseudo, 3, 2,
				"Adresse", "CODE_ADRESSE");
		return formId;
	}
	
	public String getIdcontrat() {
		String pseudo = "codcont";
		String formId = getObjectService().getCodeTable(pseudo, 7, 3,
				"contrat", "CODE_CONTRAT");
		return formId;
	}
	//a revoir
	public String getIdtypeclient() {
		String pseudo = "Typclt";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"type_client", "NUM_TYPECLT");
		return formId;
	}
	
	
	
	public String getIdpays() {
		String pseudo = "codpays";
		String formId = getObjectService().getCodeTable(pseudo, 7, 3,
				"pays", "CODE_PAYS");
		return formId;
	}
	
	public String getIduser() {
		String pseudo = "user";
		String formId = getObjectService().getCodeTable(pseudo, 4, 3,
				"Utilisateur", "CODE_UTILISATEUR");
		return formId;
	}
	
	
	public String getIdfonction() {
		String pseudo = "fonct";
		String formId = getObjectService().getCodeTable(pseudo, 5, 3,
				"Fonction", "CODE_FONCTION");
		return formId;
	}
	
	public String getIdtypeversement() {
		String pseudo = "codtver";
		String formId = getObjectService().getCodeTable(pseudo, 7, 3,
				"type_versement", "CODE_TYPEVERS");
		return formId;
	}
	
	
	public String getIdversement() {
		String pseudo = "codvers";
		String formId = getObjectService().getCodeTable(pseudo, 7, 3,
				"versement", "CODE_VERS");
		return formId;
	}
	
	public String getIdfacture() {
		String pseudo = "codfact";
		String formId = getObjectService().getCodeTable(pseudo, 7, 3,
				"facture", "CODE_FACT");
		return formId;
	}
	
	public String getIdbatiment() {
		String pseudo = "codbat";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"batiment", "CODE_BAT");
		return formId;
	}
	
	public String getIdtypclt() {
		String pseudo = "Typclt";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"type_client", "CODE_TYPECLT");
		return formId;
	}
	/*public String getIdlotmed(Medicament med) {
		String pseudo = med.getNumMed()+"codlmed";
		String formId = getObjectService().getCodeTable(pseudo, 6, 3,
				"lot_medicament", "	CODE_LOT_MEDICAMENT");
		return formId;
	}*/
	
	
	
	
	
	
	/*public String getPoliceID(String pVte, String user, String cAt) {
		String pV = pVte.substring(2);
		String Us = user;
		int i = Calendar.getInstance().get(Calendar.YEAR);
		String annee = "" + i;
		String pseudo = pV + Us + annee.substring(2) + cAt.substring(0, 3);
		String IdPolice = getObjectService().getCodeTable(pseudo, 12, 3,
				"contrat", "NUM_POLICE");
		System.out.println("****Id de la police =" + IdPolice);
		return IdPolice;
	}

	public String getIdNewAvenant(String numPolice) {

		String pseudo = numPolice + "-Av";
		String IdAvenant = getObjectService().getCodeTable(pseudo, 18, 2,
				"avenant", "NUM_AVENANT");
		System.out.println("****Id de l' Avenant =" + IdAvenant);
		return IdAvenant;
	}

	public String getIdVehiAss(Contrat ctrat) {
		String IdVehiAss = ctrat.getId() + "Va";
		// size=15+2
		return IdVehiAss;
	}

	public String getIdVehicule(Contrat ctrat) {
		// String pseudo = vehiAss.getId().substring(0, 17);
		String pseudo = ctrat.getId() + "-V";
		// 001KA1014AUT009-V001 = 17+3
		String IdVehi = getObjectService().getCodeTable(pseudo, 17, 3,
				"vehicule", "CODE_VEHICULE");
		return IdVehi;
	}

	public String getIdHistoMvmt(Vehicule vehi) {
		// idVehi= 15+"-V"+numOrd(3)=20
		// histoMvmt = idVehi(20)+"-Hmv"(4)+numOrd(3)=27
		// 001KA1015AUT013-V001-Hmv001

		String pseudo = vehi.getId() + "-Hmv";
		String IdHistoMvmt = "";

		if (vehi.getId().length() == 26) {
			// idVehi= 001KA1015AUT011-Av01Va-V01
			// histoMvmt = idVehi(26)+"-Hmv"(4)+numOrd(3)=33
			IdHistoMvmt = getObjectService().getCodeTable(pseudo, 30, 3,
					"histo_mouvement", "CODE_HISTO_MOUVEMENT");
		}

		if (vehi.getId().length() == 21) {
			// idVehi= 001KA1014AUT004Va-V01(21)+"-Hmv"(4)+numOrd(3)=28
			IdHistoMvmt = getObjectService().getCodeTable(pseudo, 25, 3,
					"histo_mouvement", "CODE_HISTO_MOUVEMENT");
		}
		if (vehi.getId().length() == 20) {
			// idVehi= 001KA1015AUT015-V001
			// histoMvmt = idVehi(20)+"-Hmv"(4)+numOrd(3)=27
			IdHistoMvmt = getObjectService().getCodeTable(pseudo, 24, 3,
					"histo_mouvement", "CODE_HISTO_MOUVEMENT");
		}

		return IdHistoMvmt;
	}

	public String getIdHistoPropVehi(String IdVehi) {
		String pseudo = IdVehi + "-H";
		String idHisto = "";
		//

		if (IdVehi.length() == 26) {
			// idVehi= 001KA1015AUT011-Av01Va-V01
			// histoProp = idVehi(26)+"-H"(2)+numOrd(2)=30
			idHisto = getObjectService().getCodeTable(pseudo, 28, 2,
					"histo_proprietes_vehicule", "CODE_HISTO_VEHICULE");
		}

		if (IdVehi.length() == 21) {
			// idVehi= 001KA1014AUT004Va-V01
			// histoProp = idVehi(21)+"-H"(2)+numOrd(2)=25
			idHisto = getObjectService().getCodeTable(pseudo, 23, 2,
					"histo_proprietes_vehicule", "CODE_HISTO_VEHICULE");
		}

		if (IdVehi.length() == 20) {
			// idVehi= 001KA1015AUT015-V001
			// histoProp = idVehi(20)+"-H"(2)+numOrd(2)=24
			idHisto = getObjectService().getCodeTable(pseudo, 22, 2,
					"histo_proprietes_vehicule", "CODE_HISTO_VEHICULE");
		}
		return idHisto;
	}

	public String getIdListEngin(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListEngin = Av + "" + num + "" + "LE";
		System.out.println("****Id de la Lite Engin =" + IdListEngin);
		return IdListEngin;
	}

	public String getIdEngin(ListeCorpsEngin lEng) {
		// l'id du trafic dans le cas des autres avenant

		String pseudo = lEng.getId();
		String IdEng = getObjectService().getCodeTable(pseudo, 22, 3,
				"corps_engin", "CODE_ENGIN");
		System.out.println("****Id de la de l'engin =" + IdEng);
		return IdEng;
	}

	public String getIdEngin(String lEng) {
		// l'id du trafic dans le cas des autres avenant

		String pseudo = lEng;
		String IdEng = getObjectService().getCodeTable(pseudo, 22, 3,
				"corps_engin", "CODE_ENGIN");
		System.out.println("****Id de la de l'engin =" + IdEng);
		return IdEng;
	}

	public String getIdListTrafic(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListTrafic = Av + "" + num + "" + "L";
		System.out.println("****Id de la Lite trafic =" + IdListTrafic);
		return IdListTrafic;
	}

	public String getIdTrafic(ListeTrafic lTraf) {
		// l'id du trafic dans le cas des autres avenant

		String pseudo = lTraf.getId() + "T";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 22, 3,
				"trafic", "CODE_TRAFIC");
		System.out.println("****Id de la du trafic =" + Idtrafic);
		return Idtrafic;
	}

	public String getIdGarChoisiTP(Aliment aliment) {
		String pseudo = aliment.getId() + "GT";
		String IdGarchoi = getObjectService()
				.getCodeTable(pseudo, 32, 2, "garantie_choisie_transport",
						"CODE_GARANTIE_CHOISIE_TRANSPORT");
		System.out.println("****Id de la GarantieChoisie de l'Aliment "
				+ aliment.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdGarChoisiMRH(Habitation habit) {
		String pseudo = habit.getId() + "GH";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 26, 2,
				"garantie_choisie_mrh", "CODE_GARANTIE_CHOISIEMRH");
		System.out.println("****Id de la GarantieChoisie de l'Habitation "
				+ habit.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdTrafic(String lTraf) {
		// l'id du trafic dans le cas des autres avenant

		String pseudo = lTraf + "T";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 22, 3,
				"trafic", "CODE_TRAFIC");
		System.out.println("****Id de la du trafic =" + Idtrafic);
		return Idtrafic;
	}

	public String getIdEtatc4() {

		String pseudo = "EC4";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 3, 4,
				"etat_c4", "CODEENGAGEMENTS");
		return Idtrafic;
	}

	public String getIdEtatc55() {

		String pseudo = "E5";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 2, 6,
				"etat_c5", "CODE_ETATC5");
		return Idtrafic;
	}

	public String getIdEtatc10b() {

		String pseudo = "EB";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 2, 6,
				"etatc10_tab_b", "CODEETATC10B");
		return Idtrafic;
	}

	public String getIdEtatCima() {

		String pseudo = "EC";
		String Idtrafic = getObjectService().getCodeTable(pseudo, 2, 6,
				"etat_cima", "CODE_ETAT_CIMA");
		return Idtrafic;
	}

	public String getIdQuittance(String numAvenant) {
		// l'id du trafic dans le cas des autres avenant
		String idQuit = "Qt-" + numAvenant;
		System.out.println("****Id de la quittance =" + idQuit);
		return idQuit;
	}

	public String getIdListHabitation(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListHabitation = Av + "" + num + "" + "LH";
		System.out.println("****Id de la Lite Habitation =" + IdListHabitation);
		return IdListHabitation;
	}

	public String getIdHabitation(ListeHabitation lHabit) {
		// l'id de l'Habitation dans le cas des autres avenant

		String pseudo = lHabit.getId();
		String IdHabit = getObjectService().getCodeTable(pseudo, 22, 3,
				"habitation", "CODE_HABITATION");
		System.out.println("****Id de la de l'Habitation =" + IdHabit);
		return IdHabit;
	}

	public String getIdHabitation(String lHabit) {
		// l'id de l'Habitation dans le cas des autres avenant

		String pseudo = lHabit;
		String IdHabit = getObjectService().getCodeTable(pseudo, 22, 3,
				"habitation", "CODE_HABITATION");
		System.out.println("****Id de la de l'Habitation =" + IdHabit);
		return IdHabit;
	}

	public String getIdAssure(String lAssure) {
		// l'id de l'Assure dans le cas des autres avenant

		String pseudo = lAssure;
		String IdAssure = getObjectService().getCodeTable(pseudo, 22, 3,
				"assure_ia", "NUM_IDENTIFICATION");
		System.out.println("****Id de l'Assure =" + IdAssure);
		return IdAssure;
	}

	public String getIdPartieAverse(String padverse) {

		String pseudo = padverse;
		String Idsinistre = getObjectService().getCodeTable(pseudo, 22, 3,
				"partie_adverse", "code_partie_adverse");
		System.out.println("****Id de l'Assure =" + Idsinistre);
		return Idsinistre;
	}

	public String getIdListAssure(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListAssure = Av + "" + num + "" + "L";
		System.out.println("****Id de la Liste Assure =" + IdListAssure);
		return IdListAssure;
	}

	public String getIdListAdherent(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListAdherent = Av + "" + num + "" + "L";
		System.out.println("****Id de la Liste Adherent =" + IdListAdherent);
		return IdListAdherent;
	}

	public String getIdListGestionConfie(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListAdherent = Av + "" + num + "" + "L";
		System.out.println("****Id de la Liste Adherent =" + IdListAdherent);
		return IdListAdherent;
	}

	public String getIdListgestionconfieformule(Avenant Avn) {

		int num = Integer.valueOf(Avn.getId().substring(18));
		String Av = Avn.getId().substring(0, 18);
		String IdListAdherent = Av + "" + num + "" + "L";
		System.out.println("****Id de la Liste Adherent =" + IdListAdherent);
		return IdListAdherent;
	}

	public String getIdGarChoisieAuto(Vehicule vehi) {
		String pseudo = vehi.getId() + "GA";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 28, 2,
				"garantie_choisie", "CODE_GARANTIE_CHOISIE");
		System.out.println("****Id de la GarantieChoisie du vehicule "
				+ vehi.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdGarChoisiIA(AssureIa assure) {
		String pseudo = assure.getId() + "GIA";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 27, 2,
				"garantie_choisie_ia", "CODE_GRTIE_CHOISIE_IA");
		System.out.println("****Id de la GarantieChoisie de l'Assure "
				+ assure.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdGarChoisiSante(AffilieSante affilieSante) {
		String pseudo = affilieSante.getId() + "GST";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 27, 2,
				"garantie_choisie_sante", "CODE_GARANTIE_CHOISIESANTE");
		System.out.println("****Id de la GarantieChoisie de l'l'Affilié "
				+ affilieSante.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdGarChoisiGc(GestionConfiee gestionConfiee) {
		String pseudo = gestionConfiee.getId() + "GC";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 27, 2,
				"garantie_choisie_GC", "CODE_GARANTIE_CHOISIEGC");
		System.out.println("****Id de la GarantieChoisie de l'l'Affilié "
				+ gestionConfiee.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdGarChoisieNTA(RisqueNta risque) {
		String pseudo = risque.getId() + "-GNTA";
		String IdGarchoi = getObjectService().getCodeTable(pseudo, 29, 2,
				"garantie_choisie_nta", "CODE_GARANTIE_CHOISIENTA");
		System.out.println("****Id de la GarantieChoisie du risque NTA "
				+ risque.getId() + "=" + IdGarchoi);
		return IdGarchoi;
	}

	public String getIdListAffilie(AdherentsSante adherent) {
		String pseudo = adherent.getId() + "-LAF";
		String IdLAF = getObjectService().getCodeTable(pseudo, 28, 2,
				"liste_affilie", "CODE_LISTE_AFFILIE");
		System.out.println("****Id de la ListeAfilie de l'Adherent "
				+ adherent.getId() + " = " + IdLAF);
		return IdLAF;
	}

	public String getIdSinistre(Contrat ctrat) {
		String pseudo = ctrat.getId() + "-SIN";
		String idSin = getObjectService().getCodeTable(pseudo, 19, 3,
				"sinistre", "CODE_SINISTRE");
		return idSin;
	}

	public String getIdVictime(Sinistre S) {
		String pseudo = S.getId() + "-V";
		String idVic = getObjectService().getCodeTable(pseudo, 24, 4,
				"victime", "NUM_VICTIME");
		return idVic;
	}

	public String getIdPartieAdverse(Sinistre sinistre) {
		String pseudo = sinistre.getId() + "-Pad";
		String idPAd = getObjectService().getCodeTable(pseudo, 25, 2,
				"partie_adverse", "CODE_PARTIE_ADVERSE");
		return idPAd;
	}

	public String getIdHistoGC(GestionConfiee gesCf) {
		String pseudo = gesCf.getId() + "-H";
		String idPAd = getObjectService().getCodeTable(pseudo, 26, 3,
				"histo_gestion_confiee", "CODE_HISTO_GC");
		return idPAd;
	}

	public String getIdReglement(Quittance quittance) {
		String pseudo = quittance.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 25, 2,
				"reglement", "CODE_REGLEMENT");
		return idReg;
	}
	
	public String getIdReglementApp(AffaireApporteur apporteur) {
		String pseudo = apporteur.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 25, 2,
				"reglement_apporteur", "CODE_REGLEMENT_APP");
		return idReg;
	}
	
	public String getIdReglementsinitre(AyantDroit ayantDroit) {
		String pseudo = ayantDroit.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 10, 2,
				"reglement_sinistre", "CODE_REGLEMENT_SINISTRE");
		return idReg;
	}
	
	public String getIdReglementvictime(Victime victime) {
		String pseudo = victime.getId() + "-Reg";
		if(victime.getId().length()>3){
			 pseudo = "VT"+victime.getId().substring(victime.getId().length()-1, victime.getId().length()) + "-Reg";
		}
		
		String idReg = getObjectService().getCodeTable(pseudo, 7, 3,
				"reglement_sinistre", "CODE_REGLEMENT_SINISTRE");
		return idReg;
	}

	public String getIdReglvict(Victime victime) {
		String pseudo = victime.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 18, 2,
				"reglement_sinistre", "CODE_REGLEMENT_SINISTRE");
		return idReg;
	}
	
	public String getIdReglementinter(Intervention inter) {
		String pseudo = inter.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 7, 2,
				"reglement_sinistre", "CODE_REGLEMENT_SINISTRE");
		return idReg;
	}
	
	public String getIdReglementact(Facture fact) {
		String pseudo = fact.getId() + "-R";
		String idReg = getObjectService().getCodeTable(pseudo, 12, 2,
				"reglement_sinistre", "CODE_REGLEMENT_SINISTRE");
		return idReg;
	}

	
	public String getIdCompteApp(Apporteur apporteur) {
		String pseudo = apporteur.getId() + "-Cpte";
		String idReg = getObjectService().getCodeTable(pseudo, 18, 2,
				"reglement_apporteur", "CODE_REGLEMENT_APP");
		return idReg;
	}

	public String getIdContact(Personne personne) {
		String pseudo = personne.getId() + "-C";
		String idReg = getObjectService().getCodeTable(pseudo, 11, 2,
				"contact", "ID_CONTACT");
		return idReg;
	}

	public String getIdFactureExp() {
		String pseudo = "F-Exp";
		String idFacExp = getObjectService().getCodeTable(pseudo, 5, 6,
				"facture", "CODE_FACTURE");
		return idFacExp;
	}

	public String getIdFactureSin() {
		String pseudo = "F-sin";
		String idFacsin = getObjectService().getCodeTable(pseudo, 5, 6,
				"facture", "CODE_FACTURE");
		return idFacsin;
	}

	public String getIdFactureExpMat() {
		String pseudo = "F-ExpMat";
		String idFacExp = getObjectService().getCodeTable(pseudo, 8, 6,
				"facture", "CODE_FACTURE");
		return idFacExp;
	}

	public String getIdFactureRegSin() {
		String pseudo = "F-RSin";
		String idFacRSin = getObjectService().getCodeTable(pseudo, 6, 6,
				"facture", "CODE_FACTURE");
		return idFacRSin;
	}

	public String getIdFactureActeMed() {
		String pseudo = "F-ActMed";
		String idFacActMed = getObjectService().getCodeTable(pseudo, 8, 6,
				"facture", "CODE_FACTURE");
		return idFacActMed;
	}

	// generation code COMPTE GENERAL DE PERTES ET PROFITS - DOMMAGE
	public String getIdCgpp() {

		String pseudo = "Cpte_GPP";
		String Idcgpp = getObjectService().getCodeTable(pseudo, 8, 6, "cgpp",
				"CODE_CGPP");
		System.out.println("****Id =" + Idcgpp);
		return Idcgpp;
	}

	// etat c10tabA
	public String getIdEtatC10tabA() {

		String pseudo = "C10TabA";
		String Idc10 = getObjectService().getCodeTable(pseudo, 7, 6,
				"etatc10TabA", "CODEETATC10");
		System.out.println("****Id =" + Idc10);
		return Idc10;
	}

	// generation code COMPTE 88-RESULTATS EN INSTANCE D'AFFECTATION-DOMMAGE
	public String getIdCria() {

		String pseudo = "Cpte_Ria";
		String IdRia = getObjectService().getCodeTable(pseudo, 8, 6, "ria",
				"CODE_CRIA");
		System.out.println("****Id =" + IdRia);
		return IdRia;
	}

	public String getIdFeuillBilan(EtatCima eC) {

		String pseudo = eC.getId() + "Bil";
		String idFeuilBil = getObjectService().getCodeTable(pseudo, 11, 3,
				"feuillebilan", "IDBILAN");
		System.out.println("****Id =" + idFeuilBil);
		return idFeuilBil;
	}

	public String getIdLgActBilan(Feuillebilan fB) {

		String pseudo = fB.getId() + "A";
		String idLgActBilan = getObjectService().getCodeTable(pseudo, 15, 2,
				"lg_bilan_actif", "IDLGBILACTIF");
		System.out.println("****Id =" + idLgActBilan);
		return idLgActBilan;
	}

	public String getIdLgPasBilan(Feuillebilan fB) {
		String pseudo = fB.getId() + "P";
		String idLgPasBilan = getObjectService().getCodeTable(pseudo, 15, 2,
				"lg_bilan_passif", "IDLGBILPASSIF");
		System.out.println("****Id =" + idLgPasBilan);
		return idLgPasBilan;
	}

	public String getIdEtatC1(EtatCima eC) {

		String pseudo = eC.getId() + "EC1";
		String idEtatC1 = getObjectService().getCodeTable(pseudo, 11, 3,
				"etatc1", "IDETATC1");
		System.out.println("****Id =" + idEtatC1);
		return idEtatC1;
	}

	public String getIdEtatCegDebit() {

		String pseudo = "CD";
		String IdcegDebit = getObjectService().getCodeTable(pseudo, 2, 6,
				"ceg_debit", "CODE_CEG_DEBIT");
		return IdcegDebit;
	}

	public String getIdEtatCegCredit() {

		String pseudo = "CC";
		String IdcegCredit = getObjectService().getCodeTable(pseudo, 2, 6,
				"ceg_credit", "CODE_CEG_CREDIT");
		return IdcegCredit;
	}
	//
	public String getIdCondPA() {

		String pseudo ="CondA";
		String Idsinistre = getObjectService().getCodeTable(pseudo, 5, 6,
				"conducteur_adverse", "code_conducteur_adverse");
		//System.out.println("****Id de l'Assure =" + Idsinistre);
		return Idsinistre;
	}
	
	
	public String getIdCondSinistre() {

		String pseudo ="CondS";
		String IdCondsinistre = getObjectService().getCodeTable(pseudo, 5, 6,
				"conducteur_sinistre", "CODE_CONDUCTEUR_SINISTRE");
		return IdCondsinistre;
	}
	

	public String getIdLgC1Deb(Etatc1 eC1) {

		String pseudo = eC1.getId() + "D";
		String idLgC1Debit = getObjectService().getCodeTable(pseudo, 15, 2,
				"lgc1debit", "IDLGC1DEBIT");
		System.out.println("****Id =" + idLgC1Debit);
		return idLgC1Debit;
	}

	public String getIdLgC1Cred(Etatc1 eC1) {
		String pseudo = eC1.getId() + "C";
		String idLgC1Credit = getObjectService().getCodeTable(pseudo, 15, 2,
				"lgc1credit", "IDLGC1CREDIT");
		System.out.println("****Id =" + idLgC1Credit);
		return idLgC1Credit;
	}

	

	public String getIdActionCons() {
		String pseudo = "ACN";
		String IdAtionnaireCons = getObjectService().getCodeTable(pseudo, 3, 6,
				"actionnaire_conseil", "CODE_AC");
		return IdAtionnaireCons;
	}

	public String getIdDirection() {
		String pseudo = "DIR";
		String IdDirection = getObjectService().getCodeTable(pseudo, 3, 6,
				"direction", "CODE_PDIRECT");
		return IdDirection;
	}

	public String getIdBranche() {
		String pseudo = "BR";
		String IdBranche = getObjectService().getCodeTable(pseudo, 3, 6,
				"branche", "CODE_BRANCHE");
		return IdBranche;
	}

	public String getIdVente() {
		String pseudo = "VT";
		String IdVente = getObjectService().getCodeTable(pseudo, 3, 6, "vente",
				"CODE_PORTEFEILLES");
		return IdVente;
	}

	public String getIdAchat() {
		String pseudo = "AC";
		String IdAchat = getObjectService().getCodeTable(pseudo, 3, 6, "achat",
				"CODE_PORTEFEILLES");
		return IdAchat;
	}

	public String getIdPaysAction() {
		String pseudo = "PA";
		String IdPaysAction = getObjectService().getCodeTable(pseudo, 3, 6,
				"pays_action", "CODE_PA");
		return IdPaysAction;
	}

	public String getIdAccords() {
		String pseudo = "AD";
		String IdAccords = getObjectService().getCodeTable(pseudo, 3, 6,
				"accords", "CODE_ACCORD");
		return IdAccords;
	}

	public String getIdPersonneCaution() {
		String pseudo = "PC";
		String IdPersCaut = getObjectService().getCodeTable(pseudo, 3, 6,
				"personne_caution", "CODE_PERS_CAUT");
		return IdPersCaut;
	}

	public String getIdEntrepriseCaution() {
		String pseudo = "EC";
		String IdEntreCaut = getObjectService().getCodeTable(pseudo, 3, 6,
				"entreprise_caution", "CODE_ENTREP_CAUT");
		return IdEntreCaut;
	}

	public String getIdEffectifPersonnel() {
		String pseudo = "EP";
		String IdEffPersonel = getObjectService().getCodeTable(pseudo, 3, 6,
				"effectif_personnel", "CODE_EFFECTIF_PERSONNEL");
		return IdEffPersonel;
	}

	public String getIdObligEmprunt() {
		String pseudo = "OE";
		String IdObligEmprunt = getObjectService().getCodeTable(pseudo, 3, 6,
				"obligation_emprunt", "CODE_OB_EMP");
		return IdObligEmprunt;
	}

	public String getIdModifCapital() {
		String pseudo = "MC";
		String IdModifCapital = getObjectService().getCodeTable(pseudo, 3, 6,
				"modifi_capital", "CODE_MODIF_CAP");
		return IdModifCapital;
	}

	public String getIdEtatc10B() {

		String pseudo = "C10B";
		String IdEtatC10b = getObjectService().getCodeTable(pseudo, 4, 6,
				"etatc10b", "CODEETATC10B");
		return IdEtatC10b;
	}
	*/
	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}
	
}
