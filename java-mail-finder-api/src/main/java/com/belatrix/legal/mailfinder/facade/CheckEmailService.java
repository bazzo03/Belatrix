package com.belatrix.legal.mailfinder.facade;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.belatrix.legal.mailfinder.services.EmailService;

public class CheckEmailService implements ICheckEmailService {

	public void check(String host, String storeType, String user, String password) {
		EmailService.check(host, storeType, user, password);
	}

	public Message[] fetchMessages(String host, String user, String password, boolean read) throws MessagingException {
		return EmailService.fetchMessages(host, user, password, read);
	}

}
