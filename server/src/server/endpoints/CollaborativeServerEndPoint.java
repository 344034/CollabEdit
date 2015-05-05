package server.endpoints;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import server.manager.CollaborativeManager;
import util.Util;
import data.Message;
import data.MessageDecoder;
import data.MessageEncoder;

@ServerEndpoint(value = "/collabserver/{application}/{channel}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class CollaborativeServerEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Session appUserSession = null;
	private CollaborativeManager collaborativeManager = new CollaborativeManager();

	@OnOpen
	public void onOpen(Session session,
			@PathParam("application") String application,
			@PathParam("channel") final String channel) {
		System.out.println("----> Connection established : " + application
				+ "/" + channel);
		session.getUserProperties().put("app", application);
		session.getUserProperties().put("channel", channel);
		this.appUserSession = session;
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {

		// TODO: Receive the message from client
		System.out.println("Received message : " + message);
		Message recMsg = Util.JSONToMessage(message);
		recMsg.setApp((String) session.getUserProperties().get("app"));
		recMsg.setChannel((String) session.getUserProperties().get("channel"));
		// Message msgWithUpdateNumber = recMsg;

		Message msgWithUpdateNumber = collaborativeManager.getResolverNumber(
				recMsg, true);

		// Store and Multicast

		// receive the timestamp counter.
		// Store in the datastore.
		// Multicast the message.
		System.out.println("In collab server Received update number : "
				+ msgWithUpdateNumber);
		int i = 1;
		for (Session s : session.getOpenSessions()) {
			if (s.isOpen()) {
				System.out.println(i + "UserProperty app : "
						+ s.getUserProperties().get("app"));
				System.out.println(i + "UserProperty channel : "
						+ s.getUserProperties().get("channel"));
				// Send message to the respective clients.
				if (msgWithUpdateNumber.getApp().equals(
						s.getUserProperties().get("app"))
						&& msgWithUpdateNumber.getChannel().equals(
								s.getUserProperties().get("channel"))) {
					try {
						// session.getBasicRemote().sendObject(response);
						System.out.println("In server : "
								+ msgWithUpdateNumber.toString());
						s.getBasicRemote().sendText(
								Util.MessageToJSONString(msgWithUpdateNumber));
					} catch (IOException e) {
						logger.log(Level.WARNING, "onMessage failed", e);
					}
				}

			}
			i++;
		}
		// return message;
	}

	public void sendMessage(final Message message) {
		for (Session s : appUserSession.getOpenSessions()) {
			if (s.isOpen()) {
				// Send message to the respective clients.
				if (message.getApp().equals(s.getUserProperties().get("app"))
						&& message.getChannel().equals(
								s.getUserProperties().get("channel"))) {
					try {
						// session.getBasicRemote().sendObject(response);
						System.out.println("In server : " + message.toString());
						s.getBasicRemote().sendText(
								Util.MessageToJSONString(message));
					} catch (IOException e) {
						logger.log(Level.WARNING, "onMessage failed", e);
					}
				}

			}
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}
