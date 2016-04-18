package com.gestion.reporting.design;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managed.caisse.Managedversement;
import com.gestion.managed.maison.Managedclient;
import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Facture;
import com.gestion.model.Versement;
import com.gestion.objetService.ObjectService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@Component

public class Etatfacture implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// Injection par spring
	
	@Autowired
	Managedversement managedversement;
	
	
	@Autowired
	private ObjectService objectService;
	
	
	private String idcontrat;
	private String idfacture;
	private String critere;
	private List<Contrat> listecontrat;
	private List<Client> listeclient;
	private List<Versement> listeversement;
	private List<Facture> listefacture;


	private File repectoire;
	
	private String nomFichier;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	public static final String RESOURCE = "http://localhost:8080/EicsConstruction/resources/images/logoeics.png";
	
	//public static final String RESOURCE = "http://localhost:8080/GestionPharmacie/resources/images/logo_j3a.jpg";
	// Pour la mise en forme
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 28,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.GREEN);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);

	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	private static Font normalText = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.BOLD);
	private static Font normalText1 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL);
	private static Font normalText1Gras = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.BOLD);

	private static Font normalTitle = new Font(Font.FontFamily.TIMES_ROMAN, 9,
			Font.BOLD);

	private static Font smallText = new Font(Font.FontFamily.COURIER, 8,
			Font.NORMAL);

	private static Font smallTextGras = new Font(Font.FontFamily.COURIER, 8,
			Font.BOLD);
	private static Font smallTextGras1 = new Font(Font.FontFamily.COURIER, 8,
			Font.BOLD);

	private static Font TITRE3 = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);

	public void editer() throws IOException, Exception {
		
		editercontrat(idfacture);
		
		
		managedversement.setEtatAnnuler(false);
		managedversement.setEtatimprimer(true);
		managedversement.vider();
	}

	
	
	 
	public void editercontrat(String idcontrat)
			throws IOException, Exception {
		// Créer le dosier de stockage des fichier générés
		repectoire = new File("c:/Etats/EICSconstruction");
		repectoire.mkdirs();
		//getProcessuconsultationMB().getIdord(getProcessuconsultationMB().getCodeor());
		setIdfacture(getManagedversement().getFacture().getCodeFact());
		
		//setIdord(getEnregistrerord().getOrdonance().getNumOrdo());
		/*ordo = new Ordonnance();
		setOrdo( (Ordonnance) getObjectService().getObjectById(getIdord(),"Ordonnance"));
		
		
		System.out.println("code ordornance code+++++++++++++++++++++++++++++++++++++++"  +getIdord());
	
		System.out.println("libree ordornance +++++++++++++++++++++++++++++++++++++++"  +getOrdo().getLibellleOrdo());
		
		System.out.println("libree consultatt +++++++++++++++++++++++++++++++++++++++"  +getOrdo().getConsultation().getLibelleCons());
		*/
		
		

		
		
		Document document = new Document(PageSize.A4.rotate(),3,3,3,3);
		document.setMargins(20, 20, 20, 20);
		nomFichier = "Facture " + managedversement.getFacture().getCodeFact()+ ".pdf";
		// step 2
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
	
		//PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(repectoire + "/"
				//+ nomFichier));

		// step 3
		//document.open();

		
			try {
		

				addContent(document);
			

		        /*document.add(new Paragraph("Barcode 3 of 9 extended"));
		        Barcode39 code39ext = new Barcode39();
		        code39.setCodeType(code39.CODE128);
		        code39ext.setCode("iText in Action");
		        code39ext.setStartStopText(false);
		        code39ext.setExtended(true);
		        document.add(code39ext.createImageWithBarcode(cb, null, null));*/
		        
		        
				/*
				Barcode39 code39 = new Barcode39();
				 
				//code39.setCodeType(code39.CODE128); 
				code39.setCode("41-1200076041-001");
				//code39.setCodeType(41-1200076041-001);
				    Image imageEAN = code39.createImageWithBarcode(cb, null, null);
				    document.add(new Chunk(imageEAN, 0, 0));*/
				//document.close();
				System.out.println("rapport généré");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur1");
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erreur2");
			}
		

		document.close();
		 /* // setting some response headers response.setHeader("Expires", "0");
		  response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
//		  response.setHeader("Pragma", "public"); // setting the content type
		  response.setContentType("application/pdf"); // the contentlength
		  response.setContentLength(baos.size()); // write ByteArrayOutputStream to the ServletOutputStream 
		  OutputStream os = response.getOutputStream(); 
		  baos.writeTo(os);
		  os.flush();
		  os.close();
		  */
		  
		  
		  ouvrirFicher();
		  
		  }
	

	


	private void addContent(Document document) throws DocumentException,
			MalformedURLException, IOException {
		PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(repectoire + "/"
				+ nomFichier));
		document.open();

		// Ajout de logo
		Image logo = Image.getInstance(new URL(RESOURCE));
		logo.scalePercent(100);
		logo.scalePercent(40f);
		document.add(logo);
		/*PdfWriter.getInstance(document, new FileOutputStream(repectoire + "/"
				+ nomFichier));
		PdfWriter writer= PdfWriter.getInstance(document,new FileOutputStream("c:/Etats/LCconstruction/report.pdf" ));	
		document.open();*/
		
		/*Paragraph saut = new Paragraph();
		addEmptyLine(saut, 1);
		document.add(sautLigne(1));*/
		//Ajout du nom de l'entreprise
		AjouterNomEntreprise(document);
		
		// Titre du document
				ajoutTitre(document);
		
		// Entête du document
		creerTitreDocument(document);

		crertabl1(document);
		Paragraph saut = new Paragraph();
		addEmptyLine(saut, 1);
		document.add(sautLigne(1));
		
		crercontenu(document);
		
		addEmptyLine(saut, 6);
		document.add(sautLigne(6));
		

		PdfContentByte cb = writer.getDirectContent();
		//document.add(new Paragraph("Barcode 128of 9"));
        Barcode128 code128 = new Barcode128();
        code128.setCodeType(code128.CODE128);
       // code128.setCodeType(Calendar.DATE);
        code128.setCode(""+Calendar.getInstance().getTimeInMillis());
        code128.setSize(10);
        Image imageEAN =(code128.createImageWithBarcode(cb, null, null));
        document.add(new Chunk(imageEAN, 0, 0));
		
		creerEmagement(document);
		
	/*	
		PdfContentByte cb = writer.getDirectContent();
		 Barcode128 code128 = new Barcode128();
		    code128.setCodeType(code128.CODE128);
		    code128.setCodeType(Calendar.DATE);
		    Image imageEAN = code128.createImageWithBarcode(cb, null, null);
		    document.add(new Chunk(imageEAN, 0, 0));
		//document.close();
		System.out.println("rapport généré");
		*/
	}

	// Titre du document
	public void creerTitreDocument(Document document) throws DocumentException {

		Paragraph titreDocument = new Paragraph(new Chunk(
				"RECU DE PAIEMENT  N ° "+ getIdfacture(), TITRE3));
		titreDocument.setAlignment(Element.ALIGN_CENTER);
		titreDocument.setSpacingAfter(30);
		document.add(titreDocument);
		
		Paragraph saut = new Paragraph();
		addEmptyLine(saut, 1);
		document.add(sautLigne(1));

		PdfPTable tabSous = new PdfPTable(4);
		tabSous.setWidths(new int[] { 30, 35,25, 20 });

		//tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
		PdfPCell cell1;
		PdfPCell cell;

		
	}

	
private void crertabl1(Document document) throws DocumentException {
		
		// Info 
	

	Paragraph saut = new Paragraph();
	addEmptyLine(saut, 1);
	document.add(sautLigne(1));
		
				PdfPTable tabSous = new PdfPTable(4);
				tabSous.setWidths(new int[] { 35, 35,35, 35 });

				//tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
				PdfPCell cell1;
				PdfPCell cell;

				
				
				cell1 = new PdfPCell(new Phrase("N ° Reçu:",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				
			cell1=new PdfPCell(new Phrase(managedversement.getFacture().getCodeFact(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				cell1 = new PdfPCell(new Phrase("Nature reglement:",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				
			cell1=new PdfPCell(new Phrase(managedversement.getFacture().getLibelleFact(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				//
				cell1 = new PdfPCell(new Phrase("Numero client:",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				
			cell1=new PdfPCell(new Phrase(managedversement.getContrat().getClient().getNumClt(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				
				
				// 1er ligne
				
				cell1 = new PdfPCell(new Phrase("Nom et prenoms du client:",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				
			cell1=new PdfPCell(new Phrase(managedversement.getContrat().getClient().getNomClt()+" "+ managedversement.getContrat().getClient().getPrenomClt(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				cell1 = new PdfPCell(new Phrase("Numero du chèque :",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getVersement().getNchequeVers(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				

				
				cell = new PdfPCell(new Phrase("Mode de reglement:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getVersement().getTypeVers(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				cell = new PdfPCell(new Phrase("Date versement :",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				if(managedversement.getVersement().getDateVers()!=null){
				cell1=new PdfPCell(new Phrase(sdf.format(managedversement.getVersement().getDateVers()),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				}
				
				
				cell = new PdfPCell(new Phrase("Etat de reglement:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getVersement().getEtatVers(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				//desigation projet
				
				
			
//2er ligne

		
				
				
				document.add(tabSous);
			
}
	

private void crercontenu(Document document) throws DocumentException {
	
	
	
	Paragraph saut = new Paragraph();
	//addEmptyLine(saut, 1);
	//document.add(sautLigne(1));
		
				PdfPTable tabSous = new PdfPTable(6);
				tabSous.setWidths(new int[] { 35,35,35, 35,35, 35 });

				//tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
				PdfPCell cell1;
				PdfPCell cell;

				// 1er ligne
	
				
				cell = new PdfPCell(new Phrase("Libellé du Projet:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getContrat().getPrototypeMaison().getLibellePrototype(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				
				
				
				
				
				
				cell = new PdfPCell(new Phrase("Montant payé à ce jour :",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getMontantdejapaye().toString(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				
				
				
				cell = new PdfPCell(new Phrase("Montant tarif Prototype:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getContrat().getPrototypeMaison().getCoutTtcPrototype().toString(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);

				
				cell1 = new PdfPCell(new Phrase("Montant  Total contrat:", normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);

				tabSous.addCell(cell1);



				cell1=new PdfPCell(new Phrase(managedversement.getFacture().getMontantTtcFact().toString(), smallTextGras1));//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);

				cell = new PdfPCell(new Phrase("Montant restant:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedversement.getReste().toString(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);

				cell = new PdfPCell(new Phrase("MONTANT RECU:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				

				
				cell1=new PdfPCell(new Phrase(managedversement.getVersement().getMontantVers().toString(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);

				addEmptyLine(saut, 2);
				document.add(sautLigne(2));
				
document.add(tabSous);
}

	
	
	
	
	

	private static void ajoutTitre(Document document) throws DocumentException{
		Paragraph titre = new Paragraph();
		Paragraph paragraph = new Paragraph("GESTION EICS CONSTRUCTION", TITRE3);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		titre.add(paragraph);
		titre.setSpacingAfter(20);
		document.add(titre);
	}
	
	
	public void AjouterNomEntreprise(Document document) throws DocumentException{
		Paragraph societe = new Paragraph("06 BP 1927 ABIDJAN 06  ");
		societe.setAlignment(Element.ALIGN_LEFT);
		document.add(societe);
	}

	

	// Emargement
	public void creerEmagement(Document document) throws DocumentException {

		Paragraph dateJour = new Paragraph(new Chunk("Imprimé à Abidjan  , le " + sdf.format(new Date().getTime())));
		dateJour.setIndentationLeft(200);

		PdfPTable tabEmerg = new PdfPTable(3);
		tabEmerg.setWidthPercentage(100);
		PdfPCell cell;

		Chunk chunkSous = new Chunk("Le client");
		chunkSous.setUnderline(0.1f, -2f);
		cell = new PdfPCell(new Phrase(chunkSous));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		tabEmerg.addCell(cell);

		cell = new PdfPCell(new Phrase(""));
		cell.setBorder(Rectangle.NO_BORDER);
		tabEmerg.addCell(cell);

		Chunk chunkComp = new Chunk("par :" +getManagedversement().getManagedConnexion().getUtilisateur().getNomUtilisateur()+ "  " +getManagedversement().getManagedConnexion().getUtilisateur().getPrenomUtilisateur());
		chunkComp.setUnderline(0.1f, -2);
		cell = new PdfPCell(new Phrase(chunkComp));
		cell.setBorder(Rectangle.NO_BORDER);
		tabEmerg.addCell(cell);

		tabEmerg.setSpacingBefore(15);
		document.add(dateJour);
		document.add(tabEmerg);
	}

	public static void addEmptyLine(Paragraph paragraph, int nbrLigne) {
		for (int i = 0; i < nbrLigne; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public Paragraph sautLigne(int number) {
		Paragraph saut = new Paragraph();
		for (int i = 0; i < number; i++) {
			saut.add(new Paragraph(" "));
		}
		return saut;
	}

	public void ouvrirFicher() {
		System.out.println("début ouverture du fichier pdf");// clean After
		try {
			
			if ((new File("C:\\Etats\\EICSconstruction\\" + nomFichier + ""))
					.exists()) {

				Process p = Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler "
								+ "C:\\Etats\\EICSconstruction\\" + nomFichier
								+ "");
				p.waitFor();

			} else {

				System.out.println("File is not exists");

			}

			System.out.println("Done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Fin ouverture du fichier pdf");

	}

	public void printFile() {
		if (Desktop.isDesktopSupported()) {
			if (Desktop.getDesktop().isSupported(java.awt.Desktop.Action.PRINT)) {
				try {
					java.awt.Desktop.getDesktop().print(
							new File(repectoire + nomFichier));
				} catch (IOException ex) {
					// Traitement de l'exception
				}
			} else {
				// La fonction n'est pas supportée par votre système
				// d'exploitation
			}
		} else {
			// Desktop pas supportée par votre système d'exploitation
		}
	}

	public void openFile() {

		if (Desktop.isDesktopSupported()) {
			if (Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
				try {
					java.awt.Desktop.getDesktop().open(
							new File(repectoire + "/" + nomFichier));
				} catch (IOException ex) {
					// Traitement de l'exception
				}
			} else {
				// La fonction n'est pas supportée par votre système
				// d'exploitation
			}
		} else {
			// Desktop pas supportée par votre système d'exploitation
		}
	}

	
	
	
	

	

	


	public ObjectService getObjectService() {
		return objectService;
	}



	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}






	public String getCritere() {
		return critere;
	}



	




	public void setCritere(String critere) {
		this.critere = critere;
	}






	public List<Contrat> getListecontrat() {
		return listecontrat;
	}




	public void setListecontrat(List<Contrat> listecontrat) {
		this.listecontrat = listecontrat;
	}




	public List<Client> getListeclient() {
		return listeclient;
	}




	public void setListeclient(List<Client> listeclient) {
		this.listeclient = listeclient;
	}




	public String getIdcontrat() {
		return idcontrat;
	}




	public void setIdcontrat(String idcontrat) {
		this.idcontrat = idcontrat;
	}






	public String getIdfacture() {
		return idfacture;
	}




	public void setIdfacture(String idfacture) {
		this.idfacture = idfacture;
	}




	public List<Facture> getListefacture() {
		return listefacture;
	}




	public void setListefacture(List<Facture> listefacture) {
		this.listefacture = listefacture;
	}




	public List<Versement> getListeversement() {
		return listeversement;
	}




	public void setListeversement(List<Versement> listeversement) {
		this.listeversement = listeversement;
	}




	public Managedversement getManagedversement() {
		return managedversement;
	}




	public void setManagedversement(Managedversement managedversement) {
		this.managedversement = managedversement;
	}




	


	


	
}
