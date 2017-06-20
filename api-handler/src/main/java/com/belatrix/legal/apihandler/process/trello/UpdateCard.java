package com.belatrix.legal.apihandler.process.trello;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class UpdateCard extends ProcessHandler {

	public UpdateCard(String processName, String transactionId) {
		super(processName, transactionId);
	}
	
	@Override
	protected String getUrl(String url, String content) throws JSONException {
		String parserUrl = "";
		JSONObject jsonContet =  new JSONObject(content);
		String card =jsonContet.get("project").toString();
		parserUrl= String.format(url, card);
		return parserUrl;
	}

	@Override
	protected String getJson(String content, OperationDTO operationConfig) throws JSONException {
		String json = "";
		JSONObject jsonContet =  new JSONObject(content);
		String description =jsonContet.get("description").toString();
		String name  =jsonContet.get("summary").toString();
		json = String.format(operationConfig.getJson(),name,description );
		logger.trace(json.trim());
		return json.trim();
	}


}
