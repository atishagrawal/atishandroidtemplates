package com.station.taxi.sockets;

import org.json.JSONObject;

import com.station.taxi.sockets.message.AbstractResponse;
import com.station.taxi.sockets.message.MessageFactory;
import com.station.taxi.sockets.message.Request;


/**
 *
 * @author alex
 */
public class StationClient {
	private static final int PORT = 13000;

	public static final String REQUEST_LIST_WAITING_CABS= "list_waiting_cabs";
	public static final String REQUEST_LIST_WAITING_PASSENGER = "list_waiting_passenger";
	public static final String REQUEST_LIST_DRIVING = "list_driving";

	private final Client mClient;

	public StationClient(String serverIp) {
		mClient = new JSONClient(serverIp, PORT);
	}

	public boolean isServerAvailable() {
		if (!mClient.connect()) {
			return false;
		}
		mClient.close();
		return true;
	}
	
	public AbstractResponse request(String input) {
		if (!mClient.connect()) {
			return null;
		}
		Request msg = null;
		AbstractResponse response = null;
		if (input.equals(REQUEST_LIST_WAITING_CABS)) {
			msg = new Request(MessageFactory.ACTION_LIST_WAITING_CABS);
		} else if (input.equals(REQUEST_LIST_WAITING_PASSENGER)) {
			msg = new Request(MessageFactory.ACTION_LIST_WAITING_PASSENGERS);			
		} else if (input.equals(REQUEST_LIST_DRIVING)) {
			msg = new Request(MessageFactory.ACTION_LIST_DRIVING);
		}

		if (msg!=null) {
			mClient.sendRequest(msg.toJSON());
			JSONObject json = (JSONObject)mClient.receiveResponse();
			response = MessageFactory.parseResponse(json);
		}
		mClient.close();
		return response;
	}
	

}
