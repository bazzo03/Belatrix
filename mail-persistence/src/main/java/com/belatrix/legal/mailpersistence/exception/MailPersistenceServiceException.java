package com.belatrix.legal.mailpersistence.exception;

public class MailPersistenceServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MailPersistenceServiceException(String msg) {
		super(msg);
	}
	
	

	public MailPersistenceServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
