package com.belatrix.legal.restclient.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

/**
 * Class contains RESTful Java client with Java build-in HTTP client library
 * 
 * @author cneira
 *
 */
public class ClientHttp {

	/**
	 * Method sends "POST" request to URL with its json params.
	 * 
	 * @param url
	 * @param json
	 * @param credentials
	 * @return
	 */

	private static String sendHttp(String url, String json, String type, String credentials, FileBody fileBody) {

		StringBuffer response = new StringBuffer();
		try {

			URL obj = new URL(url);
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(type);
			con.setDoOutput(true);
			if (credentials != null) {
				con.setRequestProperty("Authorization", "Basic " + credentials);
			}
			if (fileBody != null) {
				multipartEntity.addPart("file", fileBody);
				con.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());

			} else {
				con.setRequestProperty("Content-Type", "application/json");
			}

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			if (fileBody != null) {
				multipartEntity.writeTo(wr);
			} else {
				wr.writeBytes(json);
			}
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
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String httpRest(String url, String json, String type) {
		return sendHttp(url, json, type, null, null);
	}

	/**
	 * Method receives the url and json params to send request HTTP with auth
	 * credentials.
	 * 
	 * @param url
	 * @param json
	 * @param credentials
	 * @return
	 */
	public static String httpRest(String url, String json, String type, String credentials) {
		return sendHttp(url, json, type, credentials, null);
	}

	public static String httpRest(String url, String type, String credentials, FileBody fileBody) {
		return sendHttp(url, null, type, credentials, fileBody);
	}

}
