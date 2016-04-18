package com.gestion.objetDao;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Utilisateur;
import com.gestion.model.Versement;




@Component
@Transactional
public class RequeteUtilisateur {
	private static Logger logger = Logger.getLogger(RequeteUtilisateur.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	// private Utilisateur utilisateur = new Utilisateur();

	/**
	 * Méthode pour l'utilisateur de la session
	 * 
	 * @return utilisateur
	 * @throws HibernateException
	 */
	public Utilisateur RecupererUtilisateurCourrant() throws HibernateException {
		// Recupération du login de l'utilisateur courant
		String paramLogin = "";
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getUserPrincipal() != null) {
			paramLogin = FacesContext.getCurrentInstance().getExternalContext()
					.getUserPrincipal().getName();

		}
		String query = "SELECT * FROM `utilisateur` WHERE `LOGIN_UTILISATEUR`='"
				+ paramLogin + "'";
		Utilisateur utilisateur = new Utilisateur();
		try {

			utilisateur = (Utilisateur) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(Utilisateur.class)
					.uniqueResult();
		} catch (Exception e) {
			logger.error(" Erreur sur la recupération de l'utilisateur");
		}
		return utilisateur;
	}

	public boolean chercherLogin(String paramLogin) {
		boolean etat;
		String str = paramLogin;
		etat = false;
		try {

			String query = "SELECT * FROM `utilisateur` WHERE `LOGIN_UTILISATEUR`='"
					+ str + "'";
			List list = (List) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(Utilisateur.class).list();
			if (list.size() >= 1) {
				etat = true;
			}
			System.out.println("Etat de la requête:" + etat);
		} catch (Exception e) {
			logger.error(" Problème de Base de données", e);
		}
		return etat;
	}

	
	
	
	
	/*
	public boolean cherchermedoc(String param) {
		boolean etat;
		String str = param;
		etat = false;
		try {

			String query = "SELECT * FROM `Medicament` WHERE `DSG_MED`='"
					+ str + "'";
			List list = (List) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(Medicament.class).list();
			if (list.size() >= 1) {
				etat = true;
			}
			System.out.println("Etat de la requête:" + etat);
		} catch (Exception e) {
			logger.error(" Problème de Base de données", e);
		}
		return etat;
	}
	
	
	//recperer le code du medoc
	@Transactional
	public List<Livrer> getlivrer(String codemedoc, String dsgmedoc) throws HibernateException {

		String myQuery ="Select B.NUM_MED, B.DSG_MED, A.QTE_LVR, A.PU_LVR from livrer A, medicament B where A.NUM_MED=B.NUM_MED and A.NUM_MED='"
				+ codemedoc + "OR  B.DSG_MED ='" +dsgmedoc;
		
		
		List livrer = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Livrer.class).list();

		return livrer;
	}
	
	
	 
	*/
	//recuperer la liste des medicament prescri en ayant le code de l'ordornance
	
	/*@Transactional
	public List<Prescrire> recuplistprescri(String codeordo) throws HibernateException{
	

		String myQuery = "Select * from prescrire Where num_ordo='" + codeordo + "'";
		
		
		List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

		return presr;
	}
	
	
	//recuperer le num ordonance lorsque jai la consultation
	
		@Transactional
		public Consultation recupordo(String codeconsult) throws HibernateException{
		

			String myQuery = "SELECT c.num_ordo FROM consultation c WHERE num_consult='" + codeconsult + "'";
			
			
			Consultation presr = (Consultation) getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Consultation.class).uniqueResult();

			return presr;
		}
	
	
	
	 //recuperer les elements de prescrire e ayant le code du medicamnt
	@Transactional
	public List<Prescrire> recuplistmedsuivi(String codemedoc) throws HibernateException{
	

		String myQuery = "Select * from prescrire Where num_med='" + codemedoc + "'";
		
		
		List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

		return presr;
	}
	
	
	

	 //recuperer les elements de prescrire e ayant le code du l'ordonance
	@Transactional
	public List<Prescrire> recuplistmedord(String codeord) throws HibernateException{
	

		String myQuery = "Select * from prescrire Where num_ordo='" + codeord + "'";
		
		
		List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

		return presr;
	}
	
	
	
	
	
	//avoir la somme des medicament prescris
	@Transactional
	public List<Prescrire> getprescrire(String codemedoc) throws HibernateException {

		String myQuery ="Select SUM(P.QTE_PRS)as qtepresct,SUM(P.QTE_DISTR)as qtedistr  from prescrire P where P.num_med= '"
				+ codemedoc+ "'";
		
		
		List listpresrire = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

		return listpresrire;
	}
	

	
	//recup la liste des consultaion d'un patient
		@Transactional
		public List<Prescrire> recupconsult(String codepatient) throws HibernateException{
			

			String myQuery = "Select * from consultation Where num_pat='" + codepatient + "'";
			
			
			List consul = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Consultation.class).list();

			return consul;
		}
	
		
		//recuperer la liste des medicament prescri en ayant le code du pat
		@Transactional
		public List<Prescrire> listemedocprec(String codepat) throws HibernateException{
		

			String myQuery = "Select * from prescrire Where num_pat='" + codepat + "'";
			
			
			List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

			return presr;
		}	
		
		
		//recuperer la liste des medicamets livrés
				@Transactional
				public List<LotMedicament> listemedoclivre(String codepat) throws HibernateException{
				

					String myQuery = "Select * from lotMedicament ";
					
					
					List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(LotMedicament.class).list();

					return presr;
				}	
		
		
		
		
		
		
				//recuperer la liste des medicament prescri par les medecin
				@Transactional
				public List<Prescrire> listemedocprecristot() throws HibernateException{
				

					String myQuery = "SELECT SUM( V.MONTANT_VERS ) AS qtemontant FROM versement V, facture F WHERE V.CODE_FACT = F.CODE_FACT
                   AND V.CODE_FACT = "codfact001" ";
					
					
					List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

					return presr;
				}			
		
				
				//recuperer la liste des medicament distribués
				
				@Transactional
				public List<Prescrire> recuplistdistri() throws HibernateException{
				

					String myQuery = "SELECT distinct * FROM prescrire  WHERE `QTE_DISTR`!= 0 ";
					
					//String myQuery = "SELECT distinct p.NUM_MED ,m.dsg_med FROM prescrire p,medicament m WHERE  p.NUM_MED = m.NUM_MED   and `QTE_DISTR`!= 0  ";
					List presr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Prescrire.class).list();

					return presr;
				}
				
            //recuperer la liste des patient consuter
				
				@Transactional
	public List<Consultation> recuplisteconultation(String code1,String code2) throws HibernateException{
				
					 
		String myQuery = "SELECT distinct * FROM `consultation` WHERE `DATE_CONS` between '" + code1+ "' and '" + code2 + "'";
					
					//String myQuery = "SELECT distinct p.NUM_MED ,m.dsg_med FROM prescrire p,medicament m WHERE  p.NUM_MED = m.NUM_MED   and `QTE_DISTR`!= 0  ";
		List pati = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Consultation.class).list();

		return pati;
	}	*/
				
				
	 //recuperer la liste des contrat sur une periode
	
	@Transactional
public List<Contrat> recuplistecontat(String code1,String code2) throws HibernateException{
	
		 
String myQuery = "SELECT distinct * FROM `contrat` WHERE `DATE_DEBUT_CONTRAT` between '" + code1+ "' and '" + code2 + "'";
		
		//String myQuery = "SELECT distinct p.NUM_MED ,m.dsg_med FROM prescrire p,medicament m WHERE  p.NUM_MED = m.NUM_MED   and `QTE_DISTR`!= 0  ";
List pati = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Contrat.class).list();

return pati;
}	
		
	
	 //recuperer les elements des contrats a patir du client
		@Transactional
		public List<Contrat> recuplistcontrat(String codeclt) throws HibernateException{
		

			String myQuery = "Select * from contrat Where num_clt='" + codeclt + "'"; 
			
			
			List listecontr = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Contrat.class).list();

			return listecontr;
		}
		
		
		
			
		@Transactional
		public BigDecimal sommevers(String code){
	    	BigDecimal X = BigDecimal.ZERO;
	    	String query = "SELECT SUM( V.MONTANT_VERS ) FROM versement V, facture F WHERE V.CODE_FACT = F.CODE_FACT AND V.CODE_FACT='"+code+"'"; 
	    	//try {
				X=(BigDecimal)getSessionFactory().getCurrentSession().createSQLQuery(query).uniqueResult();
				if(X==null) {
					X=BigDecimal.ZERO;
				
				System.out.println("++++++++++++++++++++++++++++++++++somme++++++++++++"+X);
				return X;
		}
		else{
		return X;
		}}
			/*} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    	//return X;
	    //}
		//recuperer la liste des factures a patir du client
				@Transactional
				public List<Facture> recuplistfacture(String codeclt) throws HibernateException{
				

					String myQuery = "Select * from facture Where num_clt='" + codeclt + "'"; 
					
					
					List liste = getSessionFactory().getCurrentSession().createSQLQuery(myQuery).addEntity(Facture.class).list();

					return liste;
				}
				
	
	
			public SessionFactory getSessionFactory() {
			return sessionFactory;
		}

		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}
