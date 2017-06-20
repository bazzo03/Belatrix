package com.belatrix.legal.apihandler.dto;

import java.util.Map;

/**
 * 
 * @author cneira
 *
 */
public class AppProcessDTO {
	
	private String url;
	
	private String credentials;
	
	private String token;
	
	private String key;
	
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
