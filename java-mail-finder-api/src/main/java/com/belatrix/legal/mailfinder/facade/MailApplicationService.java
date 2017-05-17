package com.belatrix.legal.mailfinder.facade;

import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadConfig;

public class MailApplicationService implements IMailApplicationService {

	private static final String MAIL_RECIPIENT=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_RECIPIENT.getNameProperty());
	private static final String MAIL_PASSWORD=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_PASSWORD.getNameProperty());
	private static final String MAIL_POP_GMAIL=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_POP_GMAIL.getNameProperty());
	private static final String MAIL_POP3=LoadConfig.getInstance().getProperty(EPropertyMail.MAIL_POP3.getNameProperty());

	public void run() {
		
		String host = MAIL_POP_GMAIL;// change accordingly
		String mailStoreType = MAIL_POP3;
		String username = MAIL_RECIPIENT;// change
																// accordingly
		String password = MAIL_PASSWORD;// change accordingly

		ICheckEmailService checkEmailService = new CheckEmailService(); 
		
		checkEmailService.check(host, mailStoreType, username, password);
	}
}
