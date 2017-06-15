package com.belatrix.legal.mailintegrator.factory;

import com.belatrix.legal.mailintegrator.config.EPropertyFactory;
import com.belatrix.legal.mailintegrator.config.LoadFactoryConfig;
import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.GeneralIssueDTO;
import com.belatrix.legal.mailintegrator.facade.IProcessIssueService;
import com.belatrix.legal.mailintegrator.facade.ProcessIssueService;

public class MailFactory {
	
	private static final String ONE = "1";
	
	private static final String JIRA = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.JIRA.getNameProperty());
	private static final String TRELLO = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.TRELLO.getNameProperty());
	private static final String SALESFORCE = LoadFactoryConfig.getInstance()
			.getProperty(EPropertyFactory.SALESFORCE.getNameProperty());

	
	private static IProcessIssueService service = new ProcessIssueService();
	
	
	public static void processMail(GeneralIssueDTO issue) {

		if (JIRA.equals(ONE)) {
			//TODO call jira integrator and send general dto -> businessFacade.processJiraIssue(issue)
			service.processIssueJira(issue, EIssueProcess.CREATION);
		}
		if (TRELLO.equals(ONE)) {
			//TODO call trello integrator and send general dto -> businessFacade.processTrelloIssue(issue)
			service.processIssueTrello(issue, EIssueProcess.CREATION);
		}
		if (SALESFORCE.equals(ONE)) {
			//TODO call salesforce integrator and send general dto -> businessFacade.processSalesForceIssue(issue)
			service.processIssueSalesForce(issue, EIssueProcess.CREATION);
		}
		
	}
}
