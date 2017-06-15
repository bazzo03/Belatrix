package com.belatrix.legal.apihandler.process;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.json.JSONException;
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
				process.setUrl(LoadConfig.getInstance().getProperty( EProperty.URL.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setCredentials(LoadConfig.getInstance().getProperty(
						EProperty.CREDENTIALS.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setType(LoadConfig.getInstance().getProperty(
						EProperty.TYPE.getNameProperty().replaceAll("#app#", enumProcess.getNameProperty())));
				process.setOperations(getXmlOperationsMap(enumProcess.getNameProperty()));
				configProcess.put(enumProcess.getNameProperty(), process);
			}

		}
		logger.trace("Succes load configProcess Map.");
		return configProcess;
	}

	private static Map<String, OperationDTO> getXmlOperationsMap(String appName)
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
	 */
	public String processCredentials(String content, String operation) throws JSONException {
		return process(content, operation, true);
	}

	/**
	 * 
	 * @param content
	 * @return
	 * @throws JSONException 
	 */
	public String process(String content, String operation) throws JSONException {
		return process(content, operation, false);
	}

	private String process(String content, String operation, Boolean isAuth) throws JSONException {

		String result = "";
		AppProcessDTO process = configProcess.get(this.processName);
		OperationDTO operationConfig = process.getOperations().get(operation);
		String json = getJson(content, operationConfig);
		
		if (isAuth) {			
			logger.info(String.format("Send Http request with credentials for %s . TxId : %s", this.processName,
					this.transactionId));
			result = ClientHttp.postHttpRestAuth(process.getUrl(), json, process.getCredentials());
		} else {
			logger.info(String.format("Send Http request for %s . TxId : %s", this.processName, this.transactionId));
			result = ClientHttp.postHttpRestAuth(process.getUrl(), json, null);
		}
		return getResponse(result);
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
	protected abstract String getResponse(String result) throws JSONException;

}
