package util;

import org.json.JSONObject;

import data.Message;

/**
 * Class to have util methods.
 * 
 * @author sandeep
 *
 */
public class Util {
	public static String getAppSessionDeviceID(String app, String channel,
			String deviceID) {
		return app + ":" + channel + ":" + deviceID;
	}

	/**
	 * Converts jsonString to message object
	 * @param jsonString
	 * @return
	 */
	public static Message JSONToMessage(String jsonString) {
		Message message = new Message();
		System.out.println("Decoding : jsonMessage - " + jsonString);
		try {
			JSONObject obj = new JSONObject(jsonString);
			message.setType(obj.getString("type"));
			message.setServerURL(obj.getString("serverurl"));
			message.setPriority(obj.getString("priority"));
			message.setMessage(obj.getString("message"));

			System.out.println("Decoded msg : " + message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Converts Message to JSON String
	 * @param message
	 * @return
	 */
	public static String MessageToJSONString(Message message) {
		JSONObject obj = new JSONObject();
		obj.put("type", message.getType());
		obj.put("serverurl", message.getServerURL());
		obj.put("priority", message.getPriority());
		obj.put("message", message.getMessage());
		obj.put("serverlist", message.getServerList());
		return obj.toString();
	}
}
