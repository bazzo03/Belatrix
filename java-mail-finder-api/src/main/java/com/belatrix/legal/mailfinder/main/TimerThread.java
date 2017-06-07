package com.belatrix.legal.mailfinder.main;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailfinder.facade.MailApplicationService;

public class TimerThread extends TimerTask {

	private final static Logger LOGGER = Logger.getLogger(TimerThread.class);
	
	@Override
	public void run() {
		MailApplicationService mailApplication = new MailApplicationService();
        mailApplication.start();
        LOGGER.trace("Thread started");
	}

}
