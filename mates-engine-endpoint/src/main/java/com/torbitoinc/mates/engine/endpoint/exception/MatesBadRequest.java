package com.torbitoinc.mates.engine.endpoint.exception;

import java.util.Arrays;
import java.util.List;

public class MatesBadRequest extends MatesException{
	private static final long serialVersionUID = 1L;
	public static final MatesBadRequest DEFAULT = new MatesBadRequest(Arrays.asList("Bad Request"));
	List<String> errors;
	
	
	public MatesBadRequest(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors(){
		return errors;
	}
	
	
}
