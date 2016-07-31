package math.game.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestResponse {

	public static String createError(ErrorType errorType, String description) {
		return process(new RestError(errorType, description));
	}

	public static String createSucess(String description) {
		return process(new RestResponseBase(true, description));
	}

	public static String createSucess(String description, Object data) {
		return process(new RestResponseData(true, description, data));
	}

	private static String process(Object objectProcessable) {
		String response = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			response = mapper.writeValueAsString(objectProcessable);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}

}



class RestResponseBase {
	private boolean success;
	private String description;

	public RestResponseBase() {
	}

	public RestResponseBase(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

class RestResponseData extends RestResponseBase {
	private Object data;

	public RestResponseData() {
	}

	public RestResponseData(boolean sucess, String description, Object data) {
		super(sucess, description);
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

class RestError extends RestResponseBase {
	private ErrorType errorType;

	public RestError() {
		super(false, "");
	}

	public RestError(ErrorType errorType, String description) {
		super(false, description);
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

}
