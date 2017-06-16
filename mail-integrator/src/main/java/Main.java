import com.belatrix.legal.mailintegrator.dto.MailDTO;
import com.belatrix.legal.mailintegrator.factory.MailFactory;

public class Main {

	private static MailDTO dto = new MailDTO();
	
	public static void main(String[] args) {
//		dto.setSubject("NDA LEG something");
		
//		dto.setSubject("ATLASSIAN_JIRA LEG something");
		
//		dto.setSubject("COM LEG something");
		
		dto.setSubject("something");
		
		dto.setDescription("TestMessage");
		dto.setEmail("testEmail@email.com");
		dto.setIssueId("324");
		dto.setProject("N/A");
		dto.setTransactionId("3329-3294-6574-8932");
		System.out.println(MailFactory.processMail(dto));
	}

}
