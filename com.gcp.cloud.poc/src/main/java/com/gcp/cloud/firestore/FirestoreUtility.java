package com.gcp.cloud.firestore;

import java.util.Date;
import java.util.List;

import com.gcp.cloud.authentication.ServiceAuthentication;
import com.gcp.cloud.authentication.ServiceTypes;
import com.gcp.cloud.poc.instance.GCPInstanceUtility;
import com.gcp.cloud.poc.utilities.CommonUtility;
import com.gcp.cloud.poc.utilities.constants.Constants;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.common.collect.ImmutableList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

public class FirestoreUtility {
	public static FirestoreUtility utility = null;
	
	private final String FS_AUTH_KEY = "Key=";
	
	public static FirebaseApp firebaseApp = null;
	public static FirebaseMessaging firebaseMessaging = null;
	public static Firestore firestore = null;

	public static AccessToken accessToken = null;
	
	
	List<String> scopes = ImmutableList.of(
	          "https://www.googleapis.com/auth/firebase.database",
	          "https://www.googleapis.com/auth/cloud-platform",
	          "https://www.googleapis.com/auth/datastore");
	
	public static FirestoreUtility getInstance() {
		if(CommonUtility.getInstance().isNull(utility)) {
			utility = new FirestoreUtility();
			utility.initializeFirebaseApp();
			utility.initializeFirebaseFirestore();
			utility.initializeFirebaseMessaging();
		}
		return utility;
	}
	
	/**
	 * Read database id from the configs
	 * @author arun
	 * @param configs
	 * @return
	 */
	private String getDatabaseId(JsonObject configs) {
		if(CommonUtility.getInstance().isNotNull(configs) && configs.has(Constants.KEY_DATABASE_ID)) {
			return configs.get(Constants.KEY_DATABASE_ID).getAsString();
		}
		return Constants.DEFAULT_FIRESTORE_DATABASE_ID;
	}
	
	/**
	 * Initialize the FirebaseApp instance
	 * @author arun
	 * @return
	 */
	//TODO handle multiple projects and database ids in single project.
	public FirebaseApp initializeFirebaseApp() {
		if(CommonUtility.getInstance().isNull(firebaseApp)) {
			JsonObject configs = new JsonObject();
			configs.addProperty(Constants.KEY_DATABASE_ID, Constants.DEFAULT_FIRESTORE_DATABASE_ID);
			GoogleCredentials credentials = ServiceAuthentication.getInstance().getGoogleCredentials(ServiceTypes.Firebase, 
					Constants.FS_SERVICE_ACCOUNT_PATH, scopes, configs);
			accessToken = ServiceAuthentication.getInstance().getGoogleCredentialsAccessToken(credentials, scopes, configs);
			firebaseApp = initializeFirebaseApp(credentials, configs);
		}
		return firebaseApp;
	}
	
	/**
	 * Check Access Token is expired or not
	 * @author arun
	 * @param accessToken
	 * @return
	 */
	private boolean checkAccessTokenExpired(AccessToken accessToken) {
		if(accessToken.getExpirationTime().before(new Date(System.currentTimeMillis() + 5 * 60 * 1000))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get access token value
	 * @author arun
	 * @return
	 */
	public String getAccessToken() {
		if(CommonUtility.getInstance().isNull(accessToken) || checkAccessTokenExpired(accessToken)) {
			accessToken = ServiceAuthentication.getInstance().getGoogleCredentialsAccessToken(ServiceTypes.Firebase, Constants.FS_SERVICE_ACCOUNT_PATH, scopes, null);
		}
		return accessToken.getTokenValue();
	}
	
	/**
	 * Get the firebase app instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public FirebaseApp initializeFirebaseApp(GoogleCredentials credentials, JsonObject configs) {
		FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
				.setDatabaseId(getDatabaseId(configs))
				.build();
		FirebaseOptions options = FirebaseOptions.builder()
		    .setCredentials(credentials)
		    .setProjectId(GCPInstanceUtility.getInstance().getProjectId(configs))
		    .setFirestoreOptions(firestoreOptions)
		    .build();
		return FirebaseApp.initializeApp(options);
	}
	
	/**
	 * Initialize the firebase messaging instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public void initializeFirebaseMessaging() {
		if(CommonUtility.getInstance().isNull(firebaseMessaging)) {
			firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
		}
	}
	
	/**
	 * Initialize the firebase firestore instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public void initializeFirebaseFirestore() {
		if(CommonUtility.getInstance().isNull(firestore)) {
			firestore = FirestoreClient.getFirestore(firebaseApp);
		}
	}
	
	
	/**
	 * Get the firebase messaging instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public FirebaseMessaging getFirebaseMessaging() {
		return firebaseMessaging;
	}
	
	/**
	 * Get the firebase firestore instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public Firestore getFirebaseFirestore() {
		return firestore;
	}
}
