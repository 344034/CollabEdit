package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandeep
 *
 */
public class ServerDetails {
	private String serverName;
	Map<String, Map<String, Integer>> appSessionCount;

	public ServerDetails(String serverName) {
		this.serverName = serverName;
		appSessionCount = new HashMap<>();
	}

	/**
	 * Gives the total number of clients connected to this server.
	 * 
	 * @return
	 */
	public int getServerLoad() {
		int load = 0;
		for (String app : appSessionCount.keySet())
			load += getAppLoadInServer(app);
		return load;
	}

	/**
	 * Gives number of clients for specified app is assigned to this server.
	 * 
	 * @param app
	 * @return
	 */
	public int getAppLoadInServer(String app) {
		if (!appSessionCount.containsKey(app))
			return 0;
		return appSessionCount.get(app).size();
	}

	/**
	 * Increment the count of the app and the client.
	 * 
	 * @param app
	 * @param session
	 */
	public void addAppSession(String app, String session) {
		if (!appSessionCount.containsKey(app))
			appSessionCount.put(app, new HashMap<String, Integer>());
		if (!appSessionCount.get(app).containsKey(session))
			appSessionCount.get(app).put(session, 1);
		else
			appSessionCount.get(app).put(session,
					appSessionCount.get(app).get(session) + 1);
	}

	/**
	 * Increment the count of the app and the client.
	 * 
	 * @param app
	 * @param session
	 */
	public void removeAppSession(String app, String session) {
		if (appSessionCount.containsKey(app)
				&& appSessionCount.get(app).containsKey(session)) {
			appSessionCount.get(app).put(session,
					appSessionCount.get(app).get(session) - 1);
			if (appSessionCount.get(app).get(session) <= 0)
				appSessionCount.get(app).remove(session);
		}
	}

	/**
	 * Gets the current server name.
	 * 
	 * @return
	 */
	public String getServerName() {
		return serverName;
	}

}
