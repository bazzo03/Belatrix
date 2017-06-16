package com.belatrix.legal.mailintegrator.constant;

public enum ETask {

	CREATE_ISSUE_JIRA("createIssue"),
	UPDATE_ISSUE_JIRA("updateIssue"),
	
	CREATE_CARD_TRELLO("createCard"),
	FIND_CARD_TRELLO("findCard"),
	UPDATE_CARD_TRELLO("updateCard"),
	
	CREATE_LEAD_SF("createLead"),
	UPDATE_LEAD_SF("updateLead")
	;
	
	
	private ETask(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	private final String value;
	
	
}
