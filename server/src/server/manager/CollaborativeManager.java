package server.manager;

import server.clientendpoints.CollabResolverEndPoint;
import util.Util;
import data.Message;

public class CollaborativeManager {

	CollabResolverEndPoint resolverEndpoint;

	public CollaborativeManager() {
		// TODO : Need to have
		// Resolver Endpoint.
		// Other servers Multicast endpoints
		resolverEndpoint = new CollabResolverEndPoint();
	}
	
	public void createResolverClient(){
		
	}

	public void sendMessageToGetResolverNumber(Message message) {
		// How would you send the JSON String.
//		out.writeObject(message);
//		String status = (String) in.readObject();
		resolverEndpoint.sendMessage(Util.MessageToJSONString(message));
	}
	
	
}
