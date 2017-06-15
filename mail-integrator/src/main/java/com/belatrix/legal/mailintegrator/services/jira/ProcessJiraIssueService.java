package com.belatrix.legal.mailintegrator.services.jira;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.GeneralIssueDTO;

public class ProcessJiraIssueService {

	public static void processIssue(GeneralIssueDTO issue, EIssueProcess process) {

		switch (process) {
		case CREATION:
			createIssue(issue);
			break;
		case EDITION:
			modifyIssue(issue);
			break;
		default:
			break;
		}
	}
	
	private static void createIssue(GeneralIssueDTO issue) {
		
	}
	
	private static void modifyIssue(GeneralIssueDTO issue) {
		
	}

}
