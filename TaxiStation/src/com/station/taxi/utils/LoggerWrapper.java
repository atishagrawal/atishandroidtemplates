package com.station.taxi.utils;

import android.util.Log;

/**
 * Wrapper above android logger
 * @author alex
 *
 */
public class LoggerWrapper {

	public static void log(String tag, String msg) {
		Log.d(tag, msg);
	}

	public static void logException(String name, Exception ex) {
		Log.e(name, "Exception: ", ex);
	}

}
