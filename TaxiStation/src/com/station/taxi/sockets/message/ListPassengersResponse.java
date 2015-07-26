package com.station.taxi.sockets.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response message that contain list of waiting passengers
 * @author alex
 */
public class ListPassengersResponse extends AbstractResponse {
	private static final String KEY_PASSENGERS = "passengers";

	private Map<String,String> mPassengers = new HashMap<String, String>();

	/**
	 * @return map of passenger name to their destination
	 */
	public Map<String,String> getPassengers() {
		return mPassengers;
	}

	@Override
	protected void parseType(JSONObject json) throws JSONException {
		JSONObject jsonPassengers = (JSONObject) json.get(KEY_PASSENGERS);
		Iterator keys = jsonPassengers.keys();
		while(keys.hasNext()) {
			String kStr = (String)keys.next();
			String kValue = (String)jsonPassengers.get(kStr);
			mPassengers.put(kStr, kValue);
		}
	}
	
}
