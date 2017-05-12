package sample.test;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class JiraIntegrationAPI {

	final static BasicCredentials creds = new BasicCredentials("wseminario", "ws123456");
	static JiraClient jira;
	
	public static void createIssue(String action) throws JiraException {
		jira = new JiraClient("https://jira.belatrixsf.com/", creds);
		/* Create a new issue. */
		Issue newIssue = jira.createIssue("LEG", "Story").field(Field.SUMMARY, action)
				.field(Field.DESCRIPTION, "Testing purposes").field(Field.REPORTER, "wseminario")
				.field(Field.ASSIGNEE, "wseminario").execute();
		System.out.println(newIssue);

	}
}