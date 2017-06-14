package com.belatrix.legal.apihandler.dto;

import java.util.Map;

public class AppProcessDTO {
	
	private String url;
	
	private String credentials;
	
	private String type;
	
	private String name;
	
	private Map<String,OperationDTO> operations;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, OperationDTO> getOperations() {
		return operations;
	}

	public void setOperations(Map<String, OperationDTO> operations) {
		this.operations = operations;
	}


	
	
	
	

}
