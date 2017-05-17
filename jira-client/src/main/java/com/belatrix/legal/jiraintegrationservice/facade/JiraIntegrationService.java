package com.belatrix.legal.jiraintegrationservice.facade;

import com.belatrix.legal.jiraintegrationservice.Exception.JiraIntegrationServiceException;
import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;

public interface JiraIntegrationService {
	
	
	public String createIssue(JiraIssueDTO issue) throws JiraIntegrationServiceException;

}
