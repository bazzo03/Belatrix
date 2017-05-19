package com.belatrix.legal.mailfinder.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * LoadConfig load properties from File
 * 
 * @author dbernal
 * 
 */
public class LoadDaemonConfig {

	/**
	 * Property
	 */
	private final Properties configProp = new Properties();

	/**
	 * LoadConfig constructor
	 */
	private LoadDaemonConfig() {

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("daemon.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static class LoadConfigInner {
		private static final LoadDaemonConfig INSTANCE = new LoadDaemonConfig();
	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static LoadDaemonConfig getInstance() {
		return LoadConfigInner.INSTANCE;
	}
	
	/**
	 * Method that return the value of a property.
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return configProp.getProperty(key);
	}
	
	/**
	 * Method that return all properties
	 * @return
	 */
	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	/**
	 * Method that validate if exist a property
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}

}
