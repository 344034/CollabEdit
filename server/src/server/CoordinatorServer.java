package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.Server;

import server.endpoints.CoordinaterClientUpdateEndPoint;

/**
 * Server to balance the load and assign servers to clients
 *
 * @author dhass
 *
 */
public class CoordinatorServer {
	public static void main(String[] args) {

		runServer();
	}

	public static void runServer() {
		// TODO: pass the hostname to the program
		Server clientUpdateCoordinator = new Server("localhost", 8030,
				"/websockets", CoordinaterClientUpdateEndPoint.class);
		// Server serverUpdateCoordinator = new Server("localhost", 8031,
		// "/websockets", CoordinaterServerUpdateEndPoint.class);
		try {
			clientUpdateCoordinator.start();
			// serverUpdateCoordinator.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clientUpdateCoordinator.stop();
			// serverUpdateCoordinator.stop();
		}
	}
}
