package com.belatrix.legal.restclient.test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.belatrix.legal.restclient.client.ClientHttp;

public class TestClientHTTP {
	
	public static void main (String args[]){
		
		String URL ="https://jira.belatrixsf.com/rest/api/2/issue/";
		
		String json="{\"fields\":{\"project\":{\"key\":\"LEG\"},\"summary\":\"REST Test client\",\"description\": \"TEST API HANDLER \",\"issuetype\":{\"name\":\"Bug\"}}}";
		
		Base64.getEncoder().encode("vzverev:Gromozavr".getBytes());

		String credentials =  new String(Base64.getEncoder().encode("legalrequests:lr123456".getBytes()), StandardCharsets.UTF_8); 
		
		System.out.println(credentials);
		

		String response = ClientHttp.httpRest(URL, json,"POST", credentials);
		
		System.out.println(response);
	}

}
