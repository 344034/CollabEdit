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

import data.MessageDecoder;
import data.MessageEncoder;

@ServerEndpoint(value = "/collabserver/{application}/{channel}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class CollaborativeServerEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Session appUserSession = null;

	@OnOpen
	public void onOpen(Session session,
			@PathParam("application") String application,
			@PathParam("channel") final String channel) {
		// System.out.println("---->" + application + "/" + channel);
		this.appUserSession = session;
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		// switch (message) {
		// case "quit":
		// try {
		//
		// session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
		// "Session ended"));
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		// break;
		// }
		// TODO: Receive the message from client
		// receive the timestamp counter.
		// Store in the datastore.
		// Multicast the message.
		for (Session s : session.getOpenSessions()) {
			if (s.isOpen()) {
				try {
					// session.getBasicRemote().sendObject(response);
					System.out.println("In server : " + message.toString());
					s.getBasicRemote().sendText(message.toString());
				} catch (IOException e) {
					logger.log(Level.WARNING, "onMessage failed", e);
				}
			}
		}
		// return message;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}
