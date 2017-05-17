package com.belatrix.legal.mailfinder.facade;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadConfig;

public class MailApplicationService implements IMailApplicationService {

	private final static Logger LOGGER = Logger.getLogger(MailApplicationService.class);

	private static final String MAIL_RECIPIENT = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_RECIPIENT.getNameProperty());
	private static final String MAIL_PASSWORD = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());
	private static final String MAIL_POP_GMAIL = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_POP_GMAIL.getNameProperty());
	private static final String MAIL_POP3 = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_POP3.getNameProperty());
	private static final String MAIL_IMAP = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_IMAP.getNameProperty());
	private static final String MAIL_FROM = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_FROM.getNameProperty());

	public void run() {

		String host = MAIL_POP_GMAIL;// change accordingly
		String mailStoreType = MAIL_POP3;
		String username = MAIL_RECIPIENT;// change
											// accordingly
		String password = MAIL_PASSWORD;// change accordingly

		ICheckEmailService checkEmailService = new CheckEmailService();

		Message[] messages = null;
		List<JiraIssueDTO> issues = new ArrayList<>();
		try {
			messages = checkEmailService.fetchMessages(MAIL_IMAP, MAIL_RECIPIENT, MAIL_PASSWORD, false);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (messages != null && messages.length > 0) {
			LOGGER.info("Messages found in Inbox Folder and DTO's are ready to be created");
			issues = checkEmailService.createIssues(messages);
		}
		if (issues != null && issues.size() > 0) {
			LOGGER.info("Issues were created and are ready to be sent via Email");
			sendEmailIssues(issues);
		} else {
			LOGGER.error("There are no Issues to be sent");
		}

	}

	private void sendEmailIssues(List<JiraIssueDTO> issues) {

		ISendMailService sendMailService = new SendMailService();

		for (JiraIssueDTO dto : issues) {
			if (dto.getJiraId() != null && !dto.getJiraId().equals(StringUtils.EMPTY)) {
				sendMailService.sendEmail(dto.getDescription() + " " + dto.getJiraId(), MAIL_RECIPIENT, MAIL_FROM, dto.getAction());
			} else {
				LOGGER.error(String.format("No Issue created for transaction id: ", dto.getTransactionId()));
				sendMailService.sendEmail("No Issue created " , MAIL_RECIPIENT, MAIL_FROM, dto.getAction());
			}
		}
	}
}
