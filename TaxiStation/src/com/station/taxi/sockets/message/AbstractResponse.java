package com.station.taxi.sockets.message;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.station.taxi.utils.LoggerWrapper;

/**
 * Abstract json response message
 * @author alex
 */
abstract public class AbstractResponse implements JSONMessage {
	private static final String KEY_RESPONSE_STATUS = "status";
	
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "error";

	private static final String KEY_ERRORS = "errors";
	
	private String mStatus;
	private String mAction;
	private List<String> mErrors = new ArrayList<String>();
	
	public AbstractResponse() { }

	/**
	 * @return request action
	 */
	public String getAction() {
		return mAction;
	}
	/**
	 * Original request action
	 * @param action 
	 */
	public void setAction(String action) {
		mAction = action;
	}
	
	/**
	 * Status of response
	 * @param status 
	 */
	public void setStatus(String status) {
		mStatus = status;
	}
	
	/**
	 * Add error message to response
	 * @param error 
	 */
	public void addError(String error) {
		mErrors.add(error);
	}

	/**
	 * Set list of errors
	 * @param errorList
	 */
	public void setErrors(List<String> errorList) {
		mErrors = errorList;
	}

	/**
	 * Check for good status
	 * @return 
	 */
	public boolean isStatusOk() {
		return mStatus!=null & mStatus.equals(STATUS_OK);
	}
	
	/**
	 * @return response status
	 */
	public String getStatus() {
		return mStatus;
	}

	/**
	 * Parse response from JSON
	 */
	public void parse(JSONObject json) {
		try {
			mStatus = (String)json.get(KEY_RESPONSE_STATUS);
			if (json.has(KEY_ERRORS)) {
				mErrors = (List<String>) json.get(KEY_ERRORS);
			}
			parseType(json);
		} catch (JSONException e) {
			LoggerWrapper.logException(AbstractResponse.class.getSimpleName(), e);
		}
	}
	
	/**
	 * Parse concrete response type
	 * @param json
	 * @throws JSONException
	 */
	abstract protected void parseType(JSONObject json) throws JSONException;

}
