package com.g3.tktsvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketServiceConfig {
	static final Logger logger = LogManager.getLogger(TicketServiceConfig.class.getName());

	
	private static TicketServiceConfig config;
	private Properties props;
		
	private TicketServiceConfig() {}
	
	public static TicketServiceConfig getInstance() {
		if (config == null) { 
			config = new TicketServiceConfig();
			config.init();
		}
		return config;
	}
	
	public String getValue(String propertyKey) {
		return props.getProperty(propertyKey);
	}
	
	private void init() {
		props = new Properties();
	    InputStream input = null;
	 
	    try {
	        input = getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_FILE);
	        
	    	if (input != null) {
	        	props.load(input);
	        }
	        displayProperties();
	 
	    } catch (IOException ex) {
	        logger.error("Unable to load properties file: " + Constants.CONFIG_FILE);
	    } finally {
	        if (input != null) {
	            try {
	                input.close();
	            } catch (IOException e) { }
	        }
	    }
	}

	private void displayProperties() {
		logger.debug("Properties: " + props);
		
	}
}
