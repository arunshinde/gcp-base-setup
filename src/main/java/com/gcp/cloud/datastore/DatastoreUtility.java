package com.gcp.cloud.datastore;

import com.gcp.cloud.poc.instance.GCPInstanceUtility;
import com.gcp.cloud.poc.utilities.CommonUtility;
import com.gcp.cloud.poc.utilities.constants.Constants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.DatastoreOptions.Builder;
import com.google.gson.JsonObject;

public class DatastoreUtility {
	public static DatastoreUtility utility = null;
	public static DatastoreUtility getInstance() {
		if(CommonUtility.getInstance().isNull(utility)) {
			utility = new DatastoreUtility();
		}
		return utility;
	}
	
	/**
	 * Get Default Datastoreoptions object
	 * @author arun
	 * @return
	 */
	private Builder getDefaultBuilder() {
		return DatastoreOptions.newBuilder()
		.setProjectId(Constants.PROJECT_ID)
		.setDatabaseId(Constants.DEFAULT_DATASTORE_DATABASE_ID);
	}
	
	/**
	 * Prepare Builder from the configs
	 * @author arun
	 * @return
	 */
	private Builder getDatastoreOptionsBuilder(JsonObject configs) {
		return DatastoreOptions.newBuilder()
			.setProjectId(GCPInstanceUtility.getInstance().getProjectId(configs))
			.setDatabaseId(getDatabaseId(configs));
	}
	
	/**
	 * Read database id from the configs
	 * @author arun
	 * @param configs
	 * @return
	 */
	private String getDatabaseId(JsonObject configs) {
		if(CommonUtility.getInstance().isNotNull(configs) && configs.has("projectId")) {
			return configs.get("projectId").getAsString();
		}
		return Constants.PROJECT_ID;
	}
	
	/**
	 * Get the Datastore instance
	 * @author arun
	 * @param credentials
	 * @param configs
	 * @return
	 */
	public Builder prepareDatastoreOptions(GoogleCredentials credentials, JsonObject configs) {
		Builder builder = null;
		if(CommonUtility.getInstance().isNull(configs)) {
			builder = getDefaultBuilder();
		}else {
			builder = getDatastoreOptionsBuilder(configs);
		}
		if(CommonUtility.getInstance().isNotNull(credentials)) {
			builder.setCredentials(credentials);
		}
		return builder;
	}
}
