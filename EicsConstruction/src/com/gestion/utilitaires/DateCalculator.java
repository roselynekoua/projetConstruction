package com.gestion.utilitaires;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

public class DateCalculator  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static  Logger logger=Logger.getLogger(DateCalculator.class);
SessionFactory sessionFactory;
private long duree1heure = 1000*60*60;//1000 millisecondes * 60 secondes * 60 minutes = 1 heure
private long duree1jour = duree1heure*24; //1 heure * 24 = 1 jour
private int nombreJour;
private double nombreMois;
private int resulteComparDate;

//static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

/**
 * Methode de calcul de nombre de jours entre deux dates
 * 
 * @param date1
 * @param date2
 * @return entier(nombre de jours entre les deux dates)
 */
public long calculerDifference(Date date1, Date date2) {//By ALekerand
	long tpsMiliSeconde = Math.abs(date1.getTime() - date2.getTime()); //calcul difference entre les deux dates
	 try {
		
		setNombreJour((int) ((tpsMiliSeconde)/duree1jour));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("Erreur lors du calcul de la difference de deux dates",e);
	}
	return nombreJour ;
	}




public double calculerDifferenceMois(Date dateDebut, Date dateFin) {//By ALekerand
	nombreMois = 0;
	
	
	try {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 Date date1 = sdf.parse(dateFormat.format(dateDebut));
		 Date date2 = sdf.parse(dateFormat.format(dateFin));
		 
		GregorianCalendar gc1 = new GregorianCalendar();
	     gc1.setTime(date1);
	     
	     GregorianCalendar gc2 = new GregorianCalendar();
	     gc2.setTime(date2);
		
		
	     int mois = 0;
	     gc1.add(GregorianCalendar.MONTH, 1);
	     while(gc1.compareTo(gc2)<=0) {
	         mois++;
	         gc1.add(GregorianCalendar.MONTH, 1);
	     }
	     
	     System.out.println("format date echéance---------"+gc2.getTime()+ "mois= "+mois);	
	     
		nombreMois = mois;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("Erreur lors du calcul de la diffï¿½rence de deux dates",e);
	}
	return nombreMois ;
	}
/**
 * Méthode de comparaison de deux date
 * @param date1
 * @param date2
 * @return 
 */
public int comparerDate(Date date1, Date date2){//By ALekerand
	try {
		setResulteComparDate(date1.compareTo(date2));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("Erreur lors de la comparaison de deux dates",e);
	}
	
	return resulteComparDate;
}





//************************************Getters & Setters********************************************

public int getNombreJour() {
	return nombreJour;
}

public void setNombreJour(int nombreJour) {
	this.nombreJour = nombreJour;
}

	static public Calendar getCalendarWithDate(Date date){
		Calendar t = Calendar.getInstance();
		t.setTime(date);
		return t ;
	}


	public int getResulteComparDate() {
		return resulteComparDate;
	}


	public void setResulteComparDate(int resulteComparDate) {
		this.resulteComparDate = resulteComparDate;
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}

