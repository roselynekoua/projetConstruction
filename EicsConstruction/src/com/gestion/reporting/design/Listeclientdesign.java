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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion.managed.maison.Managedclientliste;
import com.gestion.model.Client;
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
public class Listeclientdesign implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// Injection par spring

	@Autowired
	Managedclientliste  managedclient;
	@Autowired
	private ObjectService objectService;

	private static int compteur;

	private String idclient;

	private File repectoire;

	private Client client  = new Client();

	private List<Client> listeclient = new ArrayList<>();

	private String nomFichier;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	
	public static final String RESOURCE = "http://localhost:8080/EicsConstruction/resources/images/logoeics.png";
	public static final String RESOURCE2 = "c:/Dossierphoto/Clients/";
	public static final String RESOURCEvide = "http://localhost:8080/EicsConstruction/resources/images/user.jpg";
	/*if ((new File("C:\\Etats\\EICSconstruction\\Listeclients\\"
			+ nomFichier + "")).exists()) {*/
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
		editerclt(managedclient, (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest(),
				(HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse());
		// openFile();
		// printFile();

		// ouvrirFicher();

	}

	public void editerclt(Managedclientliste managedclient,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, Exception {
		// Créer le dosier de stockage des fichier générés
		repectoire = new File("c:/Etats/EICSconstruction/Listeclients");
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
				"LISTE DES CLIENTS", TITRE3));
		titreDocument.setAlignment(Element.ALIGN_CENTER);
		titreDocument.setSpacingAfter(30);
		document.add(titreDocument);
	}

	private void crercontenu(Document document) throws DocumentException, MalformedURLException, IOException {
		// Info
		
		PdfPTable tabSous = new PdfPTable(10);
		tabSous.setWidths(new int[] { 28, 35, 30, 30, 30, 35, 35, 35, 42,42 });

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

		tabSous.addCell(new Phrase("Cni ", normalTitle));
		tabSous.addCell(new Phrase("Nationalité ", normalTitle));


		tabSous.addCell(new Phrase("Telephone ", normalTitle));

		tabSous.addCell(new Phrase("Date creation ", normalTitle));
	
		tabSous.addCell(new Phrase("Mail ", normalTitle));
		tabSous.addCell(new Phrase("photo", normalTitle));
	
listeclient = getManagedclient().getListeclient();
		

		// 2em ligne

		for (int i = 0; i < listeclient.size(); i++) {
			tabSous.addCell(new Phrase(listeclient.get(i).getNumClt(), smallText));
			tabSous.addCell(new Phrase(listeclient.get(i).getNomClt(), smallText));

			tabSous.addCell(new Phrase(listeclient.get(i).getPrenomClt(), smallText));

			tabSous.addCell(new Phrase(listeclient.get(i).getSexe().getLibelleSexe(), smallText));
			tabSous.addCell(new Phrase(listeclient.get(i).getMatriculeClt(),smallText));

			tabSous.addCell(new Phrase(listeclient.get(i).getNationalite().getLibelleNat(), smallText));
			
			tabSous.addCell(new Phrase(listeclient.get(i).getNumtelClt(),
					smallText));
			tabSous.addCell(new Phrase(sdf.format(listeclient.get(i)
					.getDatecreaClt()), smallText));
			tabSous.addCell(new Phrase(listeclient.get(i).getEmailClt(), smallText));
			
			try {
				
			if(getListeclient().get(i).getPhotoClt()!=null){
			//System.out.println("***************************image nonnull");
			Image logo2 = Image.getInstance(RESOURCE2 +getListeclient().get(i).getPhotoClt());
			
			logo2.scalePercent(100f);
			tabSous.addCell(logo2);
			}
		
	
			
			
			} catch (FileNotFoundException e) {
				
					//System.out.println("***************************image  null");
					 Image logo2 = Image.getInstance(new URL(RESOURCEvide));
					tabSous.addCell(logo2);
			}
			/*if(getListeclient().get(i).getPhotoClt()!=null){
				Image logo2 = Image.getInstance(RESOURCE2 +getListeclient().get(i).getPhotoClt());
				
				logo2.scalePercent(100f);
				tabSous.addCell(logo2);
				}else{
					Image logo2 = Image.getInstance("");
					tabSous.addCell(logo2);
				}*/
			
			//tabSous.addCell(new URL(RESOURCE2+listeclient.get(i).getPhotoClt());
			//tabSous.addCell(new Phrase(RESOURCE2+listeclient.get(i).getPhotoClt(), smallText));
			/* Image logo = Image.getInstance(new String(RESOURCE2));
			 
			 
			  Image logo = Image.getInstance(new URL(RESOURCE));
		 logo.scalePercent(100f);
		 document.add(logo);
			 document.add(logo);*/

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
			
			if ((new File("C:\\Etats\\EICSconstruction\\Listeclients\\"
					+ nomFichier + "")).exists()) {

				Process p = Runtime
						.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler "
								+ "C:\\Etats\\EICSconstruction\\Listeclients\\"
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

	
	public Managedclientliste getManagedclient() {
		return managedclient;
	}

	public void setManagedclient(Managedclientliste managedclient) {
		this.managedclient = managedclient;
	}

	public String getIdclient() {
		return idclient;
	}

	public void setIdclient(String idclient) {
		this.idclient = idclient;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Client> getListeclient() {
		return listeclient;
	}

	public void setListeclient(List<Client> listeclient) {
		this.listeclient = listeclient;
	}

	public static void setCompteur(int compteur) {
		Listeclientdesign.compteur = compteur;
	}

	

	public ObjectService getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

}
