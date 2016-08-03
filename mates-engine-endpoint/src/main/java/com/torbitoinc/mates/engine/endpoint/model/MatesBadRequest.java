package com.torbitoinc.mates.engine.endpoint.model;

import com.torbitoinc.mates.engine.endpoint.exception.ErrorResponse;

import io.vertx.core.json.JsonObject;

public class MatesBadRequest extends MatesException{

	private static final long serialVersionUID = 1L;
	private ErrorResponse errorResponse;
	
	public MatesBadRequest(JsonObject bodyRequest, String path, String restMethod, String message) {
		errorResponse = new ErrorResponse(bodyRequest, path, restMethod, message);
	}

	public ErrorResponse getErrorResponse(){
		return errorResponse;
	}
	
	
}
