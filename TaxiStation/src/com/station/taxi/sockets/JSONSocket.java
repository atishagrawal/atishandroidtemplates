
package com.station.taxi.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.station.taxi.utils.LoggerWrapper;

/**
 * Socket wrapper to work with JSON data
 * @author alex
 */
public class JSONSocket {
	
	private BufferedReader mFromNetInputStream;
	private PrintStream mToNetOutputStream;	
	private final Socket mSocket;

	/**
	 * 
	 * @param socket connection
	 */
	public JSONSocket(Socket socket) {
		mSocket = socket;
	}

	/**
	 * Initialize input and output streams
	 * @throws IOException
	 */
	public void init() throws IOException {
		mFromNetInputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		mToNetOutputStream = new PrintStream(mSocket.getOutputStream());
	}
	
	/**
	 * 
	 * @param message send json message
	 */
	public void sendMessage(JSONObject message) {
		String msg = message.toString();
		LoggerWrapper.log(JSONSocket.class.getSimpleName(), "Sending: " + msg);
		mToNetOutputStream.println(msg);
	}
	
	/**
	 * Receive message and convert it into JSON
	 * @return
	 */
	public JSONObject receiveMessage() {
		JSONObject message = null;
		try {
			String string = mFromNetInputStream.readLine();
			LoggerWrapper.log(JSONSocket.class.getSimpleName(), "Received: " + string);
			message = (JSONObject) new JSONTokener(string).nextValue();
		} catch (Exception e) {
			LoggerWrapper.logException(JSONSocket.class.getSimpleName(), e);
		}
		return message;
	}

	/**
	 * Close socket connection
	 * @throws IOException
	 */
	public void close() throws IOException {
		mSocket.close();
	}

	/**
	 * 
	 * @return wrapped socket object
	 */
	public Socket getSocket() {
		return mSocket;
	}
}
