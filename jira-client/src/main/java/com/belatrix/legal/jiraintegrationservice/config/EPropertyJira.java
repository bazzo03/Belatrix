package com.belatrix.legal.jiraintegrationservice.config;

/**
 * 
 * @author cneira
 *
 */
public enum EPropertyJira {
	/**
	 * Url
	 */
	URL("properties.jira.url"),
	
	/**
	 * User
	 */
	USER("properties.jira.user"),
	/**
	 * Password
	 */
	PASSWORD("properties.jira.pwd"),;

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
	private EPropertyJira(String nameProperty) {
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
