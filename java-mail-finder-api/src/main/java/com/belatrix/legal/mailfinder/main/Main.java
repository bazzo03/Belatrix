package com.belatrix.legal.mailfinder.main;

import java.util.Timer;

import org.apache.log4j.Logger;

public class Main {

	private final static Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String... args) {

		TimerThread timerThread = new TimerThread();
		
	    Timer timer = new Timer();

	    /*
	     * Set an initial delay of 1 second, then repeat every half second.
	     */
	    timer.schedule(timerThread, 10000, 8000);
		
	        try {
	            Thread.sleep(300);
	        } catch (Exception x) {
	        	  timer.cancel();
	        }

	      
	}

}
