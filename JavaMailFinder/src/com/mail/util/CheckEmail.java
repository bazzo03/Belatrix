package com.mail.util;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class CheckEmail {

	public static void check(String host, String storeType, String user,
		      String password) 
		   {
		      try {

//		      //create properties field
//		      Properties properties = new Properties();
//
//		      properties.put("mail.pop3.host", host);
//		      properties.put("mail.pop3.port", "995");
//		      properties.put("mail.pop3.starttls.enable", "true");
//		      Session emailSession = Session.getDefaultInstance(properties);
//		  
//		      //create the POP3 store object and connect with the pop server
//		      Store store = emailSession.getStore("pop3s");
//
//		      store.connect(host, user, password);
//		      
//		      //create the folder object and open it
//		      Folder emailFolder = store.getFolder("inbox");
//		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      //Message[] messages = emailFolder.getMessages();
		      Message[] messages = fetchMessages("imap.gmail.com", "wseminario.belatrix.jira@gmail.com", "Willyjhon88", false);
		      System.out.println("messages.length---" + messages.length);


		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
		         if (message.getSubject().contains("TEST")) {
		        	 System.out.println("---------------------------------");
			         System.out.println("Email Number " + (i + 1));
			         System.out.println("Subject: " + message.getSubject());
			         System.out.println("From: " + message.getFrom()[0]);
			         System.out.println("Text: " + message.getContent().toString());
			         //message.setFlags(new Flags(Flags.Flag.SEEN), false);
		         }
		      }
		      
//		      
//		      //close the store and folder objects
//		      emailFolder.close(false);
//		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }

		   public static void main(String[] args) {

		      String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = "wseminario.belatrix.jira@gmail.com";// change accordingly
		      String password = "Willyjhon88";// change accordingly

		      check(host, mailStoreType, username, password);

		   }
		   
		   public static Message[] fetchMessages(String host, String user, String password, boolean read) throws MessagingException {
			    Properties properties = new Properties();
			    properties.put("mail.store.protocol", "imaps");

			    Session emailSession = Session.getDefaultInstance(properties);
			    Store store = emailSession.getStore("imaps");
			    store.connect(host, user, password);

			    Folder emailFolder = store.getFolder("INBOX");
			    // use READ_ONLY if you don't wish the messages
			    // to be marked as read after retrieving its content
			    emailFolder.open(Folder.READ_WRITE);

			    // search for all "unseen" messages
			    Flags seen = new Flags(Flags.Flag.SEEN);
			    FlagTerm unseenFlagTerm = new FlagTerm(seen, read);
			    return emailFolder.search(unseenFlagTerm);
			}

}