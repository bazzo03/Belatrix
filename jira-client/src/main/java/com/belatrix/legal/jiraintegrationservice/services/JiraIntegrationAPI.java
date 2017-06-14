package com.belatrix.legal.jiraintegrationservice.services;

import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.Exception.JiraIntegrationServiceException;
import com.belatrix.legal.jiraintegrationservice.config.EPropertyJira;
import com.belatrix.legal.jiraintegrationservice.config.LoadConfig;
import com.belatrix.legal.jiraintegrationservice.constants.EIssueType;
import com.belatrix.legal.jiraintegrationservice.dto.GeneralIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.BasicCredentials;
import com.belatrix.legal.jiraintegrationservice.jiraclient.Field;
import com.belatrix.legal.jiraintegrationservice.jiraclient.Issue;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraClient;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;
import com.google.gson.Gson;

/**
 * Class that contains Jira managment methods.
 * 
 * @author cneira
 *
 */
public class JiraIntegrationAPI {

	/**
	 * log
	 */
	private final static Logger logger = Logger.getLogger(JiraIntegrationAPI.class);

	/**
	 * Properties Configuration
	 */
	private static final String url = LoadConfig.getInstance().getProperty(EPropertyJira.URL.getNameProperty());
	private static final String user = LoadConfig.getInstance().getProperty(EPropertyJira.USER.getNameProperty());
	private static final String password = LoadConfig.getInstance().getProperty(EPropertyJira.PASSWORD.getNameProperty());
	private static final String project = LoadConfig.getInstance().getProperty(EPropertyJira.PROJECT.getNameProperty());
	private static final BasicCredentials creds = new BasicCredentials(user, password);

	/**
	 * Jira Client
	 */
	private static JiraClient jira;

	/**
	 * Framework Json
	 */

	/**
	 * Method to create Issues in Jira.
	 * 
	 * @param action
	 * @param project
	 * @param issueType
	 * @throws JiraException
	 */
	public static String createIssue(GeneralIssueDTO issue) throws JiraIntegrationServiceException {

		logger.trace(String.format(" CreateIssue. Object received : %s ",
				new Gson().toJson(issue, GeneralIssueDTO.class)));

		if (!url.isEmpty() && creds != null) {
			
			String projectName = "";
			if (issue.getProject() == null || issue.getProject().isEmpty()) {
				projectName = project;
			} else {
				projectName = issue.getProject();
			}

			try {
				jira = new JiraClient(url, creds);
				logger.trace(String.format("Connect to Url : %s . TxId : %s",url,issue.getTransactionId()));
				Issue newIssue = jira.createIssue(projectName, EIssueType.STORY.getNameProperty())
						.field(Field.SUMMARY, issue.getAction()).field(Field.DESCRIPTION, issue.getDescription())
						.field(Field.REPORTER, user).field(Field.ASSIGNEE, user).execute();
				logger.info(String.format("Issue No. %s was create .Txid : %s", newIssue.getId(),
						issue.getTransactionId()));
				return newIssue.getKey();
			} catch (JiraException e) {
				logger.error("Error returned by Jira Client . TxId : "+ issue.getTransactionId(), e);
				throw new JiraIntegrationServiceException("Error in Jira Client", e);
			}

		} else {
			logger.error("Jira configuration not found. TxId "+issue.getTransactionId());
			throw new JiraIntegrationServiceException(" Jira Configuration not found.");
		}

	}
}