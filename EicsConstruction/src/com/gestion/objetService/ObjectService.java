package com.gestion.objetService;

import java.util.List;

import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Maison;

public interface ObjectService {
public void addObject(Object objet);
	
	public Object getObjectById(int id, String objet);
	
	public Object getObjectById(String id, String objet);
	
	public void updateObject(Object objet);
	
	public void deleteObject(Object objet);
	
	public  List getObjects(String objet);
	
	public List<Object> getojects(Object object);
	
	public String getCodeTable(String pseudo, int taillCar, int taillChifr,
			String nomTable, String nomCOL);
	
	
	public Object getByIdPK(Object object, String table);
	
	public Object getById(String Table, String key, String id, Class TableClass);

	
	
	
	
	public List<Contrat> filtrercontrat(String typeFiltre, String filtre);
	public List<Client> filtrerclient(String typeFiltre, String filtre);
	public List<Maison> filtrermaison(String typeFiltre, String filtre);
	public List<Facture> filtrerfacture(String typeFiltre, String filtre);
	
	
	
	
	
	/*public Personne RecupererUtilisateurCourrant();
	public List <Personne> personneByLogin(String login);
	public boolean chercherLogin(String paramLogin);
	public List<Personne> personneByNom(String nom);*/
}
