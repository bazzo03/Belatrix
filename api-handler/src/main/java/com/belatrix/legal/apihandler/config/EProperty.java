package com.belatrix.legal.apihandler.config;

/**
 * 
 * @author cneira
 *
 */
public enum EProperty {
	/**
	 * Url
	 */
	URL("properties.#app#.url"),
	
	/**
	 * User
	 */
	CREDENTIALS("properties.#app#.credentials"),
	/**
	 * Password
	 */
	TOKEN("properties.#app#.token"),
	
	/**
	 * Project Name
	 */
	KEY("properties.#app#.apikey"),;

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
	private EProperty(String nameProperty) {
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
