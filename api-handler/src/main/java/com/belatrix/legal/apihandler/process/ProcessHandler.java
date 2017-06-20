package com.belatrix.legal.apihandler.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumSet;
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
			EnumSet.allOf(ETypeProcess.class).forEach(x -> processEnum(x));
		}
		logger.trace("Succes load configProcess Map.");
		return configProcess;
	}

	private static void processEnum(ETypeProcess processEnum) {
		String processName = processEnum.getProcess();
		AppProcessDTO process = new AppProcessDTO();
		process.setName(processName);
		process.setUrl(
				LoadConfig.getInstance().getProperty(EProperty.URL.getNameProperty().replaceAll("#app#", processName)));
		process.setCredentials(LoadConfig.getInstance()
				.getProperty(EProperty.CREDENTIALS.getNameProperty().replaceAll("#app#", processName)));
		process.setToken(LoadConfig.getInstance()
				.getProperty(EProperty.TOKEN.getNameProperty().replaceAll("#app#", processName)));
		process.setKey(
				LoadConfig.getInstance().getProperty(EProperty.KEY.getNameProperty().replaceAll("#app#", processName)));
		process.setOperations(getXmlOperationsMap(processName, process));
		configProcess.put(processName, process);
	}

	private static Map<String, OperationDTO> getXmlOperationsMap(String appName, AppProcessDTO process) {

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

	private static void getXmlFile() {

		if (xmlFile == null) {
			File fXmlFile = new File("handlerOperation.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				xmlFile = dBuilder.parse(fXmlFile);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error("Error loading config from Xml File handlerOperation.xml",e);
			}

		}

	}

	/**
	 * This method receive configuration's params HTTP
	 * 
	 * @param content
	 *            Json with process information
	 * @param isAuth
	 *            isAuth Is true if the HTTP petition has credentials
	 * @param isAttachment
	 *            Is true if the HTTP petition has a file to attachment the
	 *            server.
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	protected String process(String content, Boolean isAuth, Boolean isAttachment) throws JSONException, IOException {

		String result = "";
		AppProcessDTO process = configProcess.get(this.processName);
		OperationDTO operationConfig = process.getOperations().get(getOperationName());
		String json = getJson(content, operationConfig);
		String url = this.getUrl(operationConfig.getUrl(), content);
		String credentials = null;
		FileBody file = null;

		if (isAuth) {
			credentials = process.getCredentials();
		}
		if (isAttachment) {
			file = getFileBody(content);
		}
		logger.info(String.format("Send Http request for %s . TxId : %s", this.processName, this.transactionId));
		result = ClientHttp.httpRest(url, json, operationConfig.getType(), credentials, file);

		return this.getResponse(result);
	}

	/**
	 * @param content
	 * @return
	 * @throws JSONException
	 */
	protected abstract String getJson(String content, OperationDTO operationConfig) throws JSONException;

	/**
	 * @param content
	 * @return
	 * @throws JSONException
	 */
	protected abstract String getOperationName();

	/**
	 * @param content
	 * @return
	 * @throws JSONException
	 */
	public abstract String doProcess(String content) throws JSONException, IOException;

	/**
	 * @param result
	 * @return
	 * @throws JSONException
	 */
	protected String getResponse(String result) throws JSONException {
		String key = "";
		if (result != null) {
			logger.info(result.trim());
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

		JSONObject jsonContet = new JSONObject(content);
		String file = jsonContet.get("file").toString();
		String name = jsonContet.get("name").toString();
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
