package com.belatrix.legal.mailfinder.facade;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.belatrix.legal.mailintegrator.dto.GeneralIssueDTO;


public interface ICheckEmailService {

	Message[] fetchMessages(String host, String user, String password, boolean read) throws MessagingException;
	
	List<GeneralIssueDTO> createIssues(Message[] messages);
	
}
