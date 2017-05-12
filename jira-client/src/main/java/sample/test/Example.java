package sample.test;

import net.rcarz.jiraclient.JiraException;

public class Example {
	public static void main(String[] args) throws JiraException {

		String host = "pop.gmail.com";
		String mailStoreType = "pop3";
		String username = "wseminario.belatrix.jira@gmail.com";
		String password = "Willyjhon88";

		MailFinder.check(host, mailStoreType, username, password);

	}
}