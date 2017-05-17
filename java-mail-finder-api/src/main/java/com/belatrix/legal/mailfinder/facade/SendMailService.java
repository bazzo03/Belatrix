package com.belatrix.legal.mailfinder.facade;

import com.belatrix.legal.mailfinder.services.SendEMailService;

public class SendMailService implements ISendMailService {

	public void sendEmail(String text, String recipient, String addressee, String subject) {
		SendEMailService.sendEmail(text, recipient, addressee, subject);
		
	}

}
