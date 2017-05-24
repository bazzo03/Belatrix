package com.belatrix.legal.mailfinder.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.facade.IJiraIntegrationService;
import com.belatrix.legal.jiraintegrationservice.facade.JiraIntegrationService;
import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadMailConfig;

public class EmailService {

	private final static Logger LOGGER = Logger.getLogger(EmailService.class);
	
	private static final String SUBJECT = LoadMailConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_SUBJECT.getNameProperty());

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
				LOGGER.info("**********"+SUBJECT);
				if (message.getSubject().contains(SUBJECT)) {
					LOGGER.info("---------------------------------");
					LOGGER.info("Email Number " + (i + 1));
					LOGGER.info("Subject: " + message.getSubject());
					LOGGER.info("From: " + message.getFrom()[0]);
					LOGGER.info("Description: " + message.getDescription());
					LOGGER.info("Text: " + message.getContent().toString());
				    message.setFlags(new Flags(Flags.Flag.SEEN), true);
					

					IJiraIntegrationService jiraIntegrationService = new JiraIntegrationService();
					issue.setAction(message.getSubject());
					issue.setDescription(message.getContent().toString());
					issue.setTransactionId(UUID.randomUUID().toString());
					issue.setEmail(message.getFrom()[0].toString());

					LOGGER.info("Obtaining IssueId for TxId:" + issue.getTransactionId());
					String generatedId = jiraIntegrationService.createIssue(issue);
					LOGGER.info("GeneratedId id: " + generatedId);

					if (generatedId != null && !generatedId.equals(StringUtils.EMPTY)) {
						issue.setIssueId(generatedId);
						LOGGER.info("Jira Ticket Created - Id: " + generatedId);
					} else {
						LOGGER.warn(String.format("Error in Jira Client with Txid: ", issue.getTransactionId()));
					}
				}
			} catch (Exception e) {
				LOGGER.error(String.format("Error with transaction id: ", issue.getTransactionId(), e));
			} finally {
				issuesList.add(issue);
			}
		}

		return issuesList;
	}
}