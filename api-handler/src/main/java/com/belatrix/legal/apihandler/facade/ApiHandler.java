package com.belatrix.legal.apihandler.facade;

import java.io.IOException;

import org.json.JSONException;

import com.belatrix.legal.apihandler.exception.ApiHandlerException;
import com.belatrix.legal.apihandler.process.jira.AddCommentIssue;
import com.belatrix.legal.apihandler.process.jira.AttachmentIssue;
import com.belatrix.legal.apihandler.process.jira.CreateIssue;
import com.belatrix.legal.apihandler.process.trello.AddCommentCard;
import com.belatrix.legal.apihandler.process.trello.AttachmentCard;
import com.belatrix.legal.apihandler.process.trello.CreateCard;
import com.belatrix.legal.apihandler.process.trello.EditCard;

public class ApiHandler implements IApiHandler {

	@Override
	public String createIssue(String content, String transactionId) throws ApiHandlerException {

		CreateIssue issue = new CreateIssue(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error CreateIssue ",e);
		}
		return response;
	}

	@Override
	public String addCommentIssue(String content, String transactionId) throws ApiHandlerException {
		AddCommentIssue issue = new AddCommentIssue(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error addCommentIssue ",e);
		}
		return response;
	}

	@Override
	public String attachmentIssue(String content, String transactionId) throws ApiHandlerException {
		AttachmentIssue issue = new AttachmentIssue(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error attachmentIssue ",e);
		}
		return response;
	}

	@Override
	public String createCard(String content, String transactionId) throws ApiHandlerException {
		CreateCard issue = new CreateCard(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error createCard ",e);
		}
		return response;
	}

	@Override
	public String editCard(String content, String transactionId) throws ApiHandlerException {
		EditCard issue = new EditCard(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error editCard ",e);
		}
		return response;
	}

	@Override
	public String addCommentCard(String content, String transactionId) throws ApiHandlerException {
		AddCommentCard issue = new AddCommentCard(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error addCommentCard ",e);
		}
		return response;
	}

	@Override
	public String attachmentCard(String content, String transactionId) throws ApiHandlerException {
		AttachmentCard issue = new AttachmentCard(transactionId);
		String response="";
		try {
		response=	issue.doProcess(content);
		} catch (JSONException | IOException e) {
			throw new ApiHandlerException("Error attachmentCard ",e);
		}
		return response;
	}
	
	
	
	

}
