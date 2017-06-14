package com.belatrix.legal.mailfinder.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.dto.GeneralIssueDTO;
import com.belatrix.legal.jiraintegrationservice.facade.IJiraIntegrationService;
import com.belatrix.legal.jiraintegrationservice.facade.JiraIntegrationService;
import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadMailConfig;
import com.belatrix.legal.mailfinder.factory.MailFactory;

public class EmailService {

	private final static Logger LOGGER = Logger.getLogger(EmailService.class);

	private static final String SUBJECT = LoadMailConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_SUBJECT.getNameProperty());

	public static Message[] fetchMessages(String host, String user, String password, boolean read)
			throws MessagingException {

		LOGGER.info("Fetching Messages");

		EmailConnection.getInstance().getConnectionStore(host, user, password);
		Folder emailFolder = EmailConnection.getInstance().getFolder();

		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, read);

		LOGGER.trace("Messages were fetched");

		return emailFolder.search(unseenFlagTerm);

	}

	public static List<GeneralIssueDTO> createIssues(Message[] messages) {

		List<GeneralIssueDTO> issuesList = new ArrayList<>();

		for (int i = 0, n = messages.length; i < n; i++) {
			Message message = messages[i];
			GeneralIssueDTO issue = new GeneralIssueDTO();
			try {
				message.setFlags(new Flags(Flags.Flag.SEEN), true);
//				IJiraIntegrationService jiraIntegrationService = new JiraIntegrationService();
				
				issue.setAction(message.getSubject());
				issue.setDescription(getContentFromEmail(message.getContent()));
				issue.setTransactionId(UUID.randomUUID().toString());
				issue.setEmail(message.getFrom()[0].toString());

				LOGGER.trace("---------------------------------");
				LOGGER.trace("Email Number " + (i + 1));
				LOGGER.trace("Subject: " + message.getSubject());
				LOGGER.trace("From: " + message.getFrom()[0]);
				LOGGER.trace("Description: " + message.getDescription());
				LOGGER.trace("Text: " + getContentFromEmail(message.getContent()));

				if (message.getSubject().contains(SUBJECT)) {
					LOGGER.info("Obtaining IssueId for TxId:" + issue.getTransactionId());
//					String generatedId = jiraIntegrationService.createIssue(issue);
					MailFactory.mailProcess(issue);

					/*if (generatedId != null && !generatedId.equals(StringUtils.EMPTY)) {
						issue.setIssueId(generatedId);
						LOGGER.info("Jira Ticket Created - Id: " + generatedId);
					} else {
						LOGGER.warn(String.format("Error in Jira Client with Txid: ", issue.getTransactionId()));
					}*/
					//TODO generatedId will not be returned and email must be sent from mail sender

					issuesList.add(issue);
				}
			} catch (Exception e) {
				LOGGER.error(String.format("Error with transaction id: ", issue.getTransactionId(), e));
			}
		}

		return issuesList;
	}

	private static String getContentFromEmail(Object msgContent) throws MessagingException, IOException {

		String content = "";

		if (msgContent instanceof Multipart) {
			Multipart multipart = (Multipart) msgContent;
			LOGGER.trace("BodyPart" + "MultiPartCount: " + multipart.getCount());
			for (int j = 0; j < multipart.getCount(); j++) {
				BodyPart bodyPart = multipart.getBodyPart(0);
				String disposition = bodyPart.getDisposition();
				if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
					LOGGER.trace("Mail have some attachment");
					DataHandler handler = bodyPart.getDataHandler();
					LOGGER.trace("file name : " + handler.getName());
				} else {
					content = (String) bodyPart.getContent(); // the F
				}
			}
		} else {
			content = msgContent.toString();
		}

		return content;
	}

}