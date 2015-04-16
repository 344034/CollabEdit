package server.manager;

import java.util.HashMap;

//import javax.websocket.Session;

import data.Message;
//import model.ServerDetails;

public class ResolverManager {
	
	private HashMap<String, HashMap<String, Integer>> outerMap = new HashMap<String, HashMap<String, Integer>>();
	private HashMap<String, Integer> innerMap = new HashMap<String, Integer>();   
	
	public Message performUpdateOperation(Message message, String app,
			String channel) {
		Message replyMsg = new Message();
		switch (message.getOperation()) {
		case "GetUpdateNum":
			// Find which server to be returned.
			//String server = getCollaborativeServer(app, channel);
			/* search if channel is already present */
			
			int val =0;
			
				if(outerMap.containsKey(app))
				{
					HashMap<String, Integer> map = outerMap.get("app");
					val = map.get(channel);
					outerMap.remove(app);
			}
			
			int count = val+1;
			replyMsg.setUpdate(Integer.toString(count));
			innerMap.put(channel, count);
			outerMap.put(app,innerMap);
			break;
		}
		return replyMsg;
	}
	
}
