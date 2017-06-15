package com.belatrix.legal.mailintegrator.exception;

public class MailIntegratorServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MailIntegratorServiceException(String msg) {
		super(msg);
	}
	
	

	public MailIntegratorServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
