package com.gcp.cloud.poc.utilities.exception;

public class ServiceException extends Exception{
	private int systemCode;
    private int statusCode;
    
    public ServiceException(int systemCode, int statusCode, String message) {
        super(message);
        this.systemCode = systemCode;
        this.statusCode = statusCode;
    }

	public int getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(int systemCode) {
		this.systemCode = systemCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
