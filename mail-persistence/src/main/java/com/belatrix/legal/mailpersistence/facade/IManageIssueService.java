package com.belatrix.legal.mailpersistence.facade;

import java.util.List;

import com.belatrix.legal.mailpersistence.dto.IssueDTO;
import com.belatrix.legal.mailpersistence.exception.MailPersistenceServiceException;

public interface IManageIssueService {

	void createIssue(IssueDTO issue) throws MailPersistenceServiceException;

	IssueDTO getIssueByTicketKey(String ticket) throws MailPersistenceServiceException;

	List<IssueDTO> getAllIssues() throws MailPersistenceServiceException;

	void deleteIssueByTicketKey(String ticket) throws MailPersistenceServiceException;

	void updateIssueByTicketKey(String ticket, IssueDTO issue) throws MailPersistenceServiceException;

}
