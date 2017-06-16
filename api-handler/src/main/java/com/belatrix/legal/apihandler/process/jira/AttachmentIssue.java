package com.belatrix.legal.apihandler.process.jira;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class AttachmentIssue extends ProcessHandler  {

	public AttachmentIssue(String processName, String transactionId) {
		super(processName, transactionId);
	}
	
	@Override
	protected String getUrl(String url, String content) throws JSONException {
		String parserUrl = "";
		JSONObject jsonContet =  new JSONObject(content);
		String project =jsonContet.get("project").toString();
		parserUrl= String.format(url, project);
		return parserUrl;
	}

	@Override
	protected String getJson(String content, OperationDTO operationConfig) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
