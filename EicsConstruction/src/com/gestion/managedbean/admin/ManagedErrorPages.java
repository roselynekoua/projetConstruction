package com.gestion.managedbean.admin;

import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ExceptionQueuedEvent;
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
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


@Component 
@Scope("request")

public class ManagedErrorPages{

/*	@PostConstruct
	public void init(){
		
		
//		context.getExternalContext().getRequestMap().clear();
//		context.getExternalContext().getRequestMap().putAll(X);
		FacesContext context = FacesContext.getCurrentInstance();
		ExceptionHandler  ex = (ExceptionHandler) context.getExternalContext().getSessionMap().get("wrapped");
		String request_uri = (String) context.getExternalContext().getSessionMap().get("request_uri");
		
		Iterator unhandledExceptionQueuedEvents = ex.getUnhandledExceptionQueuedEvents().iterator();
		
		if (!unhandledExceptionQueuedEvents.hasNext())
			return;
		Throwable exception = ((ExceptionQueuedEvent) unhandledExceptionQueuedEvents
				.next()).getContext().getException();
		
		exception = findExceptionRootCause(context, exception);
		System.out.println("<g<g<g<g<g<g<g<g<g< String request_uri;"+request_uri);
		System.out.println("<g<g<g<g<g<g<g<g<g< exception.getMessage();"+exception.getMessage());
		showToPrev=true;
		showToReload=false;
		showToHome=true;
		
		com.sun.faces.context.RequestMap X = (RequestMap) context.getExternalContext().getSessionMap().get("oldRqMp");
		System.out.println("inside Managed Error page init"+X);
	}
	*/
	public ManagedErrorPages() {
		// TODO Auto-generated constructor stub

	}
	
	boolean reportBug=false;
	
	String fromURI;
	
	
	Logger logs=Logger.getLogger(ManagedErrorPages.class);
	
	
	
	public String toPrevious(){
		
		if(isReportBug())
			//
			;
		FacesContext context = FacesContext.getCurrentInstance();
		String request_uri = (String) context.getExternalContext().getSessionMap().get("request_uri");
		System.out.println("<g<g<g<g<g<g<g<g<g< String request_uri;"+request_uri);
		String A = request_uri.substring(7, request_uri.length()-4);
		System.out.println("<g<g<g<g<g<g<g<g<g< String request_uri to redirect;"+A);
		context.getExternalContext().getSessionMap().clear();
		System.out.println("<g<g<g<g<g<g<g<g<g< String request_uri to redirect;"+A);
		return A+"?faces-redirect=true";
	}
	
	
	public String test() {
		if(isReportBug()== true){	
	envoiMessage("roselyneddy.j3a@gmail.com", "roselyneddy@yahoo.fr", "Erreur à l'equipe de developpement", "Bonjour !Nous avons rencontrer une erreur!!!!!", "roselyneddy.j3a@gmail.com", "eddykoua87");
	 
	System.out.println("+++++++++++++++++++++++++++++++roselyneddy@yahoo.fr");
		}
		return "/index?faces-redirect=true";
	}
	
	
	public String toHome() {
		if(isReportBug()== true){
			
			
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	       
	       
			envoiMessage("roselyneddy.j3a@gmail.com", "roselyneddy@yahoo.fr", "Erreur à l'equipe de developpement", "Bonjour !Nous avons rencontrer une erreur!!!!!", "roselyneddy.j3a@gmail.com", "eddykoua87");
			 System.out.println("Sucessfully Sent mail to All Users");
	//envoiMessage("roselyneddy.j3a@gmail.com", "m.koua@lp2i.net", "Erreur à l'equipe de developpement", "Bonjour !Nous avons rencontrer une erreur!!!!!", "roselyneddy.j3a@gmail.com", "eddykoua87")
			;}
		FacesContext context = FacesContext.getCurrentInstance();
		String request_uri = (String) context.getExternalContext().getSessionMap().get("request_uri");
		System.out.println("<g<g<g<g<g<g<g<g<g< String request_uri;"+request_uri);
		return "/index?faces-redirect=true";
	}

	//dobsongame@gmail.com
	
	public boolean envoiMessage(String mailFrom, String mailAddress, String subject,
			 String contextAjoute, final String username, final String password) {//public boolean envoiMessage(String mailFrom, String mailAddress, String subject,String pdfAjoute, String contextAjoute) {
	
	// static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		String host = "localhost";
		
		try {
			Properties props = new Properties();

			
			
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			 props.put("mail.smtp.host", "smtp.gmail.com");
			//props.put("mail.smtp.host", "localhost");
		//	props.setProperty("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.starttls.enable", "true");

			props.setProperty("mail.from", mailFrom);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 props.put("mail.smtp.socketFactory.port", "587");
		       
		        props.put("mail.smtp.socketFactory.fallback", "true");
			 
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			Message message = new MimeMessage(session);
			InternetAddress recipient = new InternetAddress(mailAddress);
			message.setRecipient(Message.RecipientType.TO, recipient);

			// subjet
			message.setSubject(subject);

			// Partie 1: Le context
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(contextAjoute);

			// Partie 2: Le pdf joint

			MimeBodyPart mbp2 = new MimeBodyPart();
//			if (getPdfAjoute() != null)
//				mbp2.attachFile(getPdfAjoute());

			// On regroupe les deux dans le message
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
//			if (getPdfAjoute() != null)
//				mp.addBodyPart(mbp2);
			message.setContent(mp);

			Transport.send(message);
			return true;
//		} catch (IOException e) {
//			System.err.println("Impossible de lire le fichier joint");
//			e.printStackTrace();
//			return false;
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
	
	
	
	
	
	
	
	
	
	
	
		   public  void sendEmail() {
		      // Recipient's email ID needs to be mentioned.
		      String to = "rroselyneddy.j3a@gmail.com";

		      // Sender's email ID needs to be mentioned
		      String from = "roselyneddy.j3a@gmail.com";
		      final String username = "roselyneddy.j3a@gmail.com";//change accordingly
		      final String password = "eddykoua87";//change accordingly
		      final String smtp_connection = "TLS";
		      // Assuming you are sending email through relay.jangosmtp.net
		      String host = "smtp.gmail.com";

		      Properties props = new Properties();
		      props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.starttls.enable", "true");
		      props.put("mail.smtp.host", host);
		      props.put("mail.smtp.port", "25");

		      // Get the Session object.
		      Session session = Session.getInstance(props,
		         new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		               return new PasswordAuthentication(username, password);
			   }
		         });

		      try {
			   // Create a default MimeMessage object.
			   Message message = new MimeMessage(session);
			
			   // Set From: header field of the header.
			   message.setFrom(new InternetAddress(from));
			
			   // Set To: header field of the header.
			   message.setRecipients(Message.RecipientType.TO,
		               InternetAddress.parse(to));
			
			   // Set Subject: header field
			   message.setSubject("Testing Subject");
			
			   // Now set the actual message
			   message.setText("Hello, this is sample for to check send " +
				"email using JavaMailAPI ");

			   Transport tr = session.getTransport("smtp");
			    tr.connect(host, username, password);
			    message.saveChanges();
			  
			    // tr.send(message);
			    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
			  
			    tr.sendMessage(message,message.getAllRecipients());
			    tr.close();
			   // Send message
			  // Transport.send(message);

			   System.out.println("Sent message successfully....");

		      } catch (MessagingException e) {
		         throw new RuntimeException(e);
		      }
		   }
		
/*	private Throwable findExceptionRootCause(FacesContext context,
			Throwable exception) {
		return Exceptions.unwrap(exception);
	}	
	*/
	public String getFromURI() {
		return fromURI;
	}

	public void setFromURI(String fromURI) {
		this.fromURI = fromURI;
	}

	public boolean isReportBug() {
		return reportBug;
	}

	public void setReportBug(boolean reportBug) {
		this.reportBug = reportBug;
	}


	
	
	
}
