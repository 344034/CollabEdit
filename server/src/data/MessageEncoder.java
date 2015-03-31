package data;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encode the message object to JSON
 * 
 * @author thirunavukarasu
 *
 */
public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public String encode(Message message) throws EncodeException {
		return Json.createObjectBuilder().add("sender", message.getSender())
				.add("received", message.getReceived().toString())
				.add("operation", message.getOperation())
				.add("update", message.getOperation()).build().toString();
	}

}
