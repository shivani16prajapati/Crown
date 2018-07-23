package com.unicef.portlet.idea;

import java.util.Date;
import java.util.Properties;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public class SendEmail {
	
	private static Log LOG = LogFactoryUtil.getLog(SendEmail.class.getName());
	
    
    public static void SendMail(String to) {
    	
    	Configuration configuration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
		
    	final String username = configuration.get("FROM_EMAIL");
		final String password = configuration.get("PASSWORD");
		final String subject = configuration.get("SUBJECT");	
		final String msg = configuration.get("MESSAGE");
		final String from = configuration.get("FROM");
		final String host = configuration.get("HOST");
		final String port = configuration.get("PORT");
	
	      
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", port);
	      props.put("mail.smtp.starttls.enable", "true") ;
	      props.put("mail.smtp.auth", "true") ;
	      
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	            }
		});


	      
	      try {
	            Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(from));
		   message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(to));
		   message.setSubject(subject);
		   message.setContent(msg,"text/html");
		   Transport.send(message);
		   LOG.info("Email Sent message successfully");

	      } catch (MessagingException e) {
	       LOG.info(e.toString());
	       LOG.info("Email Address not currect");
		   e.printStackTrace();
		   throw new RuntimeException(e);
	      }
	   }
}
