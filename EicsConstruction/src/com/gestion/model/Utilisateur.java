package com.gestion.model;

// Generated 8 avr. 2016 23:58:01 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Utilisateur generated by hbm2java
 */
@Entity
@Table(name = "utilisateur", catalog = "bdeics")
public class Utilisateur implements java.io.Serializable {

	private String codeUtilisateur;
	private Nationalite nationalite;
	private Profil profil;
	private Sexe sexe;
	private String matricule;
	private String nomUtilisateur;
	private String prenomUtilisateur;
	private Date dateCreationUtilisateur;
	private String loginUtilisateur;
	private String motPasse;
	private String mailUtilisateur;
	private Boolean activite;
	private String boitePostUt;
	private String numtelUt;
	private String faxUt;
	private String photoUt;
	private Set<Maison> maisons = new HashSet<Maison>(0);
	private Set<Contrat> contrats = new HashSet<Contrat>(0);
	private Set<Versement> versements = new HashSet<Versement>(0);
	private Set<Facture> factures = new HashSet<Facture>(0);

	public Utilisateur() {
	}

	public Utilisateur(String codeUtilisateur, Nationalite nationalite,
			Profil profil, Sexe sexe) {
		this.codeUtilisateur = codeUtilisateur;
		this.nationalite = nationalite;
		this.profil = profil;
		this.sexe = sexe;
	}

	public Utilisateur(String codeUtilisateur, Nationalite nationalite,
			Profil profil, Sexe sexe, String matricule, String nomUtilisateur,
			String prenomUtilisateur, Date dateCreationUtilisateur,
			String loginUtilisateur, String motPasse, String mailUtilisateur,
			Boolean activite, String boitePostUt, String numtelUt,
			String faxUt, String photoUt, Set<Maison> maisons,
			Set<Contrat> contrats, Set<Versement> versements,
			Set<Facture> factures) {
		this.codeUtilisateur = codeUtilisateur;
		this.nationalite = nationalite;
		this.profil = profil;
		this.sexe = sexe;
		this.matricule = matricule;
		this.nomUtilisateur = nomUtilisateur;
		this.prenomUtilisateur = prenomUtilisateur;
		this.dateCreationUtilisateur = dateCreationUtilisateur;
		this.loginUtilisateur = loginUtilisateur;
		this.motPasse = motPasse;
		this.mailUtilisateur = mailUtilisateur;
		this.activite = activite;
		this.boitePostUt = boitePostUt;
		this.numtelUt = numtelUt;
		this.faxUt = faxUt;
		this.photoUt = photoUt;
		this.maisons = maisons;
		this.contrats = contrats;
		this.versements = versements;
		this.factures = factures;
	}

	@Id
	@Column(name = "CODE_UTILISATEUR", unique = true, nullable = false, length = 20)
	public String getCodeUtilisateur() {
		return this.codeUtilisateur;
	}

	public void setCodeUtilisateur(String codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODE_NAT", nullable = false)
	public Nationalite getNationalite() {
		return this.nationalite;
	}

	public void setNationalite(Nationalite nationalite) {
		this.nationalite = nationalite;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODE_PROFIL", nullable = false)
	public Profil getProfil() {
		return this.profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODE_SEXE", nullable = false)
	public Sexe getSexe() {
		return this.sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	@Column(name = "MATRICULE", length = 20)
	public String getMatricule() {
		return this.matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	@Column(name = "NOM_UTILISATEUR", length = 25)
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	@Column(name = "PRENOM_UTILISATEUR", length = 60)
	public String getPrenomUtilisateur() {
		return this.prenomUtilisateur;
	}

	public void setPrenomUtilisateur(String prenomUtilisateur) {
		this.prenomUtilisateur = prenomUtilisateur;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_CREATION_UTILISATEUR", length = 10)
	public Date getDateCreationUtilisateur() {
		return this.dateCreationUtilisateur;
	}

	public void setDateCreationUtilisateur(Date dateCreationUtilisateur) {
		this.dateCreationUtilisateur = dateCreationUtilisateur;
	}

	@Column(name = "LOGIN_UTILISATEUR", length = 30)
	public String getLoginUtilisateur() {
		return this.loginUtilisateur;
	}

	public void setLoginUtilisateur(String loginUtilisateur) {
		this.loginUtilisateur = loginUtilisateur;
	}

	@Column(name = "MOT_PASSE", length = 30)
	public String getMotPasse() {
		return this.motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}

	@Column(name = "MAIL_UTILISATEUR", length = 50)
	public String getMailUtilisateur() {
		return this.mailUtilisateur;
	}

	public void setMailUtilisateur(String mailUtilisateur) {
		this.mailUtilisateur = mailUtilisateur;
	}

	@Column(name = "ACTIVITE")
	public Boolean getActivite() {
		return this.activite;
	}

	public void setActivite(Boolean activite) {
		this.activite = activite;
	}

	@Column(name = "BOITE_POST_UT", length = 30)
	public String getBoitePostUt() {
		return this.boitePostUt;
	}

	public void setBoitePostUt(String boitePostUt) {
		this.boitePostUt = boitePostUt;
	}

	@Column(name = "NUMTEL_UT", length = 30)
	public String getNumtelUt() {
		return this.numtelUt;
	}

	public void setNumtelUt(String numtelUt) {
		this.numtelUt = numtelUt;
	}

	@Column(name = "FAX_UT", length = 30)
	public String getFaxUt() {
		return this.faxUt;
	}

	public void setFaxUt(String faxUt) {
		this.faxUt = faxUt;
	}

	@Column(name = "PHOTO_UT", length = 30)
	public String getPhotoUt() {
		return this.photoUt;
	}

	public void setPhotoUt(String photoUt) {
		this.photoUt = photoUt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Maison> getMaisons() {
		return this.maisons;
	}

	public void setMaisons(Set<Maison> maisons) {
		this.maisons = maisons;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Contrat> getContrats() {
		return this.contrats;
	}

	public void setContrats(Set<Contrat> contrats) {
		this.contrats = contrats;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Versement> getVersements() {
		return this.versements;
	}

	public void setVersements(Set<Versement> versements) {
		this.versements = versements;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Facture> getFactures() {
		return this.factures;
	}

	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}

}