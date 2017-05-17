package com.belatrix.legal.mailfinder.services;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadConfig;

public class EmailService {
	
	private static final String MAIL_IMAP=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_IMAP.getNameProperty());
	private static final String MAIL_RECIPIENT=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_RECIPIENT.getNameProperty());
	private static final String MAIL_PASSWORD=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());
	private static final String MAIL_STORE_PROTOCOL=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_STORE_PROTOCOL.getNameProperty());
	private static final String MAIL_IMAPS=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_IMAPS.getNameProperty());
	private static final String MAIL_INBOX_FOLDER=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_INBOX_FOLDER.getNameProperty());

	private final static Logger LOGGER = Logger.getLogger(EmailService.class);

	public static void check(String host, String storeType, String user, String password) {
		try {
			
			LOGGER.info("Check Email Account");

			// //create properties field
			// Properties properties = new Properties();
			//
			// properties.put("mail.pop3.host", host);
			// properties.put("mail.pop3.port", "995");
			// properties.put("mail.pop3.starttls.enable", "true");
			// Session emailSession = Session.getDefaultInstance(properties);
			//
			// //create the POP3 store object and connect with the pop server
			// Store store = emailSession.getStore("pop3s");
			//
			// store.connect(host, user, password);
			//
			// //create the folder object and open it
			// Folder emailFolder = store.getFolder("inbox");
			// emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			// Message[] messages = emailFolder.getMessages();
			Message[] messages = fetchMessages(MAIL_IMAP, MAIL_RECIPIENT, MAIL_PASSWORD,
					false);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				if (message.getSubject().contains("TEST")) {
					LOGGER.info("---------------------------------");
					LOGGER.info("Email Number " + (i + 1));
					LOGGER.info("Subject: " + message.getSubject());
					LOGGER.info("From: " + message.getFrom()[0]);
					LOGGER.info("Text: " + message.getContent().toString());
					// message.setFlags(new Flags(Flags.Flag.SEEN), false);
				}
			}

			//
			// //close the store and folder objects
			// emailFolder.close(false);
			// store.close();

		} catch (NoSuchProviderException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static Message[] fetchMessages(String host, String user, String password, boolean read)
			throws MessagingException {
		
		LOGGER.info("Fetching Messages");
		
		Properties properties = new Properties();
		properties.put(MAIL_STORE_PROTOCOL, MAIL_IMAPS);

		Session emailSession = Session.getDefaultInstance(properties);
		Store store = emailSession.getStore(MAIL_IMAPS);
		store.connect(host, user, password);

		Folder emailFolder = store.getFolder(MAIL_INBOX_FOLDER);
		// use READ_ONLY if you don't wish the messages
		// to be marked as read after retrieving its content
		emailFolder.open(Folder.READ_WRITE);

		// search for all "unseen" messages
		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, read);
		return emailFolder.search(unseenFlagTerm);
	}

}