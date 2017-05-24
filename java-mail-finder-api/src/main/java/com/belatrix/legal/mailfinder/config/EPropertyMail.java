package com.belatrix.legal.mailfinder.config;

/**
 * 
 * @author dbernal
 *
 */
public enum EPropertyMail {

	MAIL_IMAP("mail.imap.gmail"),
	MAIL_FROM("mail.from"),
	MAIL_RECIPIENT("mail.recipient"),
	MAIL_SUBJECT("mail.subject"),
	MAIL_USERNAME("mail.username"),
	MAIL_PASSWORD("mail.password"),
	MAIL_POP_GMAIL("mail.pop.gmail"),
	MAIL_POP3("mail.pop3"),
	MAIL_SMTP_AUTH("mail.smpt.auth"),
	MAIL_STORE_PROTOCOL("mail.store.protocol"),
	MAIL_IMAPS("mail.imaps"),
	MAIL_INBOX_FOLDER("mail.inbox.folder"),
	MAIL_START_TLS("mail.start.tls"),
	MAIL_SMTP_HOST("mail.smtp.host"),
	MAIL_SMTP_GMAIL("mail.smtp.gmail"),
	MAIL_SMTP_PORT("mail.smtp.port"),
	MAIL_SMTP_PORT_NUMBER("mail.smtp.port.number"),
	
	MESSAGE_SUCCES("mail.message.success"),
	MESSAGE_FAIL("mail.message.fail");


	

	/**
	 * Name property
	 */
	private String nameProperty;

	/**
	 * Constructor
	 * 
	 * @param nameProperty
	 *            Name property
	 */
	private EPropertyMail(String nameProperty) {
		this.nameProperty = nameProperty;
	}

	/**
	 * Gets name property
	 * 
	 * @return Name Porperty
	 */
	public String getNameProperty() {
		return this.nameProperty;
	}

}
