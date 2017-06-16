
package com.belatrix.legal.apihandler.process.jira;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class CreateIssue extends ProcessHandler {

	public CreateIssue(String processName, String transactionId) {
		super(processName, transactionId);
		
	}

	@Override
	protected String getJson(String content, OperationDTO operationConfig) throws JSONException {
		String json = "";
		JSONObject jsonContet =  new JSONObject(content);
		
		String project =jsonContet.get("project").toString();
		String summary =jsonContet.get("summary").toString();
		String description =jsonContet.get("description").toString();
		String name =jsonContet.get("name").toString();
		json = String.format(operationConfig.getJson(), project,summary,description,name );
		logger.trace(json.trim());
		return json.trim();
	}

	@Override
	protected String getResponse(String result) throws JSONException {
		String key="";
		if(result != null){
		logger.trace(result.trim());
		JSONObject jsonContet =  new JSONObject(result);
		key=jsonContet.get("key").toString();
		}
		return key;
	}
	
	

}
