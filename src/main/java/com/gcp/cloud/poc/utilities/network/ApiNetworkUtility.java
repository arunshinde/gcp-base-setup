package com.gcp.cloud.poc.utilities.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import com.gcp.cloud.poc.utilities.CommonUtility;
import com.gcp.cloud.poc.utilities.inputs.ApiNetworkInputs;
import com.google.gson.JsonObject;

public class ApiNetworkUtility {
	private static final Logger logger = Logger.getLogger(ApiNetworkUtility.class.getName());
	
	public static ApiNetworkUtility apiNetworkUtility = null;
	private static HttpClient httpClient;

	public ApiNetworkUtility() {}

	public static ApiNetworkUtility getInstance() {
		if(CommonUtility.getInstance().isNull(apiNetworkUtility)) {
			apiNetworkUtility = new ApiNetworkUtility();
			httpClient = HttpClient.newBuilder().build();
		}
		return apiNetworkUtility;
	}
	
	/**
	 * Do Asynchronous API call
	 * @author arun
	 * @param request
	 * @return
	 */
	public CompletableFuture<JsonObject> doAsyncRequest(ApiNetworkInputs apiNetworkInputs) {
		HttpRequest request = prepareHttpRequest(apiNetworkInputs);
		return httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(response -> {
			long endTime = System.nanoTime(); // Capture end time after response
			long latencyMillis = (endTime - System.nanoTime()) / 1_000_000; // Calculate latency

			JsonObject output = new JsonObject();
			output.addProperty("statusCode", response.statusCode());
			output.addProperty("responseBody", response.body());
			output.addProperty("latencyMillis", latencyMillis);
			return output;
		}).exceptionally(ex -> {
			//ExceptionUtility.getInstance().logException(ex);  // Log error
			return new JsonObject(); // Example: return an empty JsonObject
		});
	}
	
	/**
	 * Do synchronous API call
	 * @author arun
	 * @param request
	 * @return
	 */
	public JsonObject doSyncRequest(ApiNetworkInputs apiNetworkInputs) {
		HttpRequest request = prepareHttpRequest(apiNetworkInputs);
		try {
			long startTime = System.nanoTime();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
	        long endTime = System.nanoTime();
	        long latencyMillis = (endTime - startTime) / 1_000_000;
	        
	        JsonObject output = new JsonObject();
	        output.addProperty("statusCode", response.statusCode());
	        output.addProperty("responseBody", response.body());
	        output.addProperty("latencyMillis", latencyMillis);
	        return output;
		}catch(Exception e) {
			//ExceptionUtility.getInstance().logException(e);
		}
		return null;
	}
	
	/**
	 * Do HTTP Rest API calls
	 * @author arun
	 * @param apiNetworkInputs
	 * @return
	 */
	public HttpRequest prepareHttpRequest(ApiNetworkInputs apiNetworkInputs) {
		HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(apiNetworkInputs.getUrl()));
                //.build();

		if(CommonUtility.getInstance().isNotNullOrEmpty(apiNetworkInputs.getHeaders())) {
			apiNetworkInputs.getHeaders().forEach(builder::header);
		}
		
		switch(apiNetworkInputs.getMethodType()) {
			case GET:
				builder.GET();
				break;
			case PUT:
				builder.PUT(HttpRequest.BodyPublishers.ofString(apiNetworkInputs.getPayload().toString()));
				break;
			case POST:
			default:
				builder.POST(HttpRequest.BodyPublishers.ofString(apiNetworkInputs.getPayload().toString()));
		}
		return builder.build();
	}
}
