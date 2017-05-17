package com.belatrix.legal.jiraintegrationservice.facade;

import com.belatrix.legal.jiraintegrationservice.Exception.JiraIntegrationServiceException;
import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;

public interface IJiraIntegrationService {
	
	
	public String createIssue(JiraIssueDTO issue) throws JiraIntegrationServiceException;

}
