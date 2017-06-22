package com.belatrix.legal.mailintegrator.config;

/**
 * 
 * @author cneira
 *
 */
public enum EProperty {

	
	PROPERTY_KEY_LIST("property.list.keyword"),

	OPERATIONS_APP_CREATE("property.operations.#app#.create"),
	OPERATIONS_APP_UPDATE("property.operations.#app#.edit"),
	OPERATIONS_APP_ADD_COMMENT("property.operations.#app#.addcoment"),
	OPERATIONS_APP_ATTACHMENT("property.operations.#app#.attachment"),
	
	PROPERTY_KEY_TYPE("property.#key#.type"),
	PROPERTY_KEY_FORM("property.#key#."), //concatenar el type al final
	PROPERTY_KEY_OPERATION_APP("property.#key#.operation."); //concaternar el type al final
	
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
