package com.belatrix.legal.jiraintegrationservice.services;

import com.belatrix.legal.jiraintegrationservice.config.EPropertyJira;
import com.belatrix.legal.jiraintegrationservice.config.LoadConfig;
import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.BasicCredentials;
import com.belatrix.legal.jiraintegrationservice.jiraclient.Field;
import com.belatrix.legal.jiraintegrationservice.jiraclient.Issue;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraClient;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;

/**
 * 
 * @author cneira
 *
 */
public class JiraIntegrationAPI {
	
	/**
	 * Properties Configuration
	 */
	private static final String url=LoadConfig.getInstance().getProperty(EPropertyJira.URL.getNameProperty());
	private static final String user=LoadConfig.getInstance().getProperty(EPropertyJira.URL.getNameProperty());
	private static final String password=LoadConfig.getInstance().getProperty(EPropertyJira.URL.getNameProperty());
	private static final BasicCredentials creds= new BasicCredentials(user, password);
	
	/**
	 * Client Jira
	 */
	private static JiraClient jira;
	

	/**
	 * Method to create Issues in Jira.
	 * @param action
	 * @param project
	 * @param issueType
	 * @throws JiraException
	 */
	public static void createIssue(JiraIssueDTO issue ) throws JiraException {
			
		jira = new JiraClient(url, creds);
		Issue newIssue = jira.createIssue(issue.getProject(), issue.getIssueType()).field(Field.SUMMARY, issue.getAction())
				.field(Field.DESCRIPTION, issue.getDescription()).field(Field.REPORTER, user)
				.field(Field.ASSIGNEE, user).execute();
	
	}
}