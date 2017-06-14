package com.belatrix.legal.mailpersistence.facade;

import java.util.List;

import com.belatrix.legal.mailpersistence.dto.IssueDTO;
import com.belatrix.legal.mailpersistence.exception.MailPersistenceServiceException;
import com.belatrix.legal.mailpersistence.services.IssueService;

public class ManageIssueService implements IManageIssueService {

	public void createIssue(IssueDTO issue) throws MailPersistenceServiceException {

		IssueService.createIssue(issue);
	}

	public IssueDTO getIssueByTicketKey(String ticket) throws MailPersistenceServiceException {

		return IssueService.getIssueByTicketKey(ticket);
	}

	public List<IssueDTO> getAllIssues() throws MailPersistenceServiceException {

		return IssueService.getAllIssues();
	}

	public void deleteIssueByTicketKey(String ticket) throws MailPersistenceServiceException {

		IssueService.deleteIssueByTicketKey(ticket);
	}

	public void updateIssueByTicketKey(String ticket, IssueDTO issue) throws MailPersistenceServiceException {
		
		IssueService.updateIssueByTicketKey(ticket, issue);
	}

}
