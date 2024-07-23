package com.gcp.cloud.firestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.gcp.cloud.poc.utilities.constants.Constants;
import com.gcp.cloud.poc.utilities.inputs.ApiNetworkInputs;
import com.gcp.cloud.poc.utilities.network.ApiNetworkUtility;
import com.gcp.cloud.poc.utilities.network.HttpMethodTypes;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.JsonObject;

public class FirestoreExecutors {
	/**
	 * Send FCM messages using Rest API
	 * @author arun
	 */
	private JsonObject sendMessageUsingRestAPI(boolean isAsync) {
	    Map<String, String> headers = new HashMap<>();
	    headers.put(Constants.HEADER_AUTHORIZATION_KEY, Constants.HEADER_AUTHORIZATION_BEARER_KEY + Constants.SPACE_VALUE + FirestoreUtility.getInstance().getAccessToken());
	    headers.put(Constants.HEADER_CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_APP_JSON_UTF8_VALUE);

	    String payload = """
	                {
	                    "message": {
	                        "token": "registration_token_id",
	                        "notification": {
	                            "body": "This is an FCM notification message!",
	                            "title": "FCM Message Title"
	                        }
	                    }
	                }
	                """;
	   
	    int count = 50;

	    ApiNetworkInputs apiNetworkInputs = new ApiNetworkInputs.Builder()
	            .setUrl(Constants.FCM_URL)
	            .setMethodType(HttpMethodTypes.POST)
	            .setHeaders(headers)
	            .setPayload(payload)
	            .build();

	    long[] latencies = new long[count]; 
	    for (int i = 0; i < count; i++) {
	        long startTime = System.nanoTime();
	        if(!isAsync) {
	        	ApiNetworkUtility.getInstance().doSyncRequest(apiNetworkInputs);
	        }else {
	        	ApiNetworkUtility.getInstance().doAsyncRequest(apiNetworkInputs);
	        }
	        long endTime = System.nanoTime();
	        latencies[i] = (endTime - startTime) / 1_000_000; 
	    }

	    long maxLatency = Arrays.stream(latencies).max().orElse(0L);
	    long minLatency = Arrays.stream(latencies).min().orElse(0L);
	    long sumLatency = Arrays.stream(latencies).sum();
	    long avgLatency = sumLatency / count;
	    
	    JsonObject finalOutput = new JsonObject();
	    finalOutput.addProperty("maxLatency", maxLatency);
	    finalOutput.addProperty("minLatency", minLatency);
	    finalOutput.addProperty("sumLatency", sumLatency);
	    finalOutput.addProperty("avgLatency", avgLatency);
	    return finalOutput;
	}
	
	/**
	 * Send FCM messages using Firebase Messaging
	 * @author arun
	 * @throws FirebaseMessagingException 
	 */
	private JsonObject sendMessageUsingSDK(boolean isAsync) { 
	    String registrationToken = "registration_token_id";

	    Message message = Message.builder()
	        .setNotification(Notification.builder()
	            .setTitle("FCM Message Title")
	            .setBody("This is an FCM notification message!")
	            .build())
	        .setToken(registrationToken)
	        .build();

	    int count = 50; 
	    
	    long[] latencies = new long[count]; 
	    
	    for (int i = 0; i < count; i++) {
	        long startTime = System.nanoTime();
	        if(!isAsync) {
	        	try {
	        		FirestoreUtility.firebaseMessaging.send(message);
				} catch (FirebaseMessagingException e) {
					//TODO handle later
				}
	        }else {
	        	FirestoreUtility.firebaseMessaging.sendAsync(message);
	        }
	        long endTime = System.nanoTime();
	        latencies[i] = (endTime - startTime) / 1_000_000; 
	    }

	    long maxLatency = Arrays.stream(latencies).max().orElse(0L);
	    long minLatency = Arrays.stream(latencies).min().orElse(0L);
	    long sumLatency = Arrays.stream(latencies).sum();
	    long avgLatency = sumLatency / count;
	    
	    JsonObject finalOutput = new JsonObject();
	    finalOutput.addProperty("maxLatency", maxLatency);
	    finalOutput.addProperty("minLatency", minLatency);
	    finalOutput.addProperty("sumLatency", sumLatency);
	    finalOutput.addProperty("avgLatency", avgLatency);

	    return finalOutput;
	}

	private void createFirestoreEntityUsingRestApi() {
		
	}
	
	public void createFirestoreEntityUsingSDK() {
		Map<String, Object> data = new HashMap<>();
		data.put("first", "Ada-1");
		data.put("last", "Lovelace-1");
		data.put("born", 1815);
		FirestoreUtility.firestore.collection("users").document("Testing1").set(data);
		System.out.println("Testing-1 pushed");
		
		
		data.put("first", "Ada-2");
		data.put("last", "Lovelace-2");
		data.put("born", 1915);
		FirestoreUtility.firestore.collection("users").document("Testing2").set(data);
		System.out.println("Testing-2 pushed");
	}
	
	public void calculateLatency() {
		/*
		int count = 10;
		String apiOutputResultASync = "Max Latency, Min Latency, Sum Latency, Avg Latency\n";
		List<JsonObject> apiOutputAsync = new ArrayList<>(); 
		for(int i=0; i<count; i++) {
			apiOutputAsync.add(FirestoreUtility.getInstance().sendMessageUsingRestAPI(true));
		}
		for(JsonObject output: apiOutputAsync) {
			apiOutputResultASync += output.get("maxLatency").getAsString() + ", " + output.get("minLatency").getAsString() + ", "
					+ output.get("sumLatency").getAsString() + ", " + output.get("avgLatency").getAsString() + "\n";
		}
		
		String fcmOutputResultASync = "Max Latency, Min Latency, Sum Latency, Avg Latency\n";
		List<JsonObject> fcmOutputAsync = new ArrayList<>();
		for(int i=0; i<count; i++) {
			fcmOutputAsync.add(FirestoreUtility.getInstance().sendMessageUsingSDK(true));
		}
		for(JsonObject output: fcmOutputAsync) {
			fcmOutputResultASync += output.get("maxLatency").getAsString() + ", " + output.get("minLatency").getAsString() + ", "
					+ output.get("sumLatency").getAsString() + ", " + output.get("avgLatency").getAsString() + "\n";
		}
		
		
		String apiOutputResultSync = "Max Latency, Min Latency, Sum Latency, Avg Latency\n";
		List<JsonObject> apiOutputSync = new ArrayList<>(); 
		for(int i=0; i<count; i++) {
			apiOutputSync.add(FirestoreUtility.getInstance().sendMessageUsingRestAPI(false));
		}
		for(JsonObject output: apiOutputSync) {
			apiOutputResultSync += output.get("maxLatency").getAsString() + ", " + output.get("minLatency").getAsString() + ", "
					+ output.get("sumLatency").getAsString() + ", " + output.get("avgLatency").getAsString() + "\n";
		}
		
		String fcmOutputResultSync = "Max Latency, Min Latency, Sum Latency, Avg Latency\n";
		List<JsonObject> fcmOutputSync = new ArrayList<>();
		for(int i=0; i<count; i++) {
			fcmOutputSync.add(FirestoreUtility.getInstance().sendMessageUsingSDK(false));
		}
		for(JsonObject output: fcmOutputSync) {
			fcmOutputResultSync += output.get("maxLatency").getAsString() + ", " + output.get("minLatency").getAsString() + ", "
					+ output.get("sumLatency").getAsString() + ", " + output.get("avgLatency").getAsString() + "\n";
		}
		
	    System.out.println(apiOutputResultASync);
	    System.out.println(apiOutputResultSync);
	    System.out.println(fcmOutputResultASync);
	    System.out.println(fcmOutputResultSync);
	    */
	}
}
