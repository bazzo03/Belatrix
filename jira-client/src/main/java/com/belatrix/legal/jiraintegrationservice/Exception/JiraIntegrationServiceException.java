package com.belatrix.legal.jiraintegrationservice.Exception;

public class JiraIntegrationServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public JiraIntegrationServiceException(String msg) {
		super(msg);
	}
	
	

	public JiraIntegrationServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
