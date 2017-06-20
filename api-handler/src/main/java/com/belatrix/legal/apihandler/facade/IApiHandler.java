package com.belatrix.legal.apihandler.facade;

import com.belatrix.legal.apihandler.exception.ApiHandlerException;

public interface IApiHandler {
	
	public String createIssue(String content,String transactionId) throws ApiHandlerException;
	
	public String addCommentIssue(String content,String transactionId) throws ApiHandlerException;
	
	public String attachmentIssue(String content,String transactionId) throws ApiHandlerException;
	
	public String createCard(String content,String transactionId) throws ApiHandlerException;
	
	public String editCard(String content,String transactionId) throws ApiHandlerException;
	
	public String addCommentCard(String content,String transactionId) throws ApiHandlerException;
	
	public String attachmentCard(String content,String transactionId)throws ApiHandlerException;

}
