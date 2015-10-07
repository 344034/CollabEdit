package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.glassfish.tyrus.server.Server;

import server.endpoints.CoordinaterClientUpdateEndPoint;
import server.manager.CoordinatorServerManager;

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
		Server clientUpdateCoordinator = null;
		//initializing CoordinatorServerMnagaer;
		new CoordinatorServerManager();
		clientUpdateCoordinator = new Server("ec2-52-89-146-237.us-west-2.compute.amazonaws.com", 8030,
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
