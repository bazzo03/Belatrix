package com.belatrix.legal.mailfinder.main;

import java.util.Timer;

import org.apache.log4j.Logger;

import com.belatrix.legal.mailfinder.config.EPropertyDaemon;
import com.belatrix.legal.mailfinder.config.LoadDaemonConfig;

public class Main {

	private static final String DELAY = LoadDaemonConfig.getInstance()
			.getProperty(EPropertyDaemon.DELAY.getNameProperty());
	private static final String PERIOD = LoadDaemonConfig.getInstance()
			.getProperty(EPropertyDaemon.PERIOD.getNameProperty());
	private static final String SLEEP = LoadDaemonConfig.getInstance()
			.getProperty(EPropertyDaemon.SLEEP.getNameProperty());
	
	private final static Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String... args) {

		LOGGER.info("...Starting application...");
		
		TimerThread timerThread = new TimerThread();
		
	    Timer timer = new Timer();

	    timer.schedule(timerThread, Long.parseLong(DELAY), Long.parseLong(PERIOD));
	    LOGGER.info("Timer started");
		
	        try {
	            Thread.sleep(Long.parseLong(SLEEP));
	        } catch (Exception x) {
	        	  timer.cancel();
	        }

	      
	}

}
