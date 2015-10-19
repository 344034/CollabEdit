package server.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import data.Message;
import util.Util;

/**
 * @author Sandeep
 *
 */
public class CollaborativeClientTCP extends Thread {
	String serverURL;
	String type;
	Socket connection;
	Logger logger;

	public CollaborativeClientTCP(String type) {
		this.type = type;
		logger = Logger.getLogger(CollaborativeClientTCP.class.getName());

	}

	@Override
	public void run() {
		switch (type) {
		case CbConstants.Action.REGISTRATION:
			registerWithCoordinateServer();
			break;
		}
	}

	private void registerWithCoordinateServer() {
		try {
			System.out.println("Sending server registration request to coordinte server on "
					+ CbConstants.URL_DATA.COORDINATE_URL + " and port " + CbConstants.URL_DATA.REGISTRATION_PORT);
			connection = new Socket(CbConstants.URL_DATA.COORDINATE_URL, CbConstants.URL_DATA.REGISTRATION_PORT);
			String instanceID = CbConstants.AWSUtils.retrieveInstanceId();
			String url = CbConstants.AWSUtils.getInstancePublicDnsName(instanceID);
			logger.info("Send the registration to coordinating server");
			Message msg = new Message();
			msg.setType("registration");
			msg.setServerURL(url);
			System.out.println("before sending characters");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter outToServer = new PrintWriter(connection.getOutputStream(), true);
			outToServer.println(Util.MessageToJSONString(msg));
			outToServer.println("done");
			outToServer.flush();
			System.out.println("sent characters");
			String str;
			while ((str = br.readLine()) != null) {
				if(str.equals("done"))
					break;
				System.out.println(str);
			}
			br.close();
			outToServer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != connection)
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
}
