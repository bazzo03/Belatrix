package com.belatrix.legal.mailintegrator.config;

public enum EPropertyFactory {

	
	JIRA("jira"),
	TRELLO("trello"),
	SALESFORCE("salesforce");
	
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
	private EPropertyFactory(String nameProperty) {
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
