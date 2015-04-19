package server.manager;

import java.util.HashMap;

//import javax.websocket.Session;
import data.Message;


/**
 * Manages the resolving conflicts between the messages from different client.
 * 
 * @author thirunavukarasu
 *
 */
public class ResolverManager {

	private HashMap<String, HashMap<String, Integer>> outerMap = new HashMap<String, HashMap<String, Integer>>();
	private HashMap<String, Integer> innerMap = new HashMap<String, Integer>();

	/**
	 * Method which gives the update sequence number for any application
	 * message.
	 * 
	 * @param message
	 * @param app
	 * @param channel
	 * @return
	 */
	public Message performOperation(Message message, String app, String channel) {
		Message replyMsg = new Message();
		switch (message.getOperation()) {
		case "GetUpdateNum":
			int val = 0;
			if (outerMap.containsKey(app)) {
				HashMap<String, Integer> map = outerMap.get("app");
				val = map.get(channel);
				outerMap.remove(app);
			}

			int count = val + 1;
			replyMsg.setUpdate(Integer.toString(count));
			innerMap.put(channel, count);
			outerMap.put(app, innerMap);
			break;
		}
		return replyMsg;
	}

}
