package com.belatrix.legal.jiraintegrationservice.constants;

/**
 * Enum with Issue Types
 * @author cneira
 *
 */
public enum EIssueType {
	
	/**
	 * Story
	 */
	STORY("Story","Functionality request expressed from the perspective of the user"),
	
	/**
	 * Bug
	 */
	BUG("Bug","Problem that impairs product or service functionality"),
	
	
	/**
	 * Epic
	 */
	EPIC("Epic","Large piece of work that encompasses many issues"),;
	


	/**
	 * Name property
	 */
	private String nameProperty;
	
	/**
	 * Description
	 */
	private String description;

	/**
	 * Constructor
	 * 
	 * @param nameProperty
	 *            Name property
	 */
	private EIssueType(String nameProperty,String description) {
		this.nameProperty = nameProperty;
		this.description = description;
	}

	/**
	 * Gets name property
	 * 
	 * @return Name Porperty
	 */
	public String getNameProperty() {
		return this.nameProperty;
	}

	/**
	 * Gets Description
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}
	
	

}
