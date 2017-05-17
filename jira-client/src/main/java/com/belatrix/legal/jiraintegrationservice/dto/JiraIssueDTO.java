package com.belatrix.legal.jiraintegrationservice.dto;

public class JiraIssueDTO {
	
	private String action;
	private String project;
	private String issueType;
	private String description;
	private String transactionId;
	private String issueId;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getJiraId() {
		return issueId;
	}
	public void setJiraId(String jiraId) {
		this.issueId = jiraId;
	}
	
	
	

}
