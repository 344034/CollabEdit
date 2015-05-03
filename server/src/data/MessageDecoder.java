package data;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

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
	public Message decode(final String textMessage) {
		System.out.println("Decoding..............");
		Message message = new Message();
		System.out.println("Decoding : txMsg - " + textMessage);
		try {
			JSONObject obj = new JSONObject(textMessage);
			message.setType(obj.getString("type"));
			message.setServerURL(obj.getString("serverurl"));
			message.setPriority(obj.getString("priority"));
			message.setMessage(obj.getString("message"));

			System.out.println("Decoded msg : " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
