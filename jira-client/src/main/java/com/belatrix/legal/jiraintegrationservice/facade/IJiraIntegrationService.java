package com.belatrix.legal.jiraintegrationservice.facade;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;
import com.belatrix.legal.jiraintegrationservice.services.JiraIntegrationAPI;

public class IJiraIntegrationService implements JiraIntegrationService {

	public void createIssue(JiraIssueDTO issue) throws JiraException {
		
		JiraIntegrationAPI.createIssue(issue);
		
	}

}
