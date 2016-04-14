/*package com.gestion.managedbean.admin;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.model.Profil;
import com.gestion.model.ProfilUtilisateur;
import com.gestion.model.ProfilUtilisateurId;
import com.gestion.model.Utilisateur;
import com.gestion.objetService.ObjectService;


@Component
public class ManagedProfilUtilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ManagedProfilUtilisateur.class);

	@Autowired
	ObjectService objectService;

	private ProfilUtilisateurId id;

	private Date dateProfilUtilisateur;

	private Utilisateur codeUtilisateur = new Utilisateur();

	private Profil codeProfil = new Profil();

	private List<SelectItem> elements;
	private List<ProfilUtilisateur> ProfilUtilisateurList;

	private UIComponent buttonadd;
	private UIComponent buttonupdate;
	private UIComponent buttondelete;

	public List<String> complete(String query) {
		List<String> results = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {
			results.add(query + i);
		}

		return results;
	}

	public String validateadd1() throws ParseException {
		String bb = "";
		return bb;

	}

	

	public void validateadd() throws ParseException {// ajouter une ligne dans
														// la base de donnée

		id = new ProfilUtilisateurId();
		id.setCodeProfil(getCodeProfil().getCodeProfil());
		id.setCodeUtilisateur(getCodeUtilisateur().getCodeUtilisateur());

		ProfilUtilisateur profilUtilisateur = new ProfilUtilisateur();
		profilUtilisateur.setId(id);
		profilUtilisateur.setDateProfilUtilisateur(getDateProfilUtilisateur());

		boolean checkeProfil = false;
		boolean checkeUtilisateur = false;
		boolean checkeProfilUtilisateur = false;
		for (Object obj : getObjectService().getObjects("Profil")) {
			if (id.getCodeProfil().equals(((Profil) obj).getCodeProfil())) {
				checkeProfil = true;
			}
		}
		for (Object obj : getObjectService().getObjects("Utilisateur")) {

			if (id.getCodeUtilisateur()
					.equals(((Utilisateur) obj).getCodeUtilisateur())) {
				checkeUtilisateur = true;
			}
		}
		for (Object obj : getObjectService().getObjects("ProfilUtilisateur")) {

			if (id.getCodeUtilisateur()
					.equals(((ProfilUtilisateur) obj).getId()
							.getCodeUtilisateur())
					&& id.getCodeProfil()
							.equals(((ProfilUtilisateur) obj).getId()
									.getCodeProfil())) {
				checkeProfilUtilisateur = true;
			}
		}
		FacesMessage message = new FacesMessage("");
		if (checkeProfilUtilisateur) {
			message = new FacesMessage(
					" le profil utilisateur existe dans la base de donnée! ");
		} else {

			if (checkeProfil && checkeUtilisateur) {
				getObjectService().addObject(profilUtilisateur);
				message = new FacesMessage(
						" le profil utilisateur est bien enregistré dans la base de donnée! ");
			} else {
				if (!checkeProfil) {
					message = new FacesMessage(
							"  le code  profil n'existe pas dans la base de donnée!");
				}
				if (!checkeUtilisateur) {
					message = new FacesMessage(
							"  le code utilisateur  n'existe pas dans la base de donnée!");
				}
				if ((!checkeUtilisateur) && (!checkeProfil)) {
					message = new FacesMessage(
							"  le code utilisateur et  le code  profil  n'existe pas dans la base de donnée!");
				}
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(buttonadd.getClientId(context), message);
	}

	public void validateupdate() throws ParseException {// mettre à jour une
														// ligne

		id = new ProfilUtilisateurId();
		id.setCodeProfil(getCodeProfil().getCodeProfil());
		id.setCodeUtilisateur(getCodeUtilisateur().getCodeUtilisateur());

		ProfilUtilisateur profilUtilisateur = new ProfilUtilisateur();
		profilUtilisateur.setId(id);
		profilUtilisateur.setDateProfilUtilisateur(getDateProfilUtilisateur());
		
		FacesMessage message = new FacesMessage("");
		message = new FacesMessage(
				" le profil utilisateur est bien enregistré dans la base de donnée! ");

		boolean checkeProfil = false;
		boolean checkeUtilisateur = false;
		boolean checkeProfilUtilisateur = false;
		for (Object obj : getObjectService().getObjects("Profil")) {
			if (id.getCodeProfil().getId().equals(((Profil) obj).getId())) {
				checkeProfil = true;
			}
		}
		for (Object obj : getObjectService().getObjects("Utilisateur")) {

			if (id.getCodeUtilisateur().getId()
					.equals(((Utilisateur) obj).getId())) {
				checkeUtilisateur = true;
			}
		}
		for (Object obj : getObjectService().getObjects("ProfilUtilisateur")) {

			if (id.getCodeUtilisateur()
					.getId()
					.equals(((ProfilUtilisateur) obj).getId()
							.getCodeUtilisateur().getId())
					&& id.getCodeProfil()
							.getId()
							.equals(((ProfilUtilisateur) obj).getId()
									.getCodeProfil().getId())) {
				checkeProfilUtilisateur = true;
			}
		}
		FacesMessage message = new FacesMessage("");
		if (!checkeProfilUtilisateur) {
			message = new FacesMessage(
					" le profil utilisateur n'existe pas dans la base de donnée! ");
		} else {

			if (checkeProfil && checkeUtilisateur) {
				getObjectService().updateObject(profilUtilisateur);
				message = new FacesMessage(
						" le profil utilisateur est bien enregistré dans la base de donnée! ");
			} else {
				if (!checkeProfil) {
					message = new FacesMessage(
							"  le code  profil n'existe pas dans la base de donnée!");
				}
				if (!checkeUtilisateur) {
					message = new FacesMessage(
							"  le code utilisateur  n'existe pas dans la base de donnée!");
				}
				if ((!checkeUtilisateur) && (!checkeProfil)) {
					message = new FacesMessage(
							"  le code utilisateur et  le code  profil  n'existe pas dans la base de donnée!");
				}
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(buttonadd.getClientId(context), message);
	}

	public void validatedelete() throws ParseException {
		id = new ProfilUtilisateurId();
		id.setCodeProfil(getCodeProfil().getCodeProfil());
		id.setCodeUtilisateur(getCodeUtilisateur().getCodeUtilisateur());

		ProfilUtilisateur profilUtilisateur = new ProfilUtilisateur();
		profilUtilisateur.setId(id);
		profilUtilisateur.setDateProfilUtilisateur(getDateProfilUtilisateur());
		FacesMessage message = new FacesMessage("");
		message = new FacesMessage(
				" le profil utilisateur est bien supprimé de la base de donnée! ");
		boolean checkeProfil = false;
		boolean checkeUtilisateur = false;
		boolean checkeProfilUtilisateur = false;
		for (Object obj : getObjectService().getObjects("Profil")) {
			if (id.getCodeProfil().equals(((Profil) obj).getCodeProfil())) {
				checkeProfil = true;
			}
		}
		for (Object obj : getObjectService().getObjects("Utilisateur")) {

			if (id.getCodeUtilisateur().getId()
					.equals(((Utilisateur) obj).getId())) {
				checkeUtilisateur = true;
			}
		}
		for (Object obj : getObjectService().getObjects("ProfilUtilisateur")) {

			if (id.getCodeUtilisateur()
					.getId()
					.equals(((ProfilUtilisateur) obj).getId()
							.getCodeUtilisateur().getId())
					&& id.getCodeProfil()
							.getId()
							.equals(((ProfilUtilisateur) obj).getId()
									.getCodeProfil().getId())) {
				checkeProfilUtilisateur = true;
			}
		}
		FacesMessage message = new FacesMessage("");
		if (!checkeProfilUtilisateur) {
			message = new FacesMessage(
					" le profil utilisateur n'existe pas dans la base de donnée! ");
		} else {

			if (checkeProfil && checkeUtilisateur) {
				getObjectService().deleteObject(profilUtilisateur);
				message = new FacesMessage(
						" le profil utilisateur est bien enregistré dans la base de donnée! ");
			} else {
				if (!checkeProfil) {
					message = new FacesMessage(
							"  le code  profil n'existe pas dans la base de donnée!");
				}
				if (!checkeUtilisateur) {
					message = new FacesMessage(
							"  le code utilisateur  n'existe pas dans la base de donnée!");
				}
				if ((!checkeUtilisateur) && (!checkeProfil)) {
					message = new FacesMessage(
							"  le code utilisateur et  le code  profil  n'existe pas dans la base de donnée!");
				}
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(buttonadd.getClientId(context), message);
	}

	public List<SelectItem> getElements() {

		if (elements == null) {
			elements = new ArrayList<SelectItem>();
			for (Object obj : getObjectService()
					.getObjects("ProfilUtilisateur")) {
			}
		}
		return elements;
	}

	public void setElements(List<SelectItem> elements) {
		this.elements = elements;
	}

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

	public ProfilUtilisateurId getId() {
		return id;
	}

	public void setId(ProfilUtilisateurId id) {
		this.id = id;
	}

	public void setButtonadd(UIComponent buttonadd) {
		this.buttonadd = buttonadd;
	}

	public UIComponent getButtonadd() {
		return buttonadd;
	}

	public UIComponent getButtonupdate() {
		return buttonupdate;
	}

	public void setButtonupdate(UIComponent buttonupdate) {
		this.buttonupdate = buttonupdate;
	}

	public UIComponent getButtondelete() {
		return buttondelete;
	}

	public void setButtondelete(UIComponent buttondelete) {
		this.buttondelete = buttondelete;
	}

	public List<ProfilUtilisateur> getProfilUtilisateurList() {
		ProfilUtilisateurList = new ArrayList<ProfilUtilisateur>();
		List<Object> listObject = getObjectService().getObjects(
				"ProfilUtilisateur");
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			ProfilUtilisateur ville = (ProfilUtilisateur) it.next();
			try {
				ProfilUtilisateurList.add(ville);
			} catch (Exception e) {
			}
		}
		return ProfilUtilisateurList;
	}

	public void setProfilUtilisateurList(List<ProfilUtilisateur> villeList) {
		this.ProfilUtilisateurList = villeList;
	}

	public Utilisateur getCodeUtilisateur() {
		return codeUtilisateur;
	}

	public void setCodeUtilisateur(Utilisateur codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
	}

	public Date getDateProfilUtilisateur() {
		return dateProfilUtilisateur;
	}

	public void setDateProfilUtilisateur(Date dateProfilUtilisateur) {
		this.dateProfilUtilisateur = dateProfilUtilisateur;
	}

	public Profil getCodeProfil() {
		return codeProfil;
	}

	public void setCodeProfil(Profil codeProfil) {
		this.codeProfil = codeProfil;
	}

}
*/