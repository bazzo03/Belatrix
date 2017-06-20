package com.belatrix.legal.apihandler.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.belatrix.legal.apihandler.test.process.JiraOperationTest;
import com.belatrix.legal.apihandler.test.process.TrelloOperationTest;

@RunWith(Suite.class)
@SuiteClasses({
	JiraOperationTest.class,
	TrelloOperationTest.class })
public class ApiHandlerTest {

}
