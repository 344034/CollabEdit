package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;


@ClientEndpoint
public class WebClientEndpoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static CountDownLatch latch;

	public WebClientEndpoint(String url){

		logger.info("sandeep" +url);

		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(this, new URI(url));


		} catch (DeploymentException | URISyntaxException
				e) {
			e.printStackTrace();
		}

	}
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		try {
			session.getBasicRemote().sendText("start");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			logger.info("Received ...." + message);
			String userInput = bufferRead.readLine();
			return userInput;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s",
				session.getId(), closeReason));
	}

	public static void main(String[] args) {
		latch = new CountDownLatch(1);

		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(WebClientEndpoint.class, new URI(
					"ws://localhost:8025/websockets/collabserver/paint/abc"));

			// TODO : Send the JSON to the server and send back.
			// Create the JSON
			// Decide the JSON format.

//			JSONObject obj = new JSONObject();
//
//			obj.put("sender", "client");
//			obj.put("received", new Date());
//			obj.put("operation", "test");
//			obj.put("update", "message");
//
//			StringWriter out = new StringWriter();
//			// obj.writeJSONString(out);
//
//			String jsonText = out.toString();

			latch.await();

		} catch (DeploymentException | URISyntaxException
				| InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
