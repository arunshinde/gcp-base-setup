package com.gcp.cloud.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.gcp.cloud.poc.utilities.CommonUtility;
import com.gcp.cloud.poc.utilities.exception.ExceptionUtility;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonObject;

public class ServiceAuthentication {
	private static final Logger logger = Logger.getLogger(ServiceAuthentication.class.getName());
	
	public static ServiceAuthentication serviceAuthentication = null;
	public static Map<ServiceTypes, GoogleCredentials> googleCredentialsMap = new HashMap<>();

	public ServiceAuthentication() {}

	public static ServiceAuthentication getInstance() {
		if(CommonUtility.getInstance().isNull(serviceAuthentication)) {
			serviceAuthentication = new ServiceAuthentication();
		}
		return serviceAuthentication;
	}
	
	/**
	 * Create Datastore client service object
	 * @author arun
	 * @param serviceAccountPath
	 */
	private void getDatastoreServiceObject(GoogleCredentials credentials, JsonObject configs) {
		/*
		return DatastoreUtility.getInstance().prepareDatastoreOptions((GoogleCredentials) credentials, configs)
				.build()
				.getService();
		*/
	}
	
	/**
	 * Create Bigquery client service object
	 * @author arun
	 * @param serviceAccountPath
	 */
	private void getBigqueryServiceObject(String serviceAccountPath) {
		
	}
	
	/**
	 * Create Firebase client service object
	 * @author arun
	 * @param serviceAccountPath
	 */
	private void getFirebaseServiceObject(GoogleCredentials credentials, JsonObject configs) {
		
	}
	
	/**
	 * Create Firebase client service object
	 * @author arun
	 * @param serviceAccountPath
	 */
	private void getCloudTaskServiceObject(String serviceAccountPath) {
		
	}
	
	/**
	 * Prepare service client object
	 * @author arun
	 * @param serviceTypes
	 * @param serviceAccountPath
	 * @return
	 */
	//TODO handle other type of clients objects
	private Object prepareServiceObject(ServiceTypes serviceTypes, GoogleCredentials credentials, JsonObject configs) {
		Object client = null;
		switch(serviceTypes) {
			case Firebase:
			case BigQuery:
			case CloudTask:
			default:
				
		}
		return client;
	}
	
	/**
	 * Get Google credentials object from the service account file path
	 * @author arun
	 * @param serviceAccountPath
	 * @param scopes
	 * @return
	 * @throws Exception
	 */
	private GoogleCredentials prepareGoogleCredentials(String serviceAccountPath, ServiceTypes serviceTypes, List<String> scopes) {
	    try{
	    	GoogleCredentials credentials = googleCredentialsMap.get(serviceTypes);
	    	if(CommonUtility.getInstance().isNull(credentials)) {
	    		credentials = GoogleCredentials.fromStream(CommonUtility.getInstance().readFileContent(serviceAccountPath));
		        credentials = CommonUtility.getInstance().isNullOrEmpty(scopes)?credentials:credentials.createScoped(scopes);
		        googleCredentialsMap.put(serviceTypes, credentials);
	    	}
	        return credentials;
	    }catch(Exception e) {
			ExceptionUtility.getInstance().logException(e);
		}
	    return null;
	}
	
	/**
	 * Get Google credentials instance using service account
	 * @author arun
	 * @param serviceTypes
	 * @param serviceAccountPath
	 * @return
	 */
	public GoogleCredentials getGoogleCredentials(ServiceTypes serviceTypes, String serviceAccountPath, List<String> scopes, JsonObject configs) {
		return prepareGoogleCredentials(serviceAccountPath, serviceTypes, scopes);
	        
	}
	
	/**
	 * Get service client instance
	 * @author arun
	 * @param serviceTypes
	 * @param serviceAccountPath
	 * @return
	 */
	public Object getClientServiceInstance(ServiceTypes serviceTypes, String serviceAccountPath, List<String> scopes, JsonObject configs) {
		return prepareServiceObject(serviceTypes, prepareGoogleCredentials(serviceAccountPath, serviceTypes, scopes), configs);
	}
	
	/**
	 * Prepare Google credentials instance and get Access Token from it 
	 * @author arun
	 * @param serviceTypes
	 * @param serviceAccountPath
	 * @return
	 * @throws IOException 
	 */
	public AccessToken getGoogleCredentialsAccessToken(ServiceTypes serviceTypes, String serviceAccountPath, List<String> scopes, JsonObject configs) {
		try {
			GoogleCredentials googleCredentials = prepareGoogleCredentials(serviceAccountPath, serviceTypes, scopes);
			return getGoogleCredentialsAccessToken(googleCredentials, scopes, configs);
		} catch (Exception e) {
			ExceptionUtility.getInstance().logException(e);
		}
		return null;
	}
	
	/**
	 * Get Access Token from the Google credentials instance
	 * @author arun
	 * @param serviceTypes
	 * @param serviceAccountPath
	 * @return
	 * @throws IOException 
	 */
	public AccessToken getGoogleCredentialsAccessToken(GoogleCredentials googleCredentials, List<String> scopes, JsonObject configs) {
		try {
			googleCredentials.refreshIfExpired();
			return googleCredentials.getAccessToken();
		} catch (Exception e) {
			ExceptionUtility.getInstance().logException(e);
		}
		return null;
	}
	
}
