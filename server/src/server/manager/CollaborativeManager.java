package server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Message;
import model.TimeStampedUpdate;
import server.clientendpoints.ResolverClient;

/**
 * @author Sandeep
 *
 */
public class CollaborativeManager {

	ResolverClient resolverClient;
	private HashMap<String, HashMap<String, Integer>> counterMap;
	private DocumentStoreManager documentStoreManager;
	private Map<String, List<String>> sessionServerMap;
	private Map<String, List<TimeStampedUpdate>> sessionUpdatesMap;
	private static CollaborativeManager _instance;

	public CollaborativeManager() {
		// TODO : Need to have
		// Resolver Endpoint.
		// resolverClient = new ResolverClient();
		// Other servers Multicast endpoints
		sessionServerMap = new HashMap<>();
		documentStoreManager = new DocumentStoreManager();
		counterMap = new HashMap<String, HashMap<String, Integer>>();
		sessionServerMap = new HashMap<>();
		_instance = this;
	}

	public Message getResolverNumber(Message message) {
		// How would you send the JSON String.
		Message msg = resolverClient.getResolverNumber(message);
		return msg;
	}

	public static CollaborativeManager getInstance() {
		if (_instance == null)
			_instance = new CollaborativeManager();
		return _instance;
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

	public void updateSessionServers(Message msg) {
		String session = msg.getMessage();
		List<String> serverList = msg.getServerList();
		sessionServerMap.put(session, serverList);
	}

	public void sessionUpdates(List<TimeStampedUpdate> updateList, String session) {
		List<TimeStampedUpdate> destination;
		if (sessionUpdatesMap.containsKey(session)) {
			destination = sessionUpdatesMap.get(session);
			if (destination != null) {
				destination.addAll(updateList);
			} else
				destination = new ArrayList<>(updateList);
		} else {
			destination = new ArrayList<>(updateList);
		}
		sessionUpdatesMap.put(session, destination);
	}

}
