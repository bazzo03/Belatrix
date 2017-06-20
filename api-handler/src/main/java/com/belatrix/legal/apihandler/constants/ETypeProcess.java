package com.belatrix.legal.apihandler.constants;

/**
 * Process that can be used.
 * @author cneira
 *
 */
public enum ETypeProcess {
	
	// Process
	JIRA("jira"),
	SALESFORCE("salesforce"),
	TRELLO("trello"),
	
	
	// Operations
	CREATE_ISSUE("jira","createIssue"),
	ADD_COMMENT_ISSUE("jira","addCommentIssue"),
	ATTACHMENT_ISSUE("jira","attachmentIssue"),
	
	CREATE_CARD("trello","createCard"),
	EDIT_CARD("trello","editCard"),
	ADD_COMMENT_CARD("trello","addCommentCard"),
	ATTACHMENT_CARD("trello","attachmentCard"),
	
	;
	
	/**
	 * Process Name
	 */
	private String process;

	/**
	 * Value
	 */
	private String value;

	/**
	 * Constructor
	 * 
	 * @param process
	 * 
	 */
	private ETypeProcess(String process) {
		this.process = process;
	}

	/**
	 * 
	 * @param process
	 * @param value
	 */
	private ETypeProcess(String process, String value) {
		this.process = process;
		this.value = value;
	}

	/**
	 * Gets process name
	 * 
	 * @return
	 */
	public String getProcess() {
		return this.process;
	}

	/**
	 * Gets Value
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

}
