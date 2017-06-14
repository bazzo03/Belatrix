package com.belatrix.legal.mailfinder.factory;

import com.belatrix.legal.jiraintegrationservice.dto.GeneralIssueDTO;
import com.belatrix.legal.mailfinder.config.EPropertyFactory;
import com.belatrix.legal.mailfinder.config.LoadFactoryConfig;

public class MailFactory {
	
//	private static final String ZERO = "0";
	private static final String ONE = "1";
	
	private static final String JIRA = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.JIRA.getNameProperty());
	private static final String TRELLO = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.TRELLO.getNameProperty());
	private static final String SALESFORCE = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.SALESFORCE.getNameProperty());

	public static void mailProcess(GeneralIssueDTO issue) {

		if (JIRA.equals(ONE)) {
			//TODO call jira integrator and send general dto -> businessFacade.processJiraIssue(issue)
		}
		if (TRELLO.equals(ONE)) {
			//TODO call trello integrator and send general dto -> businessFacade.processTrelloIssue(issue)
		}
		if (SALESFORCE.equals(ONE)) {
			//TODO call salesforce integrator and send general dto -> businessFacade.processSalesForceIssue(issue)
		}
		
	}
}
