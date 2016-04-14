package com.gestion.reporting.design;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managed.maison.Managedclient;
import com.gestion.managed.maison.Managedclientliste;
import com.gestion.managedbean.admin.ManagedUtilisateur;
import com.gestion.model.Client;
import com.gestion.model.Contrat;
import com.gestion.model.Utilisateur;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class Listecontradesign implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// Injection par spring

	@Autowired
	Managedclientliste  managedcontrat;
	@Autowired
	private ObjectService objectService;

	private static int compteur;

	private String idcontrat;

	private File repectoire;


	

	private List<Contrat> listecontrat = new ArrayList<>();

	private String nomFichier;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	// String httpsURL =
	// "https://www.certification.tn/cgi-bin/pub/crl/cacrl.crl";
	// URL myurl = new URL(httpsURL);
	public static final String RESOURCE = "http://localhost:8080/EicsConstruction/resources/images/logoeics.png";

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

	private static Font TITRE3 = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);

	public void editer() throws IOException, Exception {
		editerclt(managedcontrat, (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest(),
				(HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse());
		// openFile();
		// printFile();

		// ouvrirFicher();

	}

	public void editerclt(Managedclientliste managedcontrat,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, Exception {
		// Créer le dosier de stockage des fichier générés
		repectoire = new File("c:/Etats/EICSconstruction/ListeContrats");
		repectoire.mkdirs();

		Document document = new Document(PageSize.A4.rotate(),3,3,3,3);
		//document.setMargins(5, 5, 5, 5);
		nomFichier = "Listecontr_" + java.util.Calendar.getInstance().getTimeInMillis() + ".pdf";
		// step 2
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		PdfWriter.getInstance(document, new FileOutputStream(repectoire + "/"
				+ nomFichier));

		// step 3y
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
		// setting some response headers response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		// response.setHeader("Pragma", "public"); // setting the content type
		response.setContentType("application/pdf"); // the contentlength
		response.setContentLength(baos.size()); // write ByteArrayOutputStream
												// to the ServletOutputStream
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		ouvrirFicher();

		// printFile();
		compteur = compteur + 1;
	}

	private void addContent(Document document) throws DocumentException,
			MalformedURLException, IOException {
		// Ajout de logo
		 Image logo = Image.getInstance(new URL(RESOURCE));
		 logo.scalePercent(100f);
		 document.add(logo);
		AjouterNomEntreprise(document);

		// Titre du document
		ajoutTitre(document);
		Paragraph saut = new Paragraph();

		// entête du document
		creerTitreDocument(document);
		addEmptyLine(saut, 1);
		document.add(sautLigne(1));

		addEmptyLine(saut, 1);
		document.add(sautLigne(1));
		crercontenu(document);

		addEmptyLine(saut, 2);
		document.add(sautLigne(2));
		// Emagement
		creerEmagement(document);

	}

	// Titre du document
	public void creerTitreDocument(Document document) throws DocumentException {

		Paragraph titreDocument = new Paragraph(new Chunk(
				"LISTE DES CONTRATS", TITRE3));
		titreDocument.setAlignment(Element.ALIGN_CENTER);
		titreDocument.setSpacingAfter(30);
		document.add(titreDocument);
	}

	private void crercontenu(Document document) throws DocumentException {
		// Info

		PdfPTable tabSous = new PdfPTable(9);
		tabSous.setWidths(new int[] { 28, 35, 40, 40, 30, 30, 30, 30, 30 });

		// tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
		PdfPCell cell1;
		PdfPCell cell;
		PdfPCell cell2;
		PdfPCell cell3;

		// 1er ligne
		tabSous.addCell(new Phrase("Code contrat", normalTitle));
		tabSous.addCell(new Phrase("Nom  client", normalTitle));

		tabSous.addCell(new Phrase("Prenom client ", normalTitle));

		tabSous.addCell(new Phrase("Profession  client", normalTitle));

		tabSous.addCell(new Phrase("Projet ", normalTitle));
		tabSous.addCell(new Phrase("Type projet ", normalTitle));


		tabSous.addCell(new Phrase("Tarif maison", normalTitle));

		tabSous.addCell(new Phrase("Cout total maison ", normalTitle));
	
		tabSous.addCell(new Phrase("Etat contrat ", normalTitle));
		
		
	
listecontrat = getManagedcontrat().getListecontrat();
		

		// 2em ligne

		for (int i = 0; i < listecontrat.size(); i++) {
			tabSous.addCell(new Phrase(listecontrat.get(i).getCodeContrat(), smallText));
			tabSous.addCell(new Phrase(listecontrat.get(i).getClient().getNomClt(), smallText));

			tabSous.addCell(new Phrase(listecontrat.get(i).getClient().getPrenomClt(), smallText));

			tabSous.addCell(new Phrase(listecontrat.get(i).getClient().getProfession().getLibelleProfession(), smallText));
			tabSous.addCell(new Phrase(listecontrat.get(i).getMaison().getDsgMais(),
					smallText));
			tabSous.addCell(new Phrase(listecontrat.get(i).getMaison().getTypeMaison().getLibelleTypmaison(), smallText));
			tabSous.addCell(new Phrase(listecontrat.get(i).getPrototypeMaison().getCoutTtcPrototype().toString(),smallText));

			
			//a terminer
			
			tabSous.addCell(new Phrase(listecontrat.get(i).getPrototypeMaison().getCoutTtcPrototype().toString(), smallText));
			tabSous.addCell(new Phrase(listecontrat.get(i)
					.getEtatContrat(), smallText));
			
			/*if(listecontrat.get(i).getDateDebutContrat()!=null){
			tabSous.addCell(new Phrase(sdf.format(listecontrat.get(i).getDateDebutContrat()),smallText));}
			
			if(listecontrat.get(i).getDateFinContrat()!=null){}
			tabSous.addCell(new Phrase(sdf.format(listecontrat.get(i).getDateFinContrat()),smallText));
*/
		}
		document.add(tabSous);

	}

	private static void ajoutTitre(Document document) throws DocumentException {
		Paragraph titre = new Paragraph();
		Paragraph paragraph = new Paragraph("GESTION DE EICS CONSTRUCTION",
				TITRE3);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		titre.add(paragraph);
		titre.setSpacingAfter(20);
		document.add(titre);
	}

	public void AjouterNomEntreprise(Document document)
			throws DocumentException {
		Paragraph societe = new Paragraph("06 BP 1927 ABIDJAN 06 ");
		societe.setAlignment(Element.ALIGN_LEFT);
		document.add(societe);
	}

	// Emargement
	public void creerEmagement(Document document) throws DocumentException {

		Paragraph dateJour = new Paragraph(new Chunk("Fait à Abidjan , le "
				+ sdf.format(new Date().getTime())));
		dateJour.setIndentationLeft(250);

		PdfPTable tabEmerg = new PdfPTable(3);
		tabEmerg.setWidthPercentage(100);
		PdfPCell cell;

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
			
			if ((new File("C:\\Etats\\EICSconstruction\\ListeContrats\\"
					+ nomFichier + "")).exists()) {

				Process p = Runtime
						.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler "
								+ "C:\\Etats\\EICSconstruction\\ListeContrats\\"
								+ nomFichier + "");
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
					// Traitement de l'exceptio
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
	

	/*
	 * public void openFile(String nomFichier) {
	 * 
	 * if (Desktop.isDesktopSupported()) { if
	 * (Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) { try {
	 * java.awt.Desktop.getDesktop().open( new File(repectoire + "/" +
	 * nomFichier)); } catch (IOException ex) { // Traitement de l'exception } }
	 * else { // La fonction n'est pas supportée par votre système //
	 * d'exploitation } } else { // Desktop pas supportée par votre système
	 * d'exploitation } }
	 */

	

	

	public static int getCompteur() {
		return compteur;
	}

	
	

	public static void setCompteur(int compteur) {
		Listecontradesign.compteur = compteur;
	}

	

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

	public Managedclientliste getManagedcontrat() {
		return managedcontrat;
	}

	public void setManagedcontrat(Managedclientliste managedcontrat) {
		this.managedcontrat = managedcontrat;
	}

	
	public String getIdcontrat() {
		return idcontrat;
	}

	public void setIdcontrat(String idcontrat) {
		this.idcontrat = idcontrat;
	}

	public List<Contrat> getListecontrat() {
		return listecontrat;
	}

	public void setListecontrat(List<Contrat> listecontrat) {
		this.listecontrat = listecontrat;
	}
}
