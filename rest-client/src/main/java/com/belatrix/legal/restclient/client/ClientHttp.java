package com.belatrix.legal.restclient.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class contains RESTful Java client with Java build-in HTTP client library
 * @author cneira
 *
 */
public class ClientHttp {
	
	/**
	 * Method sends "POST" request to URL  with its json params.
	 * @param url
	 * @param json
	 * @param credentials
	 * @return
	 */

	private  static String postClientHtttp(String url, String json, String credentials) {

		StringBuffer response = new StringBuffer();
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			if(credentials != null){
			con.setRequestProperty("Authorization", "Basic " + credentials);
			}
			con.setRequestProperty("Content-Type", "application/json");
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
		
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return response.toString();
	}
	
	
	/**
	 * Method receives the url and json params to send request HTTP.
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postHttpRest(String url, String json){
		return  postClientHtttp( url, json, null);
	}
	
	
	/**
	 * Method receives the url and json params to send request HTTP with auth credentials.
	 * @param url
	 * @param json
	 * @param credentials
	 * @return
	 */
	public static String postHttpRestAuth(String url, String json, String credentials){
		return  postClientHtttp( url, json, credentials);
	}
	

}
