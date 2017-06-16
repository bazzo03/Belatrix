package com.belatrix.legal.mailintegrator.factory;

import org.junit.Assert;
import org.junit.Test;

import com.belatrix.legal.mailintegrator.dto.MailDTO;

public class MailFactoryTest {

	
	private MailDTO dto = new MailDTO();
	
	@Test
	public void processMailCreateJiraIssue() {
		
		dto.setSubject("NDA something");
		dto.setDescription("TestMessage");
		dto.setEmail("testEmail@email.com");
		dto.setIssueId("324");
		dto.setProject("N/A");
		dto.setTransactionId("3329-3294-6574-8932");
		System.out.println(MailFactory.processMail(dto));
		Assert.assertNotNull(MailFactory.processMail(dto));
	}
	
}
