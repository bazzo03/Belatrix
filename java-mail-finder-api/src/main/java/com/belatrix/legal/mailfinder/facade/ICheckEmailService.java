package com.belatrix.legal.mailfinder.facade;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface ICheckEmailService {

	void check(String host, String storeType, String user, String password);

	Message[] fetchMessages(String host, String user, String password, boolean read) throws MessagingException;
}
