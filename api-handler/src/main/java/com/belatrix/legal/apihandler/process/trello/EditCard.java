package com.belatrix.legal.apihandler.process.trello;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.belatrix.legal.apihandler.constants.ETypeProcess;
import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.apihandler.process.ProcessHandler;

public class EditCard extends ProcessHandler {

	public EditCard( String transactionId) {
		super(ETypeProcess.TRELLO.getProcess(), transactionId);
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
	
	@Override
	protected String getOperationName() {
		return ETypeProcess.EDIT_CARD.getValue();
	}
	
	@Override
	public String doProcess(String content) throws JSONException, IOException {
		return  process(content, false, false);
	}


}
