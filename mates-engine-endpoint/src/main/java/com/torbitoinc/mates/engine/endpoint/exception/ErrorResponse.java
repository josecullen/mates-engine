package com.torbitoinc.mates.engine.endpoint.exception;

import io.vertx.core.json.JsonObject;

public class ErrorResponse {
	private JsonObject bodyRequest;
	private String message;
	private String restMethod;
	private String path;		
	
	public ErrorResponse(JsonObject bodyRequest2, String path2, String restMethod2, String message2) {
		setBodyRequest(bodyRequest2);
		setMessage(message2);
		setPath(path2);
		setRestMethod(restMethod2);
	}

	public JsonObject getBodyRequest() {
		return bodyRequest;
	}

	public void setBodyRequest(JsonObject bodyRequest) {
		this.bodyRequest = bodyRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getRestMethod() {
		return restMethod;
	}


	public void setRestMethod(String restMethod) {
		this.restMethod = restMethod;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

}
