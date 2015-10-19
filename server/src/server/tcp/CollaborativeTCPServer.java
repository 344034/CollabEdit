package server.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import data.Message;
import server.manager.CollaborativeManager;
import util.Util;

/**
 * @author Sandeep
 *
 */
public class CollaborativeTCPServer extends Thread {
	ServerSocket servsocket;
	CollaborativeManager manager;

	public CollaborativeTCPServer() {
		try {
			servsocket = new ServerSocket(CbConstants.URL_DATA.RECIEVE_UPDATE_PORT);
			manager = CollaborativeManager.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		startListeningForUpdates();
	}


	private void startListeningForUpdates() {

		while (true) {
			Socket connectionSocket;
			try {
				System.out.println("waiting for connection on port " + servsocket.getLocalPort());
				connectionSocket = servsocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());

				String message = "";
				String str;
				while ((str = inFromClient.readLine()) != null) {
					if (str.equals("done"))
						break;
					message = message + str;
					System.out.println(message);
				}
				System.out.println("Received: " + message);
				outToClient.println("Request Received");
				outToClient.println("done");
				outToClient.flush();
				outToClient.close();
				inFromClient.close();
				Message msg = Util.JSONToMessage(message);
				manager.updateSessionServers(msg);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
