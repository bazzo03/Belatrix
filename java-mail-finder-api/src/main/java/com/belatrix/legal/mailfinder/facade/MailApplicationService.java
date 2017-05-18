package com.belatrix.legal.mailfinder.facade;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadConfig;

public class MailApplicationService extends Thread {

	private final static Logger LOGGER = Logger.getLogger(MailApplicationService.class);

	private static final String MAIL_RECIPIENT = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_RECIPIENT.getNameProperty());
	private static final String MAIL_PASSWORD = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());
	private static final String MAIL_IMAP = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_IMAP.getNameProperty());
	private static final String MAIL_FROM = LoadConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_FROM.getNameProperty());

	public void run() {

		LOGGER.info("In run Method: currentThread() is" + Thread.currentThread());

		while (true) {

			

			ICheckEmailService checkEmailService = new CheckEmailService();

			Message[] messages = null;
			List<JiraIssueDTO> issues = new ArrayList<>();
			try {
				messages = checkEmailService.fetchMessages(MAIL_IMAP, MAIL_RECIPIENT, MAIL_PASSWORD, false);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			if (messages != null && messages.length > 0) {
				LOGGER.info(messages.length + " Messages found in Inbox Folder");
				issues = checkEmailService.createIssues(messages);
			}
			if (issues != null && issues.size() > 0) {
				LOGGER.info(issues.size() + " Issues were created and are ready to be sent via Email");
				sendEmailIssues(issues);
			} else {
				LOGGER.info("0 Issues created. There are no Issues to be sent");
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				LOGGER.info("Leaving run Method");
			}

		}

	}

	private void sendEmailIssues(List<JiraIssueDTO> issues) {

		ISendMailService sendMailService = new SendMailService();

		for (JiraIssueDTO dto : issues) {
			if (dto.getJiraId() != null && !dto.getJiraId().equals(StringUtils.EMPTY)) {
				sendMailService.sendEmail(dto.getDescription(), MAIL_FROM, MAIL_RECIPIENT,
						dto.getAction() + " " + dto.getJiraId());
			} else {
				LOGGER.error(String.format("No Issue created for Txid: ", dto.getTransactionId()));
				sendMailService.sendEmail("No Issue created ", MAIL_FROM, MAIL_RECIPIENT, dto.getAction());
			}
		}
	}
}
