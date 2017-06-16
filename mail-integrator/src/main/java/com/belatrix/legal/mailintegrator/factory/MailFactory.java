package com.belatrix.legal.mailintegrator.factory;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailintegrator.config.EProperty;
import com.belatrix.legal.mailintegrator.config.LoadConfig;
import com.belatrix.legal.mailintegrator.dto.MailDTO;
import com.belatrix.legal.mailintegrator.facade.IProcessIssueService;
import com.belatrix.legal.mailintegrator.facade.ProcessIssueService;

public class MailFactory {

	private final static Logger LOGGER = Logger.getLogger(MailFactory.class);
	
	private static final String ONE = "1";

	/*
	 * private static final String JIRA = LoadFactoryConfig.getInstance()
	 * .getProperty(EPropertyFactory.JIRA.getNameProperty()); private static
	 * final String TRELLO = LoadFactoryConfig.getInstance()
	 * .getProperty(EPropertyFactory.TRELLO.getNameProperty()); private static
	 * final String SALESFORCE = LoadFactoryConfig.getInstance()
	 * .getProperty(EPropertyFactory.SALESFORCE.getNameProperty());
	 */

	private static IProcessIssueService service = new ProcessIssueService();

	/*
	 * public static void processMail(MailDTO issue) {
	 * 
	 * if (JIRA.equals(ONE)) { //TODO call jira integrator and send general dto
	 * -> businessFacade.processJiraIssue(issue) service.processIssueJira(issue,
	 * EIssueProcess.CREATION); } if (TRELLO.equals(ONE)) { //TODO call trello
	 * integrator and send general dto ->
	 * businessFacade.processTrelloIssue(issue)
	 * service.processIssueTrello(issue, EIssueProcess.CREATION); } if
	 * (SALESFORCE.equals(ONE)) { //TODO call salesforce integrator and send
	 * general dto -> businessFacade.processSalesForceIssue(issue)
	 * service.processIssueSalesForce(issue, EIssueProcess.CREATION); } }
	 */

	public static String processMail(MailDTO dto) {

		StringBuilder result = new StringBuilder();
		
		String keywords = LoadConfig.getInstance().getProperty(EProperty.PROPERTY_KEY_LIST.getNameProperty());

		String[] keywordsArray = keywords.split(",");

		for (String keyword : keywordsArray) {

			if (dto.getSubject().contains(keyword)) {
				
				String keyType = LoadConfig.getInstance()
						.getProperty(EProperty.PROPERTY_KEY_TYPE.getNameProperty().replaceAll("#key#", keyword));
				String app = LoadConfig.getInstance().getProperty(
						EProperty.PROPERTY_KEY_FORM.getNameProperty().replaceAll("#key#", keyword) + keyType);
				
				String[] apps = app.split(",");
				String[] tasks = new String[apps.length];

				if (dto.getSubject().contains("LEG")) {
					// edicion
					int i = 0;
					for (String application : apps) {
						result.append(" Edicion - apps:" + application);
						tasks[i] = LoadConfig.getInstance()
								.getProperty(EProperty.OPERATIONS_APP_UPDATE.getNameProperty().replaceAll("#app#", application));
						i++;
					}
					
					result.append("Edicion - keyword:" + keyword);
					
				} else {
					// creacion
					int i = 0;
					for (String application : apps) {
						result.append(" Creacion - apps:" + application);
						tasks[i] = LoadConfig.getInstance()
								.getProperty(EProperty.OPERATIONS_APP_CREATE.getNameProperty().replaceAll("#app#", application));
						i++;
					}
					
					result.append(" Creacion - keyword:" + keyword);
				}
				
				operateTasks(tasks);
				
				if (dto.toString() == "Tengo adjuntos") {
					//TODO preguntarAdjuntos
				}
			}
		}
		
		return result.toString();
	}

	private static void operateTasks(String ...tasks) {

		for (String task : tasks) {
			//TODO llamar a la fachada del handler
		}
	}

}
