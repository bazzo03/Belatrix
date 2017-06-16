package com.belatrix.legal.mailintegrator.facade;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.MailDTO;
import com.belatrix.legal.mailintegrator.services.jira.ProcessJiraIssueService;
import com.belatrix.legal.mailintegrator.services.salesforce.ProcessTrelloIssueService;
import com.belatrix.legal.mailintegrator.services.trello.ProcessSalesforceIssueService;

public class ProcessIssueService implements IProcessIssueService {

	public void processIssueJira(MailDTO issue, EIssueProcess process) {
		
		ProcessJiraIssueService.processIssue(issue, process);
	}

	public void processIssueTrello(MailDTO issue, EIssueProcess process) {

		ProcessTrelloIssueService.processIssue(issue, process);
	}

	public void processIssueSalesForce(MailDTO issue, EIssueProcess process) {

		ProcessSalesforceIssueService.processIssue(issue, process);
	}

}
