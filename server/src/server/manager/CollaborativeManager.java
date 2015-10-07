package server.manager;

import java.util.HashMap;

import server.clientendpoints.ResolverClient;
import data.Message;

public class CollaborativeManager {

	ResolverClient resolverClient;
	private HashMap<String, HashMap<String, Integer>> counterMap;
	private DocumentStoreManager documentStoreManager;
	public CollaborativeManager() {
		// TODO : Need to have
		// Resolver Endpoint.
//		resolverClient = new ResolverClient();
		// Other servers Multicast endpoints
		documentStoreManager = new DocumentStoreManager();
		counterMap = new HashMap<String, HashMap<String, Integer>>();
	}

	public Message getResolverNumber(Message message) {
		// How would you send the JSON String.
		Message msg = resolverClient.getResolverNumber(message);
		return msg;
	}

	// TODO: Add methods to multicast to other servers.
	public Message getResolverNumber(Message message, boolean inCollab) {
		// How would you send the JSON String.
		String app = message.getApp();
		String channel = message.getChannel();
		if (!counterMap.containsKey(app)) {
			counterMap.put(app, new HashMap<String, Integer>());
		}
		if (!counterMap.get(app).containsKey(channel)) {
			counterMap.get(app).put(channel, 0);
		}

		int newTimestamp = counterMap.get(app).get(channel) + 1;
		System.out.println("Setting priority...");
		message.setPriority(Integer.toString(newTimestamp));
		System.out.println("In resolver m : " + message);
		counterMap.get(app).put(channel, newTimestamp);
		return message;
	}

}
