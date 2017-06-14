package com.belatrix.legal.jiraintegrationservice.facade;

import com.belatrix.legal.jiraintegrationservice.Exception.JiraIntegrationServiceException;
import com.belatrix.legal.jiraintegrationservice.dto.GeneralIssueDTO;

public interface IJiraIntegrationService {
	
	
	public String createIssue(GeneralIssueDTO issue) throws JiraIntegrationServiceException;

}
