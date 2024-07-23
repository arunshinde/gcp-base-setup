package com.gcp.cloud.poc.instance;

import com.gcp.cloud.poc.utilities.CommonUtility;
import com.gcp.cloud.poc.utilities.constants.Constants;
import com.google.gson.JsonObject;

public class GCPInstanceUtility {
	public static GCPInstanceUtility utility = null;
	public static GCPInstanceUtility getInstance() {
		if(CommonUtility.getInstance().isNull(utility)) {
			utility = new GCPInstanceUtility();
		}
		return utility;
	}
	
	/**
	 * Read project id from the configs
	 * @author arun
	 * @param configs
	 * @return
	 */
	public String getProjectId(JsonObject configs) {
		if(CommonUtility.getInstance().isNotNull(configs) && configs.has(Constants.KEY_PROJECT_ID)) {
			return configs.get(Constants.KEY_PROJECT_ID).getAsString();
		}
		return Constants.PROJECT_ID;
	}

}
