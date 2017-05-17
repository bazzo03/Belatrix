package com.belatrix.legal.jiraintegrationservice.facade;

import org.apache.log4j.Logger;

import com.belatrix.legal.jiraintegrationservice.Exception.JiraIntegrationServiceException;
import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;
import com.belatrix.legal.jiraintegrationservice.services.JiraIntegrationAPI;

/**
 * Implementation of JiraIntegrationService Interface. 
 * @author cneira
 *
 */
public class JiraIntegrationService implements IJiraIntegrationService {
	
	/**
	 * log
	 */
	private final static Logger logger = Logger.getLogger(JiraIntegrationService.class);

	/**
	 * Method that receive the information of mails to create Jira Issues
	 * @throws JiraIntegrationServiceException 
	 */
	public String createIssue(JiraIssueDTO issue) throws  JiraIntegrationServiceException {
		
		logger.info(String.format("Init createIssue. TxId : %s ",issue.getTransactionId()));
		 return JiraIntegrationAPI.createIssue(issue);	
	
	}

}
