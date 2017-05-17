package com.belatrix.legal.mailfinder.facade;

public interface ISendMailService {

	public void sendEmail(String text, String recipient, String addressee, String subject);
	
}
