package com.gestion.objetService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Maison;
import com.gestion.objetDao.IDao;

@org.springframework.stereotype.Service
@Transactional(readOnly=true)
public class Service implements ObjectService {

	@Autowired
	IDao dao;
 
 
	@Override
	@Transactional(readOnly=false)
	public void addObject(Object objet) {
		
		// TODO Auto-generated method stub
		getDao().addObject(objet);
	}

	@Override
	@Transactional
	public Object getObjectById(int id, String objet) {
		// TODO Auto-generated method stub
	return	getDao().getObjectById(id, objet);
		 
	}

	@Override
	@Transactional
	public Object getObjectById(String id, String objet) {
		// TODO Auto-generated method stub
		return getDao().getObjectById(id, objet);
		 
	}
	
	
	@Transactional(readOnly=false)
	@Override
	public void updateObject(Object objet) {
		// TODO Auto-generated method stub
		getDao().updateObject(objet);
	}
	
	@Transactional(readOnly=false)
	@Override
	public void deleteObject(Object objet) {
		// TODO Auto-generated method stub
		getDao().deleteObject(objet);
	}
	
	
	public String getCodeTable(String pseudo, int taillCar, int taillChifr,
			String nomTable, String nomCOL){
		return getDao().getCodeTable(pseudo, taillCar, taillChifr, nomTable, nomCOL);
	}

	@Override
	public List getObjects(String objet) {
		// TODO Auto-generated method stub
	return	getDao().getObjects(objet);
		 
	}

	@Override
	public List<Object> getojects(Object object) {
		// TODO Auto-generated method stub
	return	getDao().getojects(object);
		 
	}
	/**
	 * Get Object List Garantie by Code Risque
	 * 
	 */


	@Transactional(readOnly=false)
	@Override
	public Object getByIdPK(Object object, String table){
		
		return getDao().getByIdPK(object, table);
	}
	
	@Override
	@Transactional
	public Object getById(String Table, String key, String id, Class TableClass) {
		return getDao().getById(Table, key, id, TableClass);
	}
		
	
	
	@Override
	@Transactional
	public List<Client> filtrerclient(String typeFiltre, String filtre) {
		return getDao().filtrerclient(typeFiltre, filtre);
	}
	
	@Transactional
	public List<Maison> filtrermaison(String typeFiltre, String filtre) {
		return getDao().filtrermaison(typeFiltre, filtre);
	}
	
	@Override
	@Transactional
	public List<Facture> filtrerfacture(String typeFiltre, String filtre) {
		return getDao().filtrerfacture(typeFiltre, filtre);
	}
	
	@Override
	@Transactional
	public List<Contrat> filtrercontrat(String typeFiltre, String filtre) {
		return getDao().filtrercontrat(typeFiltre, filtre);
	}
	
	
	
	
	
	/*public List<Personne> personneByNom(String nom){
		return getDao().personneByNom(nom);
		}
	

	@Override
	public Personne personneByLogin(String login, String motPass) {
		return getDao().personneByLogin(login, motPass);
	}
	
	@Override
	public List<Physique> checkPersonPhysique(Personne personne, Physique physique) {
		return getDao().checkPersonPhysique(personne, physique);
	}
	
	public boolean chercherLogin(String paramLogin) {
		return getDao().chercherLogin(paramLogin);
	}
	
	@Override
	public Personne RecupererUtilisateurCourrant() {
		// TODO Auto-generated method stub
		return getDao().RecupererUtilisateurCourrant();
	}

		@Override
		public List<Personne> personneByLogin(String login) {
			// TODO Auto-generated method stub
			return getDao().personneByLogin(login);
		}*/
		
		//getter et setter de Idao qui a été injecté
		
			public IDao getDao() {
				return dao;
				}
			
			public void setDao(IDao dao) {
				this.dao = dao;
			}

			
			
			
}
