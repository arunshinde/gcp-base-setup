package com.gcp.cloud.poc.utilities.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionUtility {
	
    private static final Logger logger = Logger.getLogger(ExceptionUtility.class.getName());

	public static ExceptionUtility exceptionUtility = null;
	public static ExceptionUtility getInstance() {
		if(exceptionUtility==null) {
			exceptionUtility = new ExceptionUtility();
		}
		return exceptionUtility;
	}
	
	/**
	 * Log the exception
	 * @author arun
	 * @param e
	 */
	public void logException(Exception e) {
		logger.severe("An error occurred: " + e.getMessage());
        logger.log(Level.SEVERE, "Exception details:", e); 
	}

	/**
	 * Log throwable exception
	 * @author arun
	 * @param e
	 */
	public void logException(Throwable e) {
		logger.severe("An error occurred: " + e.getMessage());
        logger.log(Level.SEVERE, "Exception details:", e); 
	}
}
