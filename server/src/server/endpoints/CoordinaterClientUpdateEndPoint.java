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
import data.MessageEncoder;
import data.MessageDecoder;

@ServerEndpoint(value = "/coordinatorclientupdate/{app}/{channel}/{deviceID}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
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
		// Message response = coordinatorManager.performClientOperation(message,
		// app, channel);
		// Dummy invocation for now.
		Message response = coordinatorManager.performClientOperation(app,
				channel);
		try {
			session.getBasicRemote().sendText(
					Util.MessageToJSONString(response));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public void onMessage(final Session session, Message message) {
//
//		System.out.println("Received message in server: ");
//		// Receive a JSON message and check for the command.
//		String app = (String) session.getUserProperties().get("app");
//		String channel = (String) session.getUserProperties().get("channel");
//		// Message response = coordinatorManager.performClientOperation(message,
//		// app,
//		// channel);
//		System.out.println("Server received Message : " + message);
//
//		// TODO : Process the message and send the reply back.
//		// Find the server which has less load and
//		// update the coordinator server backup.
//		// send back the server details.
//
//		try {
//			// session.getBasicRemote().sendObject(response);
//			session.getBasicRemote().sendText(message.toString());
//		} catch (IOException e) {
//			logger.log(Level.WARNING, "onMessage failed", e);
//		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}
