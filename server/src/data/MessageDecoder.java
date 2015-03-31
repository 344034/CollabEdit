package data;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Decode the JSON to message Object
 * 
 * @author thirunavukarasu
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public Message decode(final String textMessage) throws DecodeException {
		Message message = new Message();
		JsonObject obj = Json.createReader(new StringReader(textMessage))
				.readObject();
		message.setSender(obj.getString("sender"));
		message.setReceived(new Date());
		message.setOperation(obj.getString("operation"));
		message.setUpdate(obj.getString("update"));
		return message;
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
