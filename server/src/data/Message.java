package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Message to communicate across servers and clients.
 * 
 * @author sandeep
 *
 */
public class Message implements Serializable {
	private String app;
	private String channel;
	private String type;
	private String serverURL;
	private String priority;
	private String message;
	private List<String> serverList;

	public Message() {
		app="";
		channel="";
		type="";
		serverURL="";
		priority="-1";
		message="";
		serverList = new ArrayList<>();
	}

	public Message(String app, String channel, String type, String serverURL,
			String priority, String message) {
		super();
		this.app = app;
		this.channel = channel;
		this.type = type;
		this.serverURL = serverURL;
		this.priority = priority;
		this.message = message;
	}

	public List<String> getServerList() {
		return serverList;
	}

	public void setServerList(List<String> serverList) {
		this.serverList.clear();
		this.serverList.addAll(serverList);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ Message : App :" + app + ", channel :" + channel + ", type :"
				+ type + ",serverURL : " + serverURL + ",priority : "
				+ priority + ",message : " + message);
		if(serverList!=null ||serverList.size()>0){
			sb.append(" ServerList: ");
			for(String s: serverList)
				sb.append(s+" ");
		}
		sb.append( "]");
		return sb.toString();
	}

}
