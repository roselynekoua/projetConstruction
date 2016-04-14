package com.gestion.reporting.design;


import java.io.IOException;

public class RechercheFichierPDF {

	public void voirRapport(String chemainPDF){
		try {
			Runtime.getRuntime().exec("cmd /C start "+chemainPDF);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
