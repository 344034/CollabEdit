package server.manager;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import model.ServerDetails;
import data.Message;

/**
 * Service that manages the coordinator server
 * 
 * @author thirunavukarasu
 *
 */
public class CoordinatorServerManager {
	private Map<String, Session> appDeviceSessionMap;
	private Map<String, ServerDetails> serverDetailsMap;

	public CoordinatorServerManager() {
		appDeviceSessionMap = new HashMap<>();
		serverDetailsMap = new HashMap<>();
	}

	public synchronized void addAppSessionToMap(String sessionKey,
			Session session) {
		appDeviceSessionMap.put(sessionKey, session);
	}

	public synchronized Session getAppSessionToMap(String sessionKey,
			Session session) {
		return appDeviceSessionMap.get(sessionKey);
	}

	public Message performClientOperation(Message message, String app,
			String channel) {
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

	/**
	 * Gets the server based on the load of the servers available.
	 * 
	 * @param app
	 * @param channel
	 * @return
	 */
	public String getCollaborativeServer(String app, String channel) {
		// TODO : Enhance this to provide the server based on the location of
		// the device.
		String minLoadServer = "localhost";
		int min = Integer.MIN_VALUE;

		for (String server : serverDetailsMap.keySet()) {
			int serverLoad = serverDetailsMap.get(server).getServerLoad();
			if (min < serverLoad) {
				minLoadServer = server;
				min = serverLoad;
			}
		}
		return minLoadServer;
		// return "localhost";
	}

	/**
	 * This performs operation like registering new server to the system, keep
	 * record of the connections open and closed.
	 * 
	 * @param message
	 * @return
	 */
	public Message performServerOperation(Message message) {
		Message replyMsg = new Message();
		switch (message.getOperation()) {
		case "NewServer":
			registerNewCollabServer(message.getUpdate());
			replyMsg.setUpdate("Added New server");
			break;
		}
		return replyMsg;

	}

	public void registerNewCollabServer(String server) {
		serverDetailsMap.put(server, new ServerDetails(server));
	}

}
