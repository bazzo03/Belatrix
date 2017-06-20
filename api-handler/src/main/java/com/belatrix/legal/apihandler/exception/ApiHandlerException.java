package com.belatrix.legal.apihandler.exception;

public class ApiHandlerException  extends Exception {


	private static final long serialVersionUID = 1L;

	
	public ApiHandlerException(String msg) {
		super(msg);
	}
	
	

	public ApiHandlerException(String msg, Throwable cause) {
		super(msg, cause);
	}

}