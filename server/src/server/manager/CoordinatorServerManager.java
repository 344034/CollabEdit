package server.manager;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import data.Message;

/**
 * Service that manages the coordinator server
 * 
 * @author thirunavukarasu
 *
 */
public class CoordinatorServerManager {
	private Map<String, Session> appDeviceSessionMap;

	public CoordinatorServerManager() {
		appDeviceSessionMap = new HashMap<>();

		// TODO: Read from the configfile the list of instances(collaborating
		// servers) available.
	}

	public synchronized void addAppSessionToMap(String sessionKey,
			Session session) {
		appDeviceSessionMap.put(sessionKey, session);
	}

	public synchronized Session getAppSessionToMap(String sessionKey,
			Session session) {
		return appDeviceSessionMap.get(sessionKey);
	}

	public Message performOperation(Message message, String app, String channel) {
		Message replyMsg = new Message();
		switch (message.getOperation()) {
		case "GetServer":
			// Find which server to be returned.
			String server = getCollaborativeServer(app, channel);
			replyMsg.setUpdate(server);
			break;
		}
		return replyMsg;
	}

	public String getCollaborativeServer(String app, String channel) {
		// TODO : Add logic to find the load on each Server and allocate a new
		// server
		return "localhost";
	}

}
