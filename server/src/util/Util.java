package util;

/**
 * Class to have util methods.
 * 
 * @author thirunavukarasu
 *
 */
public class Util {
	public static String getAppSessionDeviceID(String app, String channel,
			String deviceID) {
		return app + ":" + channel + ":" + deviceID;
	}
}
