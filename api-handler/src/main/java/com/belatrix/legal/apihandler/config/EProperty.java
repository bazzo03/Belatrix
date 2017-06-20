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
	 * credentials
	 */
	CREDENTIALS("properties.#app#.credentials"),
	/**
	 * token
	 */
	TOKEN("properties.#app#.token"),
	
	/**
	 * api key
	 */
	KEY("properties.#app#.apikey");

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
