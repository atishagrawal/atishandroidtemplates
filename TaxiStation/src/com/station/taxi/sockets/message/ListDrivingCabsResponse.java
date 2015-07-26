/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * List of driving cabs response
 * @author alex
 */
public class ListDrivingCabsResponse extends AbstractResponse {
	private static final String KEY_CABS = "cabs";

	public static final String STATUS_BREAK = "onBreak";
	public static final String STATUS_WAITING = "waiting";
	private static final String KEY_DESTINATION = "destination";
	private static final String KEY_PASSENGERS = "passengers";
	
	private Map<Integer,Map<String,Object>> mCabs = new HashMap<Integer, Map<String, Object>>();

	/**
	 * @return cab numbers
	 */
	public Set<Integer> getCabNumbers() {
		return mCabs.keySet();
	}
	
	/**
	 * Destination of cab with num
	 * @param num
	 * @return
	 */
	public String getDestination(Integer num) {
		Map<String, Object> data = mCabs.get(num);
		return (String)data.get(KEY_DESTINATION);
	}

	/**
	 * Passengers in the cab
	 * @param num
	 * @return List of passenger names
	 */
	public List<String> getPassengers(Integer num) {
		Map<String, Object> data = mCabs.get(num);
		return (List<String>) data.get(KEY_PASSENGERS);
	}

	@Override
	protected void parseType(JSONObject json) throws JSONException {
		
		JSONObject jsonCabs = (JSONObject) json.get(KEY_CABS);
		Iterator keys = jsonCabs.keys();
		while(keys.hasNext()) {
			String strNum = (String)keys.next();
			JSONObject jsonData = (JSONObject)jsonCabs.get(strNum);

			Map<String,Object> data = new HashMap<String,Object>();
			data.put(KEY_DESTINATION, (String)jsonData.get(KEY_DESTINATION));

			List<String> names = new ArrayList<String>();
			JSONArray jsonNamesArray = (JSONArray)jsonData.getJSONArray(KEY_PASSENGERS);
			for (int i = 0; i<jsonNamesArray.length(); i++) {
				names.add((String)jsonNamesArray.get(i));
			}
			
			data.put(KEY_PASSENGERS, names);

			mCabs.put(Integer.valueOf(strNum), data);
		}
	}

}
