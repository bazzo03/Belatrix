package com.belatrix.legal.apihandler.constants;

public enum ETypeProcess {
	
	JIRA("jira"),
	SALESFORCE("salesforce"),
	TRELLO("trello"),;
	
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
	private ETypeProcess(String nameProperty) {
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
