package com.belatrix.legal.apihandler.constants;

public enum ETypeProcess {
	
	JIRA("jira"),
	SALESFORCE("salesforce"),
	TRELLO("trello"),
	
	CREATE_ISSUE("jira","createIssue"),
	ADD_COMMENT_ISSUE("jira","addCommentIssue"),
	ATTACHMENT_ISSUE("jira","attachmentIssue"),
	
	CREATE_CARD("trello","createCard"),
	EDIT_CARD("trello","editCard"),
	ADD_COMMENT_CARD("trello","addCommentCard"),
	ATTACHMENT_CARD("trello","attachmentCard"),
	
	;
	
	/**
	 * Name property
	 */
	private String process;
	
	/**
	 * Name property
	 */
	private String value;

	/**
	 * Constructor
	 * 
	 * @param nameProperty
	 *            Name property
	 */
	private ETypeProcess(String process) {
		this.process = process;
	}
	
	private ETypeProcess(String process, String value) {
		this.process = process;
		this.value = value;
	}

	/**
	 * Gets name property
	 * 
	 * @return Name Porperty
	 */
	public String getProcess() {
		return this.process;
	}

	public String getValue() {
		return value;
	}


	
	
	
	

}
