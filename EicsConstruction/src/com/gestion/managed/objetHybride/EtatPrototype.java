package com.gestion.managed.objetHybride;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

import com.gestion.model.PrototypeMaison;

@Component
public class EtatPrototype {
	
	private PrototypeMaison prototypeMaison = new PrototypeMaison();
	private long qteCommande;
	private BigDecimal acompteRestanSouscripteur;
	private BigDecimal versementSouscripteur;
	private BigDecimal totalVersementattendu;
	private BigDecimal totalVersementRestant;
	
	
	/******************ACCESSEURS************************************/
	public PrototypeMaison getPrototypeMaison() {
		return prototypeMaison;
	}
	public void setPrototypeMaison(PrototypeMaison prototypeMaison) {
		this.prototypeMaison = prototypeMaison;
	}
	public long getQteCommande() {
		return qteCommande;
	}
	public void setQteCommande(long qteCommande) {
		this.qteCommande = qteCommande;
	}
	public BigDecimal getVersementSouscripteur() {
		return versementSouscripteur;
	}
	public void setVersementSouscripteur(BigDecimal versementSouscripteur) {
		this.versementSouscripteur = versementSouscripteur;
	}
	public BigDecimal getTotalVersementattendu() {
		return totalVersementattendu;
	}
	public void setTotalVersementattendu(BigDecimal totalVersementattendu) {
		this.totalVersementattendu = totalVersementattendu;
	}
	public BigDecimal getTotalVersementRestant() {
		return totalVersementRestant;
	}
	public void setTotalVersementRestant(BigDecimal totalVersementRestant) {
		this.totalVersementRestant = totalVersementRestant;
	}
	public BigDecimal getAcompteRestanSouscripteur() {
		return acompteRestanSouscripteur;
	}
	public void setAcompteRestanSouscripteur(BigDecimal acompteRestanSouscripteur) {
		this.acompteRestanSouscripteur = acompteRestanSouscripteur;
	}

}
