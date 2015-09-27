package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.Server;

import server.endpoints.CollaborativeServerEndPoint;

/**
 * Server to collaborate with the clients to receive and push updates.
 * 
 * @author dhass
 *
 */
public class CollaborativeServer {
	public static void main(String[] args) {
		// TODO : Need to access the coordinating server and get the other
		// associated servers.
		runServer();
	}

	public static void runServer() {
		// TODO: pass the hostname to the program
		Server server = new Server("localhost", 8025, "/websockets",
				CollaborativeServerEndPoint.class);
		try {
			server.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.stop();
		}
	}
}
