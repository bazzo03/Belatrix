package com.belatrix.legal.apihandler.test.process;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.junit.Test;

import com.belatrix.legal.apihandler.exception.ApiHandlerException;
import com.belatrix.legal.apihandler.facade.ApiHandler;
import com.belatrix.legal.apihandler.facade.IApiHandler;

public class JiraOperationTest {
	
	private IApiHandler facadeHandler = new ApiHandler();
	
	@Test
	public void createIssueTest() throws ApiHandlerException{
		String transactionId = UUID.randomUUID().toString();
		String content = "{\"project\":\"LEG\", \"summary\":\"REST Test client\", \"description\":\"Test api handler \", \"name\":\"Bug\"}";
		facadeHandler.createIssue(content, transactionId);
		
	}
	
	@Test
	public void AddCommentIssueTest() throws ApiHandlerException{
		String transactionId = UUID.randomUUID().toString();
		String content = "{\"project\":\"LEG-3\", \"text\":\"Comentario de prueba api handler\"}";
		facadeHandler.addCommentIssue(content, transactionId);
		
	}
	
	@Test
	public void AttachmentIssueTest() throws ApiHandlerException, IOException{
		String transactionId = UUID.randomUUID().toString();
		String fileName = "C:\\Users\\cneira\\Desktop\\trello-mark-blue.png";
		 File file = new File(fileName);
	        int length = (int) file.length();
	        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
	        byte[] bytes = new byte[length];
	        reader.read(bytes, 0, length);
	        reader.close();
	        byte[] base64EncodedData = Base64.getEncoder().encode(bytes);
	        System.out.println(base64EncodedData);
		String content = "{\"project\":\"LEG-3\",\"file\":\""+ new String (base64EncodedData)+"\",\"name\":\"trello-mark-blue.png\"}";
		facadeHandler.attachmentIssue(content, transactionId);
		
	}

}
