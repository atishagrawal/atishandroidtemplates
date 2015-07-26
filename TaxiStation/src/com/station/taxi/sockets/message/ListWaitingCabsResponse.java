package com.station.taxi.sockets.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response message of waiting cabs
 * @author alex
 */
public class ListWaitingCabsResponse extends AbstractResponse {
	private static final String KEY_WAITACTIONS = "waitActions";
	private static final String KEY_STATUS = "statuses";

	public static final String STATUS_BREAK = "onBreak";
	public static final String STATUS_WAITING = "waiting";
	
	private Map<Integer,String> mCabsWaitActions = new HashMap<Integer, String>();
	private Map<Integer,String> mCabsStatus = new HashMap<Integer, String>();

	/**
	 * @return cab numbers with their waiting actions 
	 */
	public Map<Integer,String> getCabsWaitActions() {
		return mCabsWaitActions;
	}

	/**
	 * @return cab numbers with their waiting statuses
	 */
	public Map<Integer,String> getCabsStatus() {
		return mCabsStatus;
	}

	@Override
	protected void parseType(JSONObject json) throws JSONException {
		JSONObject jsonActions = (JSONObject) json.get(KEY_WAITACTIONS);
		JSONObject jsonStatus = (JSONObject) json.get(KEY_STATUS);
		Iterator keys = jsonActions.keys();
		while(keys.hasNext()) {
			String kStr = (String)keys.next();
			String kActionValue = (String)jsonActions.get(kStr);
			mCabsWaitActions.put(Integer.valueOf(kStr), kActionValue);
			String kStatusValue = (String)jsonStatus.get(kStr);
			mCabsStatus.put(Integer.valueOf(kStr), kStatusValue);
		}

	}
	
}
