package com.belatrix.legal.mailfinder.facade;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.belatrix.legal.jiraintegrationservice.dto.GeneralIssueDTO;
import com.belatrix.legal.mailfinder.services.EmailService;

public class CheckEmailService implements ICheckEmailService {

	public Message[] fetchMessages(String host, String user, String password, boolean read) throws MessagingException {
		return EmailService.fetchMessages(host, user, password, read);
	}

	public List<GeneralIssueDTO> createIssues(Message[] messages) {
		return EmailService.createIssues(messages);
	}

}
