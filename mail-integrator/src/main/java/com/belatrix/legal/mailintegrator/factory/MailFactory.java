package com.belatrix.legal.mailintegrator.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailintegrator.config.EProperty;
import com.belatrix.legal.mailintegrator.config.LoadConfig;
import com.belatrix.legal.mailintegrator.dto.MailDTO;
import com.belatrix.legal.mailintegrator.facade.IProcessIssueService;
import com.belatrix.legal.mailintegrator.facade.ProcessIssueService;
import com.belatrix.legal.mailintegrator.reflection.ReflectionApiHandler;

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

	public static void processMail(MailDTO dto) throws Exception {

		String keywords = LoadConfig.getInstance().getProperty(EProperty.PROPERTY_KEY_LIST.getNameProperty());

		String[] keywordsArray = keywords.split(",");

		for (String keyword : keywordsArray) {

			if (dto.getSubject().contains(keyword)) {

				String keyType = LoadConfig.getInstance()
						.getProperty(EProperty.PROPERTY_KEY_TYPE.getNameProperty().replaceAll("#key#", keyword));
				String app = LoadConfig.getInstance().getProperty(
						EProperty.PROPERTY_KEY_FORM.getNameProperty().replaceAll("#key#", keyword) + keyType);

				String[] apps = app.split(",");
				List<String> tasks = new ArrayList<>();
				int i = 0;
				for (String application : apps) {

					if (dto.getSubject().contains("LEG")) {
						// edicion
					

					//	System.out.println(" Edicion - apps:" + application);
						tasks.add(LoadConfig.getInstance().getProperty(
								EProperty.OPERATIONS_APP_UPDATE.getNameProperty().replaceAll("#app#", application)));
					//	System.out.println(" Edicion - tasks: " + tasks.get(i));
					

						System.out.println("Edicion - keyword:" + keyword);

					} else {
						// creacion
						

					//	System.out.println(" Creacion - apps:" + application);
						tasks.add(LoadConfig.getInstance().getProperty(
								EProperty.OPERATIONS_APP_CREATE.getNameProperty().replaceAll("#app#", application)));
					//	System.out.println(" Edicion - tasks: " + tasks.get(i));
					

					//	System.out.println(" Creacion - keyword:" + keyword);
					}

					if (dto.toString() == "Tengo adjuntos") {
						tasks.add(LoadConfig.getInstance().getProperty(
								EProperty.OPERATIONS_APP_ATTACHMENT.getNameProperty().replaceAll("#app#", application)));
					}
					
					i++;

				}

				operateTasks(tasks, dto);

			}
		}

	}

	private static void operateTasks(List<String> tasks, MailDTO dto) throws Exception {

		for (String task : tasks) {
			System.out.println(" Edicion - tasks: " + task);
			ReflectionApiHandler.execute("{\"project\":\"LEG\", \"summary\":\"REST Test client\", \"description\":\"Test api handler \", \"name\":\"Bug\"}", dto.getTransactionId(), task);
		}
	}

}
