package com.belatrix.legal.mailfinder.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadMailConfig;

public class SendEMailService {

	private static final String MAIL_USERNAME=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_USERNAME.getNameProperty());
	private static final String MAIL_PASSWORD=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());
	private static final String MAIL_SMTP_AUTH=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_SMTP_AUTH.getNameProperty());
	private static final String MAIL_START_TLS=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_START_TLS.getNameProperty());
	private static final String MAIL_SMTP_HOST=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_SMTP_HOST.getNameProperty());
	private static final String MAIL_SMTP_GMAIL=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_SMTP_GMAIL.getNameProperty());
	private static final String MAIL_SMTP_PORT=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_SMTP_PORT.getNameProperty());
	private static final String MAIL_SMTP_PORT_NUMBER=LoadMailConfig.getInstance().getProperty(EPropertyMail.MAIL_SMTP_PORT_NUMBER.getNameProperty());
	private static final String TRUE = "true";
	
	private final static Logger LOGGER = Logger.getLogger(SendEMailService.class);
	
	public static void sendEmail(String text, String recipient, String addressee, String subject) {

		final String username = MAIL_USERNAME;
		final String password = MAIL_PASSWORD;

		Properties props = new Properties();
		props.put(MAIL_SMTP_AUTH, TRUE);
		props.put(MAIL_START_TLS, TRUE);
		props.put(MAIL_SMTP_HOST, MAIL_SMTP_GMAIL);
		props.put(MAIL_SMTP_PORT, MAIL_SMTP_PORT_NUMBER);

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(addressee));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

			LOGGER.info("Done");

		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
