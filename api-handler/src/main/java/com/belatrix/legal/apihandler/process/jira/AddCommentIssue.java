package com.belatrix.legal.apihandler.process.jira;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.constants.ETypeProcess;
import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class AddCommentIssue extends ProcessHandler {

	public AddCommentIssue(String transactionId) {
		super(ETypeProcess.JIRA.getProcess(), transactionId);
		
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
		String json = "";
		JSONObject jsonContet =  new JSONObject(content);
		String text =jsonContet.get("text").toString();
		json = String.format(operationConfig.getJson(), text );
		logger.trace(json.trim());
		return json.trim();
	}

	@Override
	protected String getOperationName() {
		return ETypeProcess.ADD_COMMENT_ISSUE.getValue();
	}

	@Override
	public String doProcess(String content) throws JSONException, IOException {
		return  process(content, true, false);
	}
	
}
