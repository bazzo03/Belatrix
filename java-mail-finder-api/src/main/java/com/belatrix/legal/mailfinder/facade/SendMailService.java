package com.belatrix.legal.mailfinder.facade;

import com.belatrix.legal.mailfinder.services.SendEMailService;

public class SendMailService implements ISendMailService {

	public void sendEmail() {
		SendEMailService.sendEmail();
	}

}
