package com.gcp.cloud.poc.utilities.constants;

public class Constants {
	public static final String PROJECT_ID = "YOUR_PROJECT_ID";
	public static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/" + PROJECT_ID + "/messages:send";
	
	public static final String DEFAULT_DATASTORE_DATABASE_ID = "(default)";
	public static final String DEFAULT_FIRESTORE_DATABASE_ID = "firestore";
	
	public static final String FS_SERVICE_ACCOUNT_PATH = "gcp-keys/project-id/project-id-76cb449e5ae5.json";
	
	
	public static final String DATASTORE_SERVICE_ACCOUNT_PATH = "";
	public static final String CLOUD_TASKS_SERVICE_ACCOUNT_PATH = "";
	public static final String BIGQUERY_SERVICE_ACCOUNT_PATH = "";
	
	public static final String KEY_PROJECT_ID = "projectId";
	public static final String KEY_DATABASE_ID = "databaseId";
	
	public static final String KEY_FS_DATABASE_URL = "fsDatabaseUrl";
	
	public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
	public static final String HEADER_AUTHORIZATION_BEARER_KEY = "Bearer";
	public static final String SPACE_VALUE = " ";
	
	
	public static final String HEADER_CONTENT_TYPE_KEY = "Content-TYpe";
	public static final String CONTENT_TYPE_APP_JSON_UTF8_VALUE = "application/json; UTF-8";
}
