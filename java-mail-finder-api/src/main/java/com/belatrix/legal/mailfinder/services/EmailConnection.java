package com.belatrix.legal.mailfinder.services;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.belatrix.legal.mailfinder.config.EPropertyMail;
import com.belatrix.legal.mailfinder.config.LoadMailConfig;

public class EmailConnection {

	private static final String MAIL_STORE_PROTOCOL = LoadMailConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_STORE_PROTOCOL.getNameProperty());
	private static final String MAIL_IMAPS = LoadMailConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_IMAPS.getNameProperty());
	private static final String MAIL_INBOX_FOLDER = LoadMailConfig.getInstance()
			.getProperty(EPropertyMail.MAIL_INBOX_FOLDER.getNameProperty());
	


	private Store store;

	private Folder folder;

	private static class EmailConnectionInner {
		public static final EmailConnection instance = new EmailConnection();
	}

	public static EmailConnection getInstance() {
		return EmailConnectionInner.instance;
	}

	public Store getConnectionStore(String host, String user, String password) throws MessagingException {

		if (store == null) {
			Properties properties = new Properties();
			properties.put(MAIL_STORE_PROTOCOL, MAIL_IMAPS);

			Session emailSession = Session.getDefaultInstance(properties);
			store = emailSession.getStore(MAIL_IMAPS);
			store.connect(host, user, password);
		}
		return store;
	}

	public void closeStore() throws MessagingException {
		store.close();
	}

	public Folder getFolder() throws MessagingException {

		if (folder == null) {
			folder = store.getFolder(MAIL_INBOX_FOLDER);	
			folder.open(Folder.READ_WRITE);
		}

		return folder;
	}

}
