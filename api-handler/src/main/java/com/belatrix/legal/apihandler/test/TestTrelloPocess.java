package com.belatrix.legal.apihandler.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONException;

import com.belatrix.legal.apihandler.process.trello.AddCommentCard;
import com.belatrix.legal.apihandler.process.trello.AttachmentCard;
import com.belatrix.legal.apihandler.process.trello.CreateCard;
import com.belatrix.legal.apihandler.process.trello.UpdateCard;

public class TestTrelloPocess {

	public static void main(String args[]) {
		// testCreateCard();
		// testUpdateCard();
		// testAddCommentCard();
		// testAttachmentCardUrl();
		testAttachmentCardFile();
	}

	private static void testCreateCard() {
		CreateCard issue = new CreateCard("trello", "test1trello1");

		String content = "{\"project\":\"LEG\", \"summary\":\"REST Test client\", \"description\":\"Test api handler \", \"name\":\"Bug\"}";

		String response;
		try {
			response = issue.process(content, "CreateCard");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testUpdateCard() {

		UpdateCard issue = new UpdateCard("trello", "test1trello1");
		String content = "{\"project\":\"5943f52e150386b671cd96a1\", \"summary\":\"REST Test client Update\", \"description\":\"Test api handler Update \", \"name\":\"Bug\"}";
		String response;
		try {
			response = issue.process(content, "UpdateCard");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testAddCommentCard() {

		AddCommentCard issue = new AddCommentCard("trello", "test1trello1");
		String content = "{\"project\":\"5943f52e150386b671cd96a1\", \"text\":\"Es un comentario de prueba desde api handler.\"}";
		String response;
		try {
			response = issue.process(content, "AddCommentCard");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testAttachmentCardUrl() {

		AttachmentCard issue = new AttachmentCard("trello", "test1trello1");
		String content = "{\"project\":\"5943f52e150386b671cd96a1\", \"url\":\"https://d2k1ftgv7pobq7.cloudfront.net/meta/u/res/images/b428584f224c42e98d158dad366351b0/trello-mark-blue.png\",\"file\":\"\",\"name\":\"IconPrueba\"}";
		String response;
		try {
			response = issue.process(content, "AddCommentCard");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testAttachmentCardFile() {

		AttachmentCard issue = new AttachmentCard("trello", "test1trello1");
		//File txt = new File("C:\\Users\\cneira\\Desktop\\aaa.txt");
		
		try {
			//file
			//String content = "{\"project\":\"5943f52e150386b671cd96a1\", \"url\":\"\",\"file\":\"PSBJbnRlZ3JhdGlvbiBHaXRMYWIgYW5kIEJpdEJ1Y2tldCBSZXBvc2l0b3JpZXMNCg==\",\"name\":\"IconPrueba.txt\"}";
			//image
			String fileName = "C:\\Users\\cneira\\Desktop\\trello-mark-blue.png";
			 File file = new File(fileName);
		        int length = (int) file.length();
		        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		        byte[] bytes = new byte[length];
		        reader.read(bytes, 0, length);
		        reader.close();
		        byte[] base64EncodedData = Base64.getEncoder().encode(bytes);
		        System.out.println(base64EncodedData);
			String content = "{\"project\":\"5943f52e150386b671cd96a1\", \"url\":\"\",\"file\":\""+ new String (base64EncodedData)+"\",\"name\":\"trello-mark-blue.png\"}";
		
			String response;
			response = issue.processAttachment(content, "AttachmentCard");
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
