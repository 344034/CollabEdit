package data;

import java.util.Date;

/**
 * Message to communicate across servers and clients.
 * 
 * @author thirunavukarasu
 *
 */
public class Message {
	private String sender;
	/**
	 * Convert Date to string and pass it to decode
	 */
	private String received;
	private String operation;
	private String update;

	public Message() {

	}

	public Message(String sender, String received, String operation,
			String update) {
		super();
		this.sender = sender;
		this.received = received;
		this.operation = operation;
		this.update = update;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String toString() {
		return "[sender:" + sender + ",received:" + received + ",operation:"
				+ operation + ",update:" + update + "]";
	}

}
