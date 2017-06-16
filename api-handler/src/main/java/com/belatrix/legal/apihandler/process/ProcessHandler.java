package com.belatrix.legal.apihandler.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.belatrix.legal.apihandler.config.EProperty;
import com.belatrix.legal.apihandler.config.LoadConfig;
import com.belatrix.legal.apihandler.constants.ETypeProcess;
import com.belatrix.legal.apihandler.dto.AppProcessDTO;
import com.belatrix.legal.apihandler.dto.OperationDTO;
import com.belatrix.legal.restclient.client.ClientHttp;

public abstract class ProcessHandler {

	/**
	 * log
	 */
	protected final static Logger logger = Logger.getLogger(ProcessHandler.class);

	private String processName;
	private String transactionId;
	private static Document xmlFile;

	protected static ConcurrentHashMap<String, AppProcessDTO> configProcess;

	static {
		try {
			getConfigProcess();
		} catch (Exception e) {
			logger.debug("Error loading config from Xml File handlerOperation.xml");
		}
	}

	public ProcessHandler(String processName, String transactionId) {
		this.processName = processName;
		this.transactionId = transactionId;
		logger.info(String.format("Init Proccess handler for %s . TxId : %s", this.processName, this.transactionId));

	}

	private static ConcurrentHashMap<String, AppProcessDTO> getConfigProcess()
			throws ParserConfigurationException, SAXException, IOException {
		if (configProcess == null) {
			configProcess = new ConcurrentHashMap<String, AppProcessDTO>();
			logger.trace("Init Load config from Xml File handlerOperation.xml");
			for (ETypeProcess enumProcess : ETypeProcess.values()) {
				AppProcessDTO process = new AppProcessDTO();
				process.setName(enumProcess.getNameProperty());
				process.setUrl(LoadConfig.getInstance().getProperty(
						EProperty.URL.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setCredentials(LoadConfig.getInstance().getProperty(
						EProperty.CREDENTIALS.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setToken(LoadConfig.getInstance().getProperty(
						EProperty.TOKEN.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setKey(LoadConfig.getInstance().getProperty(
						EProperty.KEY.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setOperations(getXmlOperationsMap(enumProcess.getNameProperty(), process));
				configProcess.put(enumProcess.getNameProperty(), process);
			}

		}
		logger.trace("Succes load configProcess Map.");
		return configProcess;
	}

	private static Map<String, OperationDTO> getXmlOperationsMap(String appName, AppProcessDTO process)
			throws ParserConfigurationException, SAXException, IOException {

		Map<String, OperationDTO> mapService = new HashMap<String, OperationDTO>();
		getXmlFile();

		xmlFile.getDocumentElement().normalize();

		NodeList nList = xmlFile.getElementsByTagName("app");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				if (appName.equals(nNode.getAttributes().getNamedItem("name").getNodeValue().toString())) {

					NodeList operationList = nNode.getChildNodes();

					for (int temp2 = 0; temp2 < operationList.getLength(); temp2++) {

						Node nNode2 = operationList.item(temp2);
						if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
							OperationDTO operation = new OperationDTO();
							operation.setName(nNode2.getAttributes().getNamedItem("name").getNodeValue().toString());
							operation.setType(nNode2.getAttributes().getNamedItem("type").getNodeValue().toString());
							operation.setPath(nNode2.getAttributes().getNamedItem("path").getNodeValue().toString());

							operation.setUrl(getUrl(process, operation));

							for (int temp3 = 0; temp3 < nNode2.getChildNodes().getLength(); temp3++) {

								Node nNode3 = nNode2.getChildNodes().item(temp3);
								if (nNode3.getNodeType() == Node.CDATA_SECTION_NODE) {

									CharacterData child = (CharacterData) nNode3;
									if (child.getData() != null && !child.getData().isEmpty()) {
										operation.setJson(child.getData());

									}

								}
							}

							mapService.put(operation.getName(), operation);
						}
					}
				}
			}

		}

		return mapService;

	}

	private static String getUrl(AppProcessDTO process, OperationDTO operation) {
		StringBuilder url = new StringBuilder(process.getUrl());
		url.append(operation.getPath());

		if (process.getKey() != null && !process.getKey().isEmpty()) {
			url.append("?key=" + process.getKey());
		}
		if (process.getToken() != null && !process.getToken().isEmpty()) {
			url.append("&token=" + process.getToken());
		}
		return url.toString();
	}

	private static void getXmlFile() throws ParserConfigurationException, SAXException, IOException {

		if (xmlFile == null) {
			File fXmlFile = new File("handlerOperation.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlFile = dBuilder.parse(fXmlFile);

		}

	}

	/**
	 * 
	 * @param content
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	public String processCredentials(String content, String operation) throws JSONException, IOException {
		return process(content, operation, true, false);
	}

	/**
	 * 
	 * @param content
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	public String process(String content, String operation) throws JSONException, IOException {
		return process(content, operation, false, false);
	}

	/**
	 * 
	 * @param content
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	public String processAttachment(String content, String operation) throws JSONException, IOException {
		return process(content, operation, false, true);
	}

	private String process(String content, String operation, Boolean isAuth, Boolean isAttachment) throws JSONException, IOException {

		String result = "";
		AppProcessDTO process = configProcess.get(this.processName);
		OperationDTO operationConfig = process.getOperations().get(operation);
		String json = getJson(content, operationConfig);
		String url = this.getUrl(operationConfig.getUrl(),content);
		if (isAuth) {
			logger.info(String.format("Send Http request with credentials for %s . TxId : %s", this.processName,
					this.transactionId));
		
			result = ClientHttp.httpRest(url, json, operationConfig.getType(),
					process.getCredentials());
		} else if(isAttachment) {	
			FileBody file = getFileBody( content);
			result = ClientHttp.httpRest(url, operationConfig.getType(),
					process.getCredentials(),file);
			
			
		}else {
		
			logger.info(String.format("Send Http request for %s . TxId : %s", this.processName, this.transactionId));
			result = ClientHttp.httpRest(url, json, operationConfig.getType());
		}
		
		return this.getResponse(result);
	}

	/**
	 * @param content
	 * @return
	 * @throws JSONException
	 */
	protected abstract String getJson(String content, OperationDTO operationConfig) throws JSONException;

	/**
	 * @param result
	 * @return
	 * @throws JSONException
	 */
	protected String getResponse(String result) throws JSONException {
		String key = "";
		if (result != null) {
			logger.trace(result.trim());
			System.out.println(result.trim());
			JSONObject jsonContet = new JSONObject(result);
			key = jsonContet.get("id").toString();
		}
		return key;
	}

	/**
	 * @param result
	 * @return
	 * @throws JSONException
	 */
	protected String getUrl(String url, String content) throws JSONException {
		return url.trim();
	}
	
	
	/**
	 * @param result
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	protected FileBody getFileBody(String content) throws JSONException, IOException {
		

		JSONObject jsonContet =  new JSONObject(content);
		String file =jsonContet.get("file").toString();
		String name =jsonContet.get("name").toString();
		byte[] decodedBytes = Base64.getDecoder().decode(file);
		File file64 = new File(name);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file64));
        writer.write(decodedBytes);
        writer.flush();
        writer.close();
        FileBody fileBody = new FileBody(file64);
        return fileBody;
	}

}
