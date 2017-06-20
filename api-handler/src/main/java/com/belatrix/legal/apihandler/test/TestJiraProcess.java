package com.belatrix.legal.apihandler.test;

import org.json.JSONException;

import com.belatrix.legal.apihandler.process.jira.AddCommentIssue;
import com.belatrix.legal.apihandler.process.jira.CreateIssue;

public class TestJiraProcess {

	public static void main(String args[]) {
		//testCreateIssue();
		testAddCommentIssue();
	}

	private static void testCreateIssue() {
		CreateIssue issue = new CreateIssue("jira", "test1");

		String content = "{\"project\":\"LEG\", \"summary\":\"REST Test client\", \"description\":\"Test api handler \", \"name\":\"Bug\"}";

		String response;
		try {
			response = issue.processCredentials(content, "CreateIssue");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testAddCommentIssue() {
		AddCommentIssue issue = new AddCommentIssue("jira", "test1");

		String content = "{\"project\":\"LEG-3\", \"text\":\"Comentario de prueba api handler\"}";

		String response;
		try {
			response = issue.processCredentials(content, "AddCommentIssue");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
