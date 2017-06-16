package com.belatrix.legal.apihandler.process.trello;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class CreateCard extends ProcessHandler {

	public CreateCard(String processName, String transactionId) {
		super(processName, transactionId);
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
