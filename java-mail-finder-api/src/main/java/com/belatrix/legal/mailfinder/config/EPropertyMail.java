package com.belatrix.legal.mailfinder.config;

/**
 * 
 * @author dbernal
 *
 */
public enum EPropertyMail {
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
	private EPropertyMail(String nameProperty) {
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
