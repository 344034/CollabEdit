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
	private Date received;
	private String operation;
	private String update;

	public Message() {

	}

	public Message(String sender, Date received, String operation, String update) {
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

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
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

}
