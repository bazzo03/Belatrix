package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.belatrix.legal.mailpersistence.dto.IssueDTO;
import com.belatrix.legal.mailpersistence.exception.MailPersistenceServiceException;
import com.belatrix.legal.mailpersistence.facade.IManageIssueService;
import com.belatrix.legal.mailpersistence.facade.ManageIssueService;

public class Main {

	public static void main(String[] args) throws MailPersistenceServiceException {
		// TODO Auto-generated method stub

		IManageIssueService service = new ManageIssueService();
		
		IssueDTO dto = new IssueDTO();
		
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = now.format(formatter);

        dto.setId(UUID.randomUUID().toString());
        dto.setKey("s346sq");
        dto.setEmail("dbazzo03@gmail.com");
        dto.setDate(formatDateTime);
        
//        connector.createIssue(dto);
        
        IssueDTO dto2 = new IssueDTO();
        dto2.setDate(dto.getDate());
        dto2.setKey(dto.getKey());
        dto2.setEmail("bazzo03@hotmail.com");
        
        System.out.println(service.getAllIssues().get(0).getEmail());
//        service.updateIssueByTicketKey(dto.getKey(), dto2);
        
	}

}
