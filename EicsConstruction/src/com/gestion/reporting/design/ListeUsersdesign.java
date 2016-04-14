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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import com.gestion.managedbean.admin.ManagedUtilisateur;
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
public class ListeUsersdesign implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// Injection par spring

	@Autowired
	ManagedUtilisateur managedUtilisateur;
	@Autowired
	private ObjectService objectService;

	private static int compteur;

	private String idmed;

	private File repectoire;

	private Utilisateur utilisateur = new Utilisateur();

	private List<Utilisateur> listeutilisateur = new ArrayList<>();

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
		editerstock(managedUtilisateur, (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest(),
				(HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse());
		// openFile();
		// printFile();

		// ouvrirFicher();

	}

	public void editerstock(ManagedUtilisateur managedUtilisateur,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, Exception {
		// Créer le dosier de stockage des fichier générés
		repectoire = new File("c:/Etats/EICSconstruction/Listedesutilisateurs");
		repectoire.mkdirs();

		Document document = new Document(PageSize.A4.rotate(),3,3,3,3);
		//document.setMargins(5, 5, 5, 5);
		nomFichier = "Listeuser_" + java.util.Calendar.getInstance().getTimeInMillis() + ".pdf";
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
				"LISTE DES UTILISATEURS", TITRE3));
		titreDocument.setAlignment(Element.ALIGN_CENTER);
		titreDocument.setSpacingAfter(30);
		document.add(titreDocument);
	}

	private void crercontenu(Document document) throws DocumentException {
		// Info

		PdfPTable tabSous = new PdfPTable(10);
		tabSous.setWidths(new int[] { 28, 35, 30, 30, 30, 35, 35, 35, 42, 35 });

		// tabSous.getDefaultCell().setBorder(Cell.NO_BORDER);
		PdfPCell cell1;
		PdfPCell cell;
		PdfPCell cell2;
		PdfPCell cell3;

		// 1er ligne
		tabSous.addCell(new Phrase("Code ", normalTitle));
		tabSous.addCell(new Phrase("Nom ", normalTitle));

		tabSous.addCell(new Phrase("Prenom ", normalTitle));

		tabSous.addCell(new Phrase("Sexe ", normalTitle));

		tabSous.addCell(new Phrase("Matricule ", normalTitle));
		tabSous.addCell(new Phrase("Nationalité ", normalTitle));

		// tabSous.addCell(new Phrase("Fonction Utilisateur", normalTitle));

		tabSous.addCell(new Phrase("Telephone ", normalTitle));

		tabSous.addCell(new Phrase("Date creation ", normalTitle));
		// tabSous.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabSous.addCell(new Phrase("Mail ", normalTitle));
		tabSous.addCell(new Phrase("Login ", normalTitle));

		listeutilisateur = getManagedUtilisateur().getListeUser();

		// 2em ligne

		for (int i = 0; i < listeutilisateur.size(); i++) {
			tabSous.addCell(new Phrase(listeutilisateur.get(i).getCodeUtilisateur(), smallText));
			tabSous.addCell(new Phrase(listeutilisateur.get(i).getNomUtilisateur(), smallText));

			tabSous.addCell(new Phrase(listeutilisateur.get(i).getPrenomUtilisateur(), smallText));

			tabSous.addCell(new Phrase(listeutilisateur.get(i).getSexe().getLibelleSexe(), smallText));
			tabSous.addCell(new Phrase(listeutilisateur.get(i).getMatricule(),smallText));

			tabSous.addCell(new Phrase(listeutilisateur.get(i).getNationalite().getLibelleNat(), smallText));
			//tabSous.addCell(new Phrase(listeutilisateur.get(i).getFonction().getLibelleFonction(),smallText));
			tabSous.addCell(new Phrase(listeutilisateur.get(i).getNumtelUt(),
					smallText));
			tabSous.addCell(new Phrase(sdf.format(listeutilisateur.get(i)
					.getDateCreationUtilisateur()), smallText));
			tabSous.addCell(new Phrase(listeutilisateur.get(i)
					.getMailUtilisateur(), smallText));
			tabSous.addCell(new Phrase(listeutilisateur.get(i)
					.getLoginUtilisateur(), smallText));

		}
		document.add(tabSous);

	}

	private static void ajoutTitre(Document document) throws DocumentException {
		Paragraph titre = new Paragraph();
		Paragraph paragraph = new Paragraph("GESTION EICS CONSTRUCTION",
				TITRE3);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		titre.add(paragraph);
		titre.setSpacingAfter(20);
		document.add(titre);
	}

	public void AjouterNomEntreprise(Document document)
			throws DocumentException {
		Paragraph societe = new Paragraph("06 BP 1927 ABIDJAN 06");
		societe.setAlignment(Element.ALIGN_LEFT);
		document.add(societe);
	}

	// Emargement
	public void creerEmagement(Document document) throws DocumentException {

		Paragraph dateJour = new Paragraph(new Chunk("Fait à Abidjan , le "
				+ sdf.format(new Date().getTime())));
		dateJour.setIndentationLeft(200);

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
			
			if ((new File("C:\\Etats\\EICSconstruction\\Listedesutilisateurs\\"
					+ nomFichier + "")).exists()) {

				Process p = Runtime
						.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler "
								+ "C:\\Etats\\EICSconstruction\\Listedesutilisateurs\\"
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

	public String getIdmed() {
		return idmed;
	}

	public ManagedUtilisateur getManagedUtilisateur() {
		return managedUtilisateur;
	}

	public void setManagedUtilisateur(ManagedUtilisateur managedUtilisateur) {
		this.managedUtilisateur = managedUtilisateur;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setIdmed(String idmed) {
		this.idmed = idmed;
	}

	public static int getCompteur() {
		return compteur;
	}

	public static void setCompteur(int compteur) {
		ListeUsersdesign.compteur = compteur;
	}

	public List<Utilisateur> getListeutilisateur() {
		return listeutilisateur;
	}

	public void setListeutilisateur(List<Utilisateur> listeutilisateur) {
		this.listeutilisateur = listeutilisateur;
	}

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

}
