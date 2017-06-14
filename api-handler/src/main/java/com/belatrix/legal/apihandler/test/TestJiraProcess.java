package com.belatrix.legal.apihandler.test;

import org.json.JSONException;

import com.belatrix.legal.apihandler.process.jira.CreateIssue;

public class TestJiraProcess {
	
	public static void main(String args[]){
		
		CreateIssue issue = new CreateIssue("jira","test1");
		
		String content = "{\"project\":\"LEG\", \"summary\":\"REST Test client\", \"description\":\"Test api handler \", \"name\":\"Bug\"}";
		
		String response;
		try {
			response = issue.processCredentials(content, "CreateIssue");
			System.out.println(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
