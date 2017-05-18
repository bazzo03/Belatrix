package com.belatrix.legal.mailfinder.main;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailfinder.facade.MailApplicationService;

public class Main {

	private final static Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String... args) {
		Thread t = new Thread(new MailApplicationService());
		try {
			LOGGER.info("Sleeping Thread...");
			t.setDaemon(true);
			t.start();
			t.join();
			Thread.sleep(5000);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
