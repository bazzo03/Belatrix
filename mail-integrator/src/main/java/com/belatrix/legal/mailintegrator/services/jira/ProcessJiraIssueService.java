package com.belatrix.legal.mailintegrator.services.jira;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.GeneralIssueDTO;
import com.belatrix.legal.mailintegrator.dto.JiraIssueDTO;
import com.google.gson.Gson;

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
		
		Gson gson = new Gson();
		
		JiraIssueDTO dto = new JiraIssueDTO();
		dto.setDescription(issue.getDescription());
		dto.setName(issue.getIssueType());
		dto.setProject("LEG");
		dto.setSummary(issue.getDescription());
		
		String json = gson.toJson(dto);
		
		
		
	}
	
	private static void modifyIssue(GeneralIssueDTO issue) {
		
	}

}
