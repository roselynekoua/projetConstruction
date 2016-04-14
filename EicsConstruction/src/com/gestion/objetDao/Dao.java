package com.gestion.objetDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Maison;



@Repository
public class Dao implements IDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private static  Logger logger=Logger.getLogger(Dao.class);

	//Injection par Spring
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void addObject(Object objet) {
		
		getSessionFactory().getCurrentSession().save(objet);
		// TODO Auto-generated method stub
		 
	}

	@Override
	public Object getObjectById(int id, String objet) {
		Session session= getSessionFactory().getCurrentSession();
		String query= "from" +" "+ objet + " "+ " where id =?";
		  List liste = session.createQuery(query).setParameter(0,id).list();
		  if (liste.size()==0){
		   return null;}
		return liste.get(0);
	}

	@Override
	public Object getObjectById(String id, String objet) {
		Session session= getSessionFactory().getCurrentSession();
		String query= "from" +" "+ objet + " "+ " where id =?";
		  List liste = session.createQuery(query).setParameter(0,id).list();
		  if (liste.size()==0){
		   return null;}
		
		return liste.get(0);
	}

	@Override
	@Transactional
	public void updateObject(Object objet) {
		// TODO Auto-generated method stub
	getSessionFactory().getCurrentSession().update(objet);
	}

	@Override
	@Transactional
	public void deleteObject(Object objet) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().delete(objet);
	}
	
	@Override
	@Transactional
	public String getCodeTable(String pseudo, int taillCar, int taillChifr,
			String nomTable, String nomCOL) {
		// Methode crï¿½ation d'un id code alphanumrique chronologique d'une
				// ligne de table ds la BD
				

				String query = "SELECT MAX(CAST(SUBSTRING(" + nomCOL + " FROM "
						+ (taillCar + 1) + " FOR " + (taillChifr)
						+ ") AS UNSIGNED )) AS NUMBER FROM " + nomTable + " WHERE "+nomCOL+" LIKE '"+pseudo+"%'";
				Integer v = null;
				try {
					v = (Integer) getSessionFactory().getCurrentSession()
							.createSQLQuery(query)
							.addScalar("NUMBER", StandardBasicTypes.INTEGER).uniqueResult();
					
					String tC = String.valueOf(taillChifr);
					if (v == null) {
						//int numOrdT = i+1;
						String numOrd= String.format("%0"+tC+"d", 1);
						System.out.println("///////Verification requette V null");
						System.out.println("///////Verification requette V null et pseudo = "+pseudo);
						String s = pseudo + numOrd;
						System.out.println("///////Verification requette V null et pseudo + numrd = "+s);
						return s;
					} else {
						v++;
						String numOrd= String.format("%0"+tC+"d", v);
						System.out.println("///////Verification requette V non null");
						String s = pseudo + numOrd;
						return s;
					}
				} catch (HibernateException e) {
					e.printStackTrace();
					return "blag aaa";
				}
				// sess.close();

	}
	
	
	@Override
	public List getObjects(String objet) {
		Session session= getSessionFactory().getCurrentSession();
		String query = "from"+" "+objet;
		List list = session.createQuery(query).list();
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public List<Object> getojects(Object object) {
		Session session= getSessionFactory().getCurrentSession();
	List list = session.createQuery("from"+" "+object).list();
		// TODO Auto-generated method stub
		return list ;
	}
	
	@Transactional
	public Object getByIdPK(Object object, String table)
			throws HibernateException {
		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + table + " O where O.id=:pk");
		query.setParameter("pk", object);
		List list = query.list();
		System.out
				.println("/********************requï¿½te Pk reussie***********************/");
		if (list.size() == 0)
			return null;
		return list.get(0);
	}
	
	@Override
	@Transactional
	public Object getById(String Table, String key, String id, Class TableClass)
			throws HibernateException {
		String query = "SELECT * FROM " + Table + " a  WHERE a." + key + " ='"
				+ id + "' ";
		Object A = getSessionFactory().getCurrentSession()
				.createSQLQuery(query).addEntity(TableClass).uniqueResult();
		return A;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> filtrerclient(String typeFiltre, String filtre) {
		List<Client> list = new ArrayList<Client>();
		String sql = "from Client where " + typeFiltre + " like '%" + filtre
				+ "%'";

		try {
			list = getSessionFactory().getCurrentSession().createQuery(sql)
					.setMaxResults(10).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TOTAL : " + list.size());
		return list;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contrat> filtrercontrat(String typeFiltre, String filtre) {
		List<Contrat> list = new ArrayList<Contrat>();
		String sql = "FROM Contrat where " + typeFiltre + " like '%" + filtre
				+ "%'";

		try {
			list = getSessionFactory().getCurrentSession().createQuery(sql)
					.setMaxResults(10).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TOTAL : " + list.size());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Facture> filtrerfacture(String typeFiltre, String filtre) {
		List<Facture> list = new ArrayList<Facture>();
		String sql = "FROM Facture where " + typeFiltre + " like '%" + filtre
				+ "%'";

		try {
			list = getSessionFactory().getCurrentSession().createQuery(sql)
					.setMaxResults(10).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TOTAL : " + list.size());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Maison> filtrermaison(String typeFiltre, String filtre) {
		List<Maison> list = new ArrayList<Maison>();
		String sql = "FROM Maison where " + typeFiltre + " like '%" + filtre
				+ "%'";

		try {
			list = getSessionFactory().getCurrentSession().createQuery(sql)
					.setMaxResults(10).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TOTAL : " + list.size());
		return list;
	}
	
	
	
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<Livrer> filtrerlivrer(String typeFiltre, String filtre) {
		List<Livrer> list = new ArrayList<Livrer>();
		
		Select B.NUM_MED, B.DSG_MED, A.QTE_LVR, A.PU_LVR from livrer A, medicament B where A.NUM_MED=B.NUM_MED and B.NUM_MED="codmed002" 
		
		String sql = "FROM livrer  where " + typeFiltre + " like '%" + filtre
				+ "%'";

		try {
			list = getSessionFactory().getCurrentSession().createQuery(sql)
					.setMaxResults(10).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TOTAL : " + list.size());
		return list;
	}
	*/
	
	
	
	/*@SuppressWarnings("unchecked")
	public List<Personne> personneByNom(String nom){
		String query = "SELECT `personne`.* FROM personne WHERE (`personne`.`NOM_RAISON_SOCIALE` ='"+nom+"')";
		List<Personne> list = (List<Personne>) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Personne.class).list();
		System.out.println("------->> Taille de la liste Personne: "+list.size());//Clean after
		return list;
	}
	
	
	@Override
	public CompagnieAssurance RecupererCompagnieCourrant() {
		// Recupération du login de l'utilisateur courant
					String paramLogin = "";
					if (FacesContext.getCurrentInstance().getExternalContext()
							.getUserPrincipal() != null) {
						paramLogin = FacesContext.getCurrentInstance().getExternalContext()
								.getUserPrincipal().getName();
						System.out.println("paramLogin:"+paramLogin);

					}
					String query = "SELECT * FROM compagnie_assurance WHERE LOGIN_COMP_ASS='"+ paramLogin + "'";
					CompagnieAssurance connected  = new CompagnieAssurance();
					try {

						connected = (CompagnieAssurance) getSessionFactory().getCurrentSession()
								.createSQLQuery(query).addEntity(CompagnieAssurance.class)
								.uniqueResult();
					} catch (Exception e) {
						logger.error(" Erreur sur la recupération de l'utilisateur");
					}
					return connected;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Personne> personneByLogin(String login) {
		List<Personne> maListe = new ArrayList<>();
		String myQuery = "SELECT `personne`.* FROM personne WHERE (`personne`.`LOGIN_PERS` ='"+login+"')";
		 maListe = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Personne.class).list();
		return maListe;
	}

	public boolean chercherLogin(String paramLogin) {
		boolean etat;
		String str = paramLogin;
		etat = false;
		try {

			String query = "SELECT * FROM `personne` WHERE `LOGIN_PERS`='"
					+ str + "'";
			List list = (List) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(Personne.class).list();
			if (list.size() >= 1) {
				etat = true;
			}
			System.out.println("Etat de la requête:" + etat);
		} catch (Exception e) {
			logger.error(" Problème de Base de données", e);
		}
		return etat;
	}
	
	
	public boolean chercherLoginCompagnie(String paramLogin) {
		boolean etat;
		String str = paramLogin;
		etat = false;
		try {

			String query = "SELECT * FROM `compagnie_assurance` WHERE `LOGIN_COMP_ASS`='"
					+ str + "'";
			List list = (List) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(Personne.class).list();
			if (list.size() >= 1) {
				etat = true;
			}
			System.out.println("Etat de la requête:" + etat);
		} catch (Exception e) {
			logger.error(" Problème de Base de données", e);
		}
		return etat;
	}
	
	@Override
	public Personne RecupererUtilisateurCourrant() {
		// Recupération du login de l'utilisateur courant
					String paramLogin = "";
					if (FacesContext.getCurrentInstance().getExternalContext()
							.getUserPrincipal() != null) {
						paramLogin = FacesContext.getCurrentInstance().getExternalContext()
								.getUserPrincipal().getName();
						System.out.println("paramLogin:"+paramLogin);

					}
					String query = "SELECT * FROM personne WHERE LOGIN_PERS='"+ paramLogin + "'";
					Personne connected = new Personne();
					try {

						connected = (Personne) getSessionFactory().getCurrentSession()
								.createSQLQuery(query).addEntity(Personne.class)
								.uniqueResult();
					} catch (Exception e) {
						logger.error(" Erreur sur la recupération de l'utilisateur");
					}
					return connected;
	}
	
	
	@Override
public Personne personneByLogin(String login, String motPass) {
	String query = "SELECT `personne`.* FROM personne WHERE ((`personne`.`LOGIN_PERS` ='"+login+"') AND (`personne`.`MOT_PASSE_PERS` ='"+motPass+"'))";
	Personne personne  = (Personne) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Personne.class).uniqueResult();
	return personne;
}*/
	
	
	
	
	//getters et setters
		public SessionFactory getSessionFactory() {
			return sessionFactory;}
		
	    public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}
