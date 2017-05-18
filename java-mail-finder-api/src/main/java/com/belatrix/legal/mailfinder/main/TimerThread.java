package com.belatrix.legal.mailfinder.main;
import java.util.TimerTask;

import com.belatrix.legal.mailfinder.facade.MailApplicationService;

public class TimerThread extends TimerTask {

	@Override
	public void run() {
		
		MailApplicationService t = new MailApplicationService();
        t.start();

			
	}

}
