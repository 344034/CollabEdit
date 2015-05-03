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
		// System.out.println("---->" + application + "/" + channel);
		session.getUserProperties().put("app", application);
		session.getUserProperties().put("channel", channel);
		this.appUserSession = session;
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {

		// TODO: Receive the message from client
		Message recMsg = Util.JSONToMessage(message);
		recMsg.setApp((String) session.getUserProperties().get("app"));
		recMsg.setChannel((String) session.getUserProperties().get("channel"));
		collaborativeManager.sendMessageToGetResolverNumber(recMsg);
		// receive the timestamp counter.
		// Store in the datastore.
		// Multicast the message.
		for (Session s : session.getOpenSessions()) {
			if (s.isOpen()) {
				try {
					// session.getBasicRemote().sendObject(response);
					System.out.println("In server : " + message.toString());
					JSONObject jsonObject = new JSONObject(message);

					JSONObject json = new JSONObject();

					json.put("type", "ACK");

					json.put("ACKNO", jsonObject.get("ACKNO"));

					jsonObject.put("type", "message");
					s.getBasicRemote().sendText(json.toString());
				} catch (IOException e) {
					logger.log(Level.WARNING, "onMessage failed", e);
				}
			}
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
