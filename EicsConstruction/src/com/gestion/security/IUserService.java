package com.gestion.security;


import com.gestion.model.Utilisateur;
import com.gestion.utilitaires.AutorityClass;

public interface IUserService {
	public Utilisateur  getuser();
	/*public TFonction getFonction(TOperateur operateur);
	public TMotdepasse getMotPasse(TOperateur operateur);*/
	public AutorityClass cleanAutority();
	public AutorityClass getAutorisation(String role);
}
