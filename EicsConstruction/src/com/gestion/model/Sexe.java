package com.gestion.model;

// Generated 8 avr. 2016 23:58:01 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sexe generated by hbm2java
 */
@Entity
@Table(name = "sexe", catalog = "bdeics")
public class Sexe implements java.io.Serializable {

	private String codeSexe;
	private String libelleSexe;
	private Set<Client> clients = new HashSet<Client>(0);
	private Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>(0);

	public Sexe() {
	}

	public Sexe(String codeSexe) {
		this.codeSexe = codeSexe;
	}

	public Sexe(String codeSexe, String libelleSexe, Set<Client> clients,
			Set<Utilisateur> utilisateurs) {
		this.codeSexe = codeSexe;
		this.libelleSexe = libelleSexe;
		this.clients = clients;
		this.utilisateurs = utilisateurs;
	}

	@Id
	@Column(name = "CODE_SEXE", unique = true, nullable = false, length = 20)
	public String getCodeSexe() {
		return this.codeSexe;
	}

	public void setCodeSexe(String codeSexe) {
		this.codeSexe = codeSexe;
	}

	@Column(name = "LIBELLE_SEXE", length = 30)
	public String getLibelleSexe() {
		return this.libelleSexe;
	}

	public void setLibelleSexe(String libelleSexe) {
		this.libelleSexe = libelleSexe;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sexe")
	public Set<Client> getClients() {
		return this.clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sexe")
	public Set<Utilisateur> getUtilisateurs() {
		return this.utilisateurs;
	}

	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

}
