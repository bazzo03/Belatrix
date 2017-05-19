package com.belatrix.legal.mailfinder.config;

public enum EPropertyDaemon {

	DELAY("delay"),
	PERIOD("period"),
	SLEEP("sleep");
	
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
	private EPropertyDaemon(String nameProperty) {
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
	
	public Long getLongPropertyName() {
		return Long.valueOf(this.getNameProperty());
	}

}
