package server.endpoints;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import server.manager.CoordinatorServerManager;
import util.Util;
import data.Message;

@ServerEndpoint(value = "/coordinatorclientupdate/{app}/{channel}/{deviceID}, encoders = MessageEncoder.class, decoders = MessageDecoder.class)")
public class CoordinaterClientUpdateEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	CoordinatorServerManager coordinatorManager = new CoordinatorServerManager();

	@OnOpen
	public void onOpen(Session session, @PathParam("app") final String app,
			@PathParam("channel") final String channel,
			@PathParam("deviceID") final String deviceID) {

		logger.info(" Opening connection for Application : " + app
				+ " Channel :" + channel + "DeviceID : " + deviceID);

		// Set the details of this session.
		session.getUserProperties().put("app", app);
		session.getUserProperties().put("channel", channel);
		session.getUserProperties().put("deviceID", deviceID);
		String key = Util.getAppSessionDeviceID(app, channel, deviceID);
		coordinatorManager.addAppSessionToMap(key, session);

		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public void onMessage(final Session session, Message message) {
		// Receive a JSON message and check for the command.
		String app = (String) session.getUserProperties().get("app");
		String channel = (String) session.getUserProperties().get("channel");
		Message response = coordinatorManager.performClientOperation(message, app,
				channel);
		if (session.isOpen())
			try {
				session.getBasicRemote().sendObject(response);
			} catch (IOException | EncodeException e) {
				logger.log(Level.WARNING, "onMessage failed", e);
			}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}
