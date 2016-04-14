/*package com.gestion.security;

import java.util.Date;

import com.projet.model.TAssignation;
import com.projet.model.TFonction;
import com.projet.model.TMotdepasse;
import com.projet.model.TOperateur;
import com.projet.utilitaires.AutorityClass;

@org.springframework.stereotype.Service
public class UserService implements IUserService {

	private TOperateur operateurs = new TOperateur();
	private TMotdepasse motdepasses = new TMotdepasse();
	private TFonction fonctions = new TFonction();
	private Date dateCons;
	private AutorityClass autoritys = new AutorityClass();
	
	public TFonction getFonction(TOperateur operateur){
		//setOperateurs(operateur);
		TFonction f = new TFonction();
		for(TAssignation ass: operateur.getTAssignations()){
			if(ass.getAssStatut().intValue()==1){
				setFonctions(ass.getTFonction());
				f = ass.getTFonction();	
			}
		}
		return f;
	}
	
	public TMotdepasse getMotPasse(TOperateur operateur){
		TMotdepasse m = new TMotdepasse();
		for(TMotdepasse mdp: operateur.getTMotdepasses()){
			if(mdp.getMdpStatut().intValue()==1){
				setMotdepasses(mdp);
			m = mdp;	
			}
		}
		return m;
	}
	
	
	public AutorityClass cleanAutority(){
		AutorityClass autority = new AutorityClass();
		
		autority.setMenuAdmin(false);
		autority.setMenucreation(false);
		autority.setMenuImputation(false);
		autority.setMenuExecution(false);
		setAutoritys(autority);
		return autority;
	}
	
	
	public AutorityClass getAutorisation(String role){
		AutorityClass autority = new AutorityClass();
		switch (role) {
		case "ROLE_ADM":
			autority.setMenuAdmin(true);
			autority.setMenucreation(false);
			autority.setMenuImputation(false);
			autority.setMenuExecution(false);
			break;
		
		case "ROLE_SCV":
			autority.setMenuAdmin(false);
			autority.setMenucreation(false);
			autority.setMenuImputation(true);
			autority.setMenuExecution(false);
			break;
			
		case "ROLE_CAB":
			autority.setMenuAdmin(false);
			autority.setMenucreation(true);
			autority.setMenuImputation(false);
			autority.setMenuExecution(false);
			break;
			
		case "ROLE_CPT":
			autority.setMenuAdmin(false);
			autority.setMenucreation(false);
			autority.setMenuImputation(false);
			autority.setMenuExecution(true);
			break;

		default:
			autority.setMenuAdmin(false);
			autority.setMenucreation(false);
			autority.setMenuImputation(false);
			autority.setMenuExecution(false);
			break;
		}
		setAutoritys(autority);
		return autority;
	}
	public String getRole(String role){
		String Role = "";
		
		switch (role) {
		case "ROLE_ADM":
			Role = "ROLE_ADM";
			break;
		
		case "ROLE_SCV":
			Role = "ROLE_USER";
			break;
			
		case "ROLE_CAB":
			Role = "ROLE_USER";
			break;
			
		case "ROLE_CPT":
			Role = "ROLE_USER";
			break;

	
		}
	
		return Role;
	}

	public TOperateur getOperateurs() {
		return operateurs;
	}

	public void setOperateurs(TOperateur operateurs) {
		this.operateurs = operateurs;
	}

	public TMotdepasse getMotdepasses() {
		return motdepasses;
	}

	public void setMotdepasses(TMotdepasse motdepasses) {
		this.motdepasses = motdepasses;
	}

	public TFonction getFonctions() {
		return fonctions;
	}

	public void setFonctions(TFonction fonctions) {
		this.fonctions = fonctions;
	}

	public Date getDateCons() {
		return dateCons;
	}

	public void setDateCons(Date dateCons) {
		this.dateCons = dateCons;
	}

	public AutorityClass getAutoritys() {
		return autoritys;
	}

	public void setAutoritys(AutorityClass autoritys) {
		this.autoritys = autoritys;
	}
	
	
}
*/