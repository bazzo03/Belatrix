package com.belatrix.legal.mailpersistence.services;

import java.util.List;

import com.belatrix.legal.mailpersistence.dao.IIssueDAO;
import com.belatrix.legal.mailpersistence.dao.IssueDAO;
import com.belatrix.legal.mailpersistence.dto.IssueDTO;
import com.belatrix.legal.mailpersistence.exception.MailPersistenceServiceException;

public class IssueService {
	
	private static IIssueDAO dao = new IssueDAO();

	public static void createIssue(IssueDTO issue) throws MailPersistenceServiceException {
		
		dao.createIssue(issue);
	}
	
	public static IssueDTO getIssueByTicketKey(String ticket) throws MailPersistenceServiceException {
		
		return dao.getIssueByTicketKey(ticket);
	}
	
	public static List<IssueDTO> getAllIssues() throws MailPersistenceServiceException {
		
		return dao.getAllIssues();
	}
	
	public static void deleteIssueByTicketKey(String ticket) throws MailPersistenceServiceException {
		
		dao.deleteIssueByTicketKey(ticket);
	}
	
	public static void updateIssueByTicketKey(String ticket, IssueDTO issue) throws MailPersistenceServiceException {
		
		dao.updateIssueByTicketKey(ticket, issue);
	}
	
}
