package com.station.taxi.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.json.JSONObject;

import com.station.taxi.utils.LoggerWrapper;

/**
 * JSON Implementation of client
 * @author alex
 */
public class JSONClient implements Client {
	private static final int TIMEOUT = 2000;

	private JSONSocket mJSONSocket;

	private final String mHost;
	private final Integer mPort;
	
	/**
	 * 
	 * @param host Server host to connect to
	 * @param port Server port to connect to
	 */
	public JSONClient(String host, Integer port) {
		mHost = host;
		mPort = port;
	}
	
	/**
	 * Connect to the server
	 * @return true if successfully connected
	 */
	public boolean connect() {
		try {
			SocketAddress sockaddr = new InetSocketAddress(mHost, mPort);
			Socket socket = new Socket();
			socket.connect(sockaddr, TIMEOUT);
			LoggerWrapper.log(JSONClient.class.getSimpleName(), 
					"Connected to "+socket.getInetAddress()+":"+socket.getPort()
					+" from "+socket.getLocalSocketAddress()
			);
			mJSONSocket = new JSONSocket(socket);
			mJSONSocket.init();
		} catch (Exception ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
			return false;
		}
		return true;
	}

	/**
	 * Disconnect from the server
	 */
	public void close() {
		if (mJSONSocket == null) {
			return;
		}
		try {
			mJSONSocket.close();
		} catch (IOException ex) {
			LoggerWrapper.logException(StationClient.class.getName(), ex);
		}
		mJSONSocket = null;
	}

	/**
	 * Send request to server
	 */
	public void sendRequest(Object request) {
		mJSONSocket.sendMessage((JSONObject)request);
	}

	/**
	 * Receive response form server
	 */
	public Object receiveResponse() {
		return mJSONSocket.receiveMessage();
	}
	
}
