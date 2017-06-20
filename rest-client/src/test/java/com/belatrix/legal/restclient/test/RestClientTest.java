package com.belatrix.legal.restclient.test;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.entity.mime.content.FileBody;
import org.junit.Test;

import com.belatrix.legal.restclient.client.ClientHttp;

public class RestClientTest {

	private String URL = "https://jira.belatrixsf.com/rest/api/2/issue/";

	@Test
	public void sendHttpRequestTest() {

		String json = "{\"fields\":{\"project\":{\"key\":\"LEG\"},\"summary\":\"REST Test client\",\"description\": \"TEST API HANDLER \",\"issuetype\":{\"name\":\"Bug\"}}}";
		Base64.getEncoder().encode("vzverev:Gromozavr".getBytes());
		String credentials = new String(Base64.getEncoder().encode("legalrequests:lr123456".getBytes()),
				StandardCharsets.UTF_8);
		FileBody fileBody = null;
		System.out.println(credentials);
		String response = ClientHttp.httpRest(URL, json, "POST", credentials, fileBody);
		assertTrue(!response.isEmpty());
	}

}
