package com.belatrix.legal.mailintegrator.services.trello;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.MailDTO;

public class ProcessSalesforceIssueService {

	public static void processIssue(MailDTO issue, EIssueProcess process) {

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

	private static void createIssue(MailDTO issue) {

	}

	private static void modifyIssue(MailDTO issue) {

	}

}
