package data;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

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
		JSONObject obj = new JSONObject();
		obj.put("type", message.getType());
		obj.put("serverurl", message.getServerURL());
		obj.put("priority", message.getPriority());
		obj.put("message", message.getMessage());
		return obj.toString();
	}
}
