package com.station.taxi.sockets.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.station.taxi.utils.LoggerWrapper;

/**
 * Represent request of StationClient
 * @author alex
 */
public class Request implements JSONMessage {
	public static final String KEY_CABNUM = "num";
	public static final String KEY_CABWHILEWAITING = "whileWaiting";
	

	private String mAction = "";
	private Map<String,Object> mData = new HashMap<String, Object>();
	
	public Request() {
		
	}
	/**
	 * 
	 * @param action 
	 */
	public Request(String action) {
		mAction = action;
	}
	/**
	 * Request action
	 * @return 
	 */
	public String getAction() {
		return mAction;
	}
	
	/**
	 * Data value by key
	 * @param key
	 * @return 
	 */
	public Object getData(String key) {
		return mData.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param value 
	 */
	public void put(String key, Object value) {
		mData.put(key, value);
	}

	public JSONObject toJSON() {
		if (mAction == null) {
			throw new IllegalStateException("Action is not defined");
		}
		JSONObject json = new JSONObject();
		try {
			json.put(MessageFactory.KEY_ACTION, mAction);
			for(String key: mData.keySet()) {
				json.put(key, mData.get(key));
			}
		} catch (JSONException e) {
			LoggerWrapper.logException(Request.class.getSimpleName(), e);
		}
		return json;
	}

	/**
	 * Parse json message into request
	 */
	public void parse(JSONObject json) {
		Iterator keys = json.keys();
		while(keys.hasNext()) {
			String key = (String) keys.next();
			try {
				if (key.equals(MessageFactory.KEY_ACTION)) {
					mAction = (String) json.get(key);
				} else {
					mData.put((String)key, json.get(key));
				}
			} catch (JSONException e) {
				LoggerWrapper.logException(Request.class.getSimpleName(), e);
			}
		}
	}
	
}
