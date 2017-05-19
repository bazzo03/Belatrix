package com.belatrix.legal.mailfinder.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.facade.IJiraIntegrationService;
import com.belatrix.legal.jiraintegrationservice.facade.JiraIntegrationService;
import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadConfig;
import com.belatrix.legal.mailfinder.facade.ISendMailService;
import com.belatrix.legal.mailfinder.facade.SendMailService;

public class EmailService {

	private static final String MAIL_IMAP = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_IMAP.getNameProperty());
	private static final String MAIL_RECIPIENT = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_RECIPIENT.getNameProperty());
	private static final String MAIL_PASSWORD = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());


	private static final String MAIL_FROM = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_FROM.getNameProperty());

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
			Message[] messages = fetchMessages(MAIL_IMAP, MAIL_RECIPIENT, MAIL_PASSWORD, false);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				if (message.getSubject().contains("TEST")) {
					LOGGER.info("---------------------------------");
					LOGGER.info("Email Number " + (i + 1));
					LOGGER.info("Subject: " + message.getSubject());
					LOGGER.info("From: " + message.getFrom()[0]);
					LOGGER.info("Text: " + message.getContent().toString());
					LOGGER.info(message.getDescription());
					// message.setFlags(new Flags(Flags.Flag.SEEN), false);

					IJiraIntegrationService jiraIntegrationService = new JiraIntegrationService();
					JiraIssueDTO issue = new JiraIssueDTO();
					issue.setAction(message.getSubject());
					issue.setDescription(message.getDescription());
					issue.setTransactionId(UUID.randomUUID().toString());

					String generatedId = jiraIntegrationService.createIssue(issue);

					ISendMailService sendMailService = new SendMailService();

					if (generatedId == null || generatedId.equals(StringUtils.EMPTY)) {
						LOGGER.info("El ticket se cre� en Jira con id: " + generatedId + " Para el transaction Id: "
								+ issue.getTransactionId());
						sendMailService.sendEmail(issue.getDescription(), MAIL_RECIPIENT, MAIL_FROM, issue.getAction());
					} else {
						LOGGER.error(
								"No se pudo crear ticket en Jira para el transaction Id: " + issue.getTransactionId());
						sendMailService.sendEmail("", MAIL_RECIPIENT, MAIL_FROM, issue.getAction());
					}

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

		EmailConnection.getInstance().getConnectionStore(host, user, password);
		Folder emailFolder= EmailConnection.getInstance().getFolder() ;
		
		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, read);
		LOGGER.info("Messages were fetched");
		return emailFolder.search(unseenFlagTerm);

	}

	public static List<JiraIssueDTO> createIssues(Message[] messages) {

		List<JiraIssueDTO> issuesList = new ArrayList<>();

		for (int i = 0, n = messages.length; i < n; i++) {
			Message message = messages[i];
			JiraIssueDTO issue = new JiraIssueDTO();
			try {
				if (message.getSubject().contains("NDA")) {
					LOGGER.info("---------------------------------");
					LOGGER.info("Email Number " + (i + 1));
					LOGGER.info("Subject: " + message.getSubject());
					LOGGER.info("From: " + message.getFrom()[0]);
					LOGGER.info("Description: " + message.getDescription());
					LOGGER.info("Text: " + message.getContent().toString());
					// message.setFlags(new Flags(Flags.Flag.SEEN), false);

					IJiraIntegrationService jiraIntegrationService = new JiraIntegrationService();
					issue.setAction(message.getSubject());
					issue.setDescription(message.getContent().toString());
					issue.setTransactionId(UUID.randomUUID().toString());
					issue.setEmail(message.getFrom()[0].toString());

					String generatedId = jiraIntegrationService.createIssue(issue);

					if (generatedId != null && !generatedId.equals(StringUtils.EMPTY)) {
						issue.setIssueId(generatedId);
						LOGGER.info("Jira Ticket Created");
					} else {
						LOGGER.error(String.format("Error in Jira Client with Txid: ", issue.getTransactionId()));
					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error(String.format("Error with transaction id: ", issue.getTransactionId(), e));
			} finally {
				issuesList.add(issue);
			}
		}

		return issuesList;
	}
}