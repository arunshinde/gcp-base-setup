package com.gcp.cloud.poc.utilities.inputs;

import java.util.Map;

import com.gcp.cloud.poc.utilities.network.HttpMethodTypes;


public class ApiNetworkInputs {
	private String url;
	private HttpMethodTypes methodType;
	private Object payload;
	private Map<String, String> headers;
	
	private boolean executeAsync;
	
	public ApiNetworkInputs() {
		super();
	}
	
	public ApiNetworkInputs(Builder builder) {
		this.url = builder.url;
		this.methodType = builder.methodType;
		this.payload = builder.payload;
		this.headers = builder.headers;
		this.executeAsync = builder.executeAsync;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethodTypes getMethodType() {
		return methodType;
	}

	public void setMethodType(HttpMethodTypes methodType) {
		this.methodType = methodType;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public boolean isExecuteAsync() {
		return executeAsync;
	}

	public void setExecuteAsync(boolean executeAsync) {
		this.executeAsync = executeAsync;
	}

	public static class Builder{
		private String url;
		private HttpMethodTypes methodType;
		private Object payload;
		private Map<String, String> headers;
		
		private boolean executeAsync;
		
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		public Builder setMethodType(HttpMethodTypes methodType) {
			this.methodType = methodType;
			return this;
		}
		public Builder setPayload(Object payload) {
			this.payload = payload;
			return this;
		}
		public Builder setHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}
		public Builder setExecuteAsync(boolean executeAsync) {
			this.executeAsync = executeAsync;
			return this;
		}
		public ApiNetworkInputs build() {
			return new ApiNetworkInputs(this);
		}
	}
	
}
