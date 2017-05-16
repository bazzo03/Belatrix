package com.belatrix.legal.jiraintegrationservice.facade;

import com.belatrix.legal.jiraintegrationservice.dto.JiraIssueDTO;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;

public interface JiraIntegrationService {
	
	
	public void createIssue(JiraIssueDTO issue) throws JiraException;

}
