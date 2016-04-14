package com.gestion.reporting.design;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managed.maison.Managedimpcontrat;
import com.gestion.model.Client;
import com.gestion.model.Contrat;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@Component

public class Imprimercontrat implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// Injection par spring
	
	@Autowired
	Managedimpcontrat managedimpcontrat;
	
	


	@Autowired
	private ObjectService objectService;
	
	



	private String idcontrat;
	private String critere;
	private List<Contrat> listecontrat;
	private List<Client> listeclient;
	


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
	private static Font smallTextGras1 = new Font(Font.FontFamily.COURIER, 9,
			Font.BOLD);

	private static Font TITRE3 = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);

	public void editer() throws IOException, Exception {
		
		editercontrat(idcontrat);
		managedimpcontrat.reset();
		
	
	}

	
	
	 
	public void editercontrat(String idcontrat)
			throws IOException, Exception {
		// Créer le dosier de stockage des fichier générés
		repectoire = new File("c:/Etats/EICSconstruction");
		repectoire.mkdirs();
		
	
		setIdcontrat(getManagedimpcontrat().getContratrech().getCodeContrat());
		
		
		//setIdord(getEnregistrerord().getOrdonance().getNumOrdo());
		/*ordo = new Ordonnance();
		setOrdo( (Ordonnance) getObjectService().getObjectById(getIdord(),"Ordonnance"));
		
		
		System.out.println("code ordornance code+++++++++++++++++++++++++++++++++++++++"  +getIdord());
	
		System.out.println("libree ordornance +++++++++++++++++++++++++++++++++++++++"  +getOrdo().getLibellleOrdo());
		
		System.out.println("libree consultatt +++++++++++++++++++++++++++++++++++++++"  +getOrdo().getConsultation().getLibelleCons());
		*/
		
		

		
		
		Document document = new Document(PageSize.A4);
		document.setMargins(20, 20, 20, 20);
		nomFichier = "Contrat " + managedimpcontrat.getContratrech().getCodeContrat()+ ".pdf";
		// step 2
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		PdfWriter.getInstance(document, new FileOutputStream(repectoire + "/"
				+ nomFichier));

		// step 3
		document.open();

		
			try {
		

				addContent(document);
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
		// Ajout de logo
		Image logo = Image.getInstance(new URL(RESOURCE));
		logo.scalePercent(100);
		logo.scalePercent(40f);
		document.add(logo);
		
		
		
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
		
		addEmptyLine(saut, 5);
		document.add(sautLigne(5));
		
		
		
		creerEmagement(document);

	}

	// Titre du document
	public void creerTitreDocument(Document document) throws DocumentException {

		Paragraph titreDocument = new Paragraph(new Chunk(
				"Contrat  N ° "+ getIdcontrat(), TITRE3));
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

				// 1er ligne
				
				cell1 = new PdfPCell(new Phrase("Nom et prenoms du client:",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				
			cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getClient().getNomClt()+" "+ managedimpcontrat.getContratrech().getClient().getPrenomClt(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				cell1 = new PdfPCell(new Phrase("CNI du client :",  normalTitle));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				
				
				cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getClient().getMatriculeClt(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell1);
				

				
				cell = new PdfPCell(new Phrase("Numero de telephone:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getClient().getNumtelClt(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				cell = new PdfPCell(new Phrase("Pays:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getClient().getPays().getLibellePays(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				cell = new PdfPCell(new Phrase("Commune:",  normalTitle));
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tabSous.addCell(cell);
				
				
				
				cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getClient().getCommune().getLibelleCommune(),smallText));
				//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);


				

				addEmptyLine(saut, 1);
				document.add(sautLigne(1));
				
//2er ligne

		
				
				
				document.add(tabSous);
			
}
	

private void crercontenu(Document document) throws DocumentException {
	
	
	
	Paragraph saut = new Paragraph();
	addEmptyLine(saut, 1);
	document.add(sautLigne(1));
		
				PdfPTable tabSous = new PdfPTable(4);
				tabSous.setWidths(new int[] { 30, 30,30, 30 });

				//tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
				PdfPCell cell1;
				PdfPCell cell;

				// 1er ligne
	
	
cell1 = new PdfPCell(new Phrase("Code du contrat :", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);




cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getCodeContrat(),smallText));
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);
		

cell1 = new PdfPCell(new Phrase("Projet Maison:", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getMaison().getDsgMais(),smallText));//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);
tabSous.addCell(cell1);

cell1 = new PdfPCell(new Phrase("Type Maison:", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getMaison().getTypeMaison().getLibelleTypmaison(),smallText));//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);
tabSous.addCell(cell1);

cell1 = new PdfPCell(new Phrase("Tarif Prototype Maison:", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getPrototypeMaison().getCoutTtcPrototype().toString(),smallText));//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);
tabSous.addCell(cell1);


cell1 = new PdfPCell(new Phrase("Localisation Maison:", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase(managedimpcontrat.getContratrech().getPrototypeMaison().getLocalisationPrototype().toString(),smallText));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);



tabSous.addCell(cell1);


cell1 = new PdfPCell(new Phrase("TARIF TOTAL PROJET :", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase(managedimpcontrat.getCoutTotal().toString(), smallTextGras1));//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);
tabSous.addCell(cell1);

/*cell1 = new PdfPCell(new Phrase("Apport initial:", normalTitle));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);

tabSous.addCell(cell1);



cell1=new PdfPCell(new Phrase("managedimpcontrat.getContratrech().getFacture().getApportInitial().toString()",smallText));
//cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setBorder(Rectangle.NO_BORDER);
cell1.setColspan(1);



tabSous.addCell(cell1);*/
document.add(tabSous);
}

	
	/*private void crercontenu(Document document) throws DocumentException {
		
		// Info 
		
				PdfPTable tabSous = new PdfPTable(3);
				tabSous.setWidths(new int[] { 20, 20, 50 });

				//tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
				PdfPCell cell1;
				PdfPCell cell;

				// 1er ligne
				cell1 = new PdfPCell(new Phrase("Medicament", normalTitle));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				tabSous.addCell(new Phrase("Quantité prescrite", normalTitle));
			
				cell1 = new PdfPCell(new Phrase("Posologie", normalTitle));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				tabSous.addCell(cell1);
				
				
				listeprecrire = getEnregistrerord().getListeprescrire();
				
				
				for (int i = 0; i < listeprecrire.size(); i++) {
				// 2em ligne
				
				
				
				cell1 = new PdfPCell(new Phrase(getListeprecrire().get(i).getMedicament().getDsgMed(), smallText));
						
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				cell = new PdfPCell(new Phrase(getListeprecrire().get(i).getQtePrs().toString(),smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				
				tabSous.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase(getListeprecrire().get(i).getPosologieOr(), smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBorder(Rectangle.NO_BORDER);
				
				tabSous.addCell(cell);
				
				
				//3iemlign
				
				
				cell1 = new PdfPCell(new Phrase(getListeprecrire().get(i).getMedicament().getDsgMed(), smallText));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				
				
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire2().getQtePrs().toString(),smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBorder(Rectangle.NO_BORDER);
				
				tabSous.addCell(cell);
				
				
			
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire2().getPosologieOr(), smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
			
				
				// 4em ligne
				
				
				cell1 = new PdfPCell(new Phrase(enregistrerord.getMedicament3().getDsgMed(), smallText));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				
				
				
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire3().getQtePrs().toString(),smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
				
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire3().getPosologieOr(), smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
				
				//5iemlign
				
				cell1 = new PdfPCell(new Phrase(enregistrerord.getMedicament4().getDsgMed(), smallText));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				

				
			
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire4().getQtePrs().toString(),smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
				
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire4().getPosologieOr(), smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
				
				cell1 = new PdfPCell(new Phrase(enregistrerord.getMedicament5().getDsgMed(), smallText));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setColspan(1);
				
				tabSous.addCell(cell1);
				


				
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire5().getQtePrs().toString(),smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
			
				cell = new PdfPCell(new Phrase(enregistrerord.getPrescrire5().getPosologieOr(), smallText));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				tabSous.addCell(cell);
				
				
				
				
				
				}
				
				
				document.add(tabSous);
				}
		*/

	
	
	
	
	
	//}

	private static void ajoutTitre(Document document) throws DocumentException{
		Paragraph titre = new Paragraph();
		Paragraph paragraph = new Paragraph("GESTION EICS CONSTRUCTION", TITRE3);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		titre.add(paragraph);
		titre.setSpacingAfter(20);
		document.add(titre);
	}
	
	
	public void AjouterNomEntreprise(Document document) throws DocumentException{
		Paragraph societe = new Paragraph("01 BP 1289 ABJ 01");
		societe.setAlignment(Element.ALIGN_LEFT);
		document.add(societe);
	}

	

	// Emargement
	public void creerEmagement(Document document) throws DocumentException {

		Paragraph dateJour = new Paragraph(new Chunk("Fait à Abidjan  , le " + sdf.format(new Date().getTime())));
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

		Chunk chunkComp = new Chunk("par :" +getManagedimpcontrat().getManagedConnexion().getUtilisateur().getNomUtilisateur() +" " +  getManagedimpcontrat().getManagedConnexion().getUtilisateur().getPrenomUtilisateur());
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






	
	public Managedimpcontrat getManagedimpcontrat() {
		return managedimpcontrat;
	}




	public void setManagedimpcontrat(Managedimpcontrat managedimpcontrat) {
		this.managedimpcontrat = managedimpcontrat;
	}




	


	
}
