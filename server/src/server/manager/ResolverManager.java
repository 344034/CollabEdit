package server.manager;

import java.util.HashMap;
import data.Message;

/**
 * Manages the resolving conflicts between the messages from different client.
 * 
 * @author thirunavukarasu
 *
 */
public class ResolverManager {

	private HashMap<String, HashMap<String, Integer>> counterMap;

	public ResolverManager() {
		counterMap = new HashMap<String, HashMap<String, Integer>>();
	}

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
			// TODO : Can this be removed??.
			if (!counterMap.containsKey(app)) {
				counterMap.put(app, new HashMap<String, Integer>());
			}
			if (!counterMap.get(app).containsKey(channel)) {
				counterMap.get(app).put(channel, 0);
			}

			int newTimestamp = counterMap.get(app).get(channel) + 1;
			replyMsg.setUpdate(Integer.toString(newTimestamp));
			counterMap.get(app).put(channel, newTimestamp);
			break;
		default:
			System.out.println("Operation not supported");
			break;
		}
		return replyMsg;
	}

}
