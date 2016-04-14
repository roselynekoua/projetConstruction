/*package com.gestion.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;





@Component
public class MailClass {

	*//**
	 * @param args
	 * 
	 * herve Bah 
	 * 
	 * send Mail
	 *//*
	private String mailFrom;
	private String mailAddress;
	//private String pdfAjoute;
	private String contextAjoute;
	private String subject;
	
	
	private String username = "";
	private String password = "";
	private String nomsociate="";
	private String adressesociete="";
	private TParametres parametres = new TParametres();
	private List<TMails> mailList ;
	@Autowired
	ObjectService objectService;
	
	private UIComponent buttonmessage;
	
	@PostConstruct
	public void postConstru() {
		parametres = (TParametres)objectService.getObjectById(1, "TParametres");
		//Recupération des Mails
		mailList = new ArrayList<TMails>();
		for(TMails m: parametres.getTMailses()){
			if(m.getMailStatut().equalsIgnoreCase("actif")){
			mailList.add(m)	;
			}
		}
	
	}
	
			@Async
	public void envoieUnMail(String nature,String codeImputation, String objet, String utilisateur, String pdfAjoute){
		//for(TMails m: mailList){//

			adressesociete= "Ministère de l'Intégration Africaine et des Ivoirens de l'extérieur";
			

			subject="Service Conférence Voyage: Création d'une  Mission "+nature;
			contextAjoute="Bonjour Monsieur le Directeur de Cabinet\n" +"\n" +
					
					"Nous vous informons de la création d'une Mission "+nature+" ! \n " +
					"\n" +"\n"+
					"Mission : "+codeImputation+"."+"\n"+
					"Objet : "+objet+"."+"\n"+
					"Le service Conférence Voyage: "+utilisateur+"\n"+
					
					"Aderesse: "+adressesociete+"\n"+ utilisateur ;
			envoiMessage(subject, pdfAjoute, contextAjoute);
			
		//}
		//envoiMessage(mailFrom,mailAddress,subject,pdfAjoute, contextAjoute);
	}
	

	
	public boolean envoiMessage(String subject, String pdfAjoute, String contextAjoute) {
		try {
			Properties props = new Properties();

			props.put("mail.smtp.auth", parametres.getMailSmtpAuth());
			props.put("mail.smtp.starttls.enable", parametres.getMailSmtpStarttlsEnable());
			props.put("mail.smtp.host", parametres.getMailSmtpHost());
			props.put("mail.smtp.port", ""+parametres.getMailSmtpPort()+"");
			props.put("mail.transport.protocol", parametres.getMailTransportProtocol());
			props.setProperty("mail.from",parametres.getMailFrom());
			
			
			
			
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(parametres.getMailLogin(), parametres.getMailPassword());
				}
			});
			Message message = new MimeMessage(session);
			for(TMails m: mailList){
				if(m.getMailRecipiant().equalsIgnoreCase("TO")){
					InternetAddress recipient = new InternetAddress(m.getMailAdresse());
					message.addRecipient(Message.RecipientType.TO, recipient);
					
				}
				
				if(m.getMailRecipiant().equalsIgnoreCase("CC")){
					InternetAddress recipient = new InternetAddress(m.getMailAdresse());
					message.addRecipient(Message.RecipientType.CC, recipient);
					
				}
				
				
			}
			


			// subjet
			message.setSubject(subject);

			// Partie 1: Le context
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(contextAjoute);

			// Partie 2: Le pdf joint

			MimeBodyPart mbp2 = new MimeBodyPart();
			if (pdfAjoute != null)
				mbp2.attachFile(pdfAjoute);

			// On regroupe les deux dans le message
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			if (pdfAjoute != null)
				mp.addBodyPart(mbp2);
			message.setContent(mp);

			Transport.send(message);
			return true;
		} catch (IOException e) {
			System.err.println("Impossible de lire le fichier joint");
			e.printStackTrace();
			return false;
		} catch (NoSuchProviderException e) {
			System.err.println("Pas de transport disponible pour ce protocole");
			System.err.println(e);
			return false;
		} catch (AddressException e) {
			System.err.println("Adresse invalide");
			System.err.println(e);
			return false;
		} catch (MessagingException e) {
			System.err.println("Erreur dans le message");
			System.err.println(e);
			return false;
		}
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


	public UIComponent getButtonmessage() {
		return buttonmessage;
	}
	public void setButtonmessage(UIComponent buttonmessage) {
		this.buttonmessage = buttonmessage;
	}


	public TParametres getParametres() {
		return parametres;
	}


	public void setParametres(TParametres parametres) {
		this.parametres = parametres;
	}


	public List<TMails> getMailList() {
		return mailList;
	}


	public void setMailList(List<TMails> mailList) {
		this.mailList = mailList;
	}


}
*/