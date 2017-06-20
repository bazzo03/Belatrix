package com.belatrix.legal.apihandler.process.jira;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.constants.ETypeProcess;
import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class AttachmentIssue extends ProcessHandler  {

	public AttachmentIssue( String transactionId) {
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
		return null;
	}
	
	@Override
	protected String getOperationName() {
		return ETypeProcess.ATTACHMENT_ISSUE.getValue();
	}
	
	@Override
	public String doProcess(String content) throws JSONException, IOException {
		return  process(content, true, true);
	}
	
	@Override
	protected String getResponse(String result) throws JSONException {
		String key="";
		if(result != null){
		logger.info(result.trim());
		JSONArray jsonContet =  new JSONArray(result);
	//	key=jsonContet.getInt("key").toString();
		}
		return key;
	}

}
